package com.onemorevote.openvoteapp.service;

import com.onemorevote.openvoteapp.config.Constants;
import com.onemorevote.openvoteapp.domain.Authority;
import com.onemorevote.openvoteapp.domain.User;
import com.onemorevote.openvoteapp.domain.VotePlace;
import com.onemorevote.openvoteapp.repository.AuthorityRepository;
import com.onemorevote.openvoteapp.repository.UserRepository;
import com.onemorevote.openvoteapp.repository.VotePlaceRepository;
import com.onemorevote.openvoteapp.security.AuthoritiesConstants;
import com.onemorevote.openvoteapp.security.SecurityUtils;
import com.onemorevote.openvoteapp.service.dto.UserDTO;
import com.onemorevote.openvoteapp.service.exceptions.EmailAlreadyUsedException;
import com.onemorevote.openvoteapp.service.exceptions.InvalidPasswordException;
import com.onemorevote.openvoteapp.service.exceptions.UsernameAlreadyUsedException;
import com.onemorevote.openvoteapp.web.rest.errors.VotePlaceNotFoundException;
import io.github.jhipster.security.RandomUtil;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;

    private final MailService mailService;

    private final VotePlaceRepository votePlaceRepository;

    public UserService(UserRepository userRepository,
                       MailService mailService,
                       PasswordEncoder passwordEncoder,
                       AuthorityRepository authorityRepository,
                       CacheManager cacheManager,
                       VotePlaceRepository votePlaceRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
        this.mailService = mailService;
        this.votePlaceRepository = votePlaceRepository;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                this.clearUserCaches(user);
                log.debug("Activated user: {}", user);
                return user;
            });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository.findOneByResetKey(key)
            .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
            .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                this.clearUserCaches(user);
                return user;
            });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail)
            .filter(User::getActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(Instant.now());
                this.clearUserCaches(user);
                return user;
            });
    }

    public void registerUser(UserDTO userDTO) {
        userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new UsernameAlreadyUsedException();
            }
        });
        userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });
        User newUser = new User();
        String newUserPassword = SecurityUtils.generatePassword();
        String encryptedPassword = passwordEncoder.encode(newUserPassword);
        newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            newUser.setEmail(userDTO.getEmail().toLowerCase());
        }
        newUser.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            newUser.setLangKey(Constants.DEFAULT_LANGUAGE);
        } else {
            newUser.setLangKey(userDTO.getLangKey());
        }
        // new user already active
        newUser.setActivated(true);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        newUser.setMiddleName(userDTO.getMiddleName());
        newUser.setPhone(userDTO.getPhone());
        newUser.setAddress(userDTO.getAddress());
        newUser.setBirthday(userDTO.getBirthday());
        newUser.setCity(userDTO.getCity());
        newUser.setVotePlaceId(userDTO.getVotePlaceId());
        VotePlace votePlace = votePlaceRepository.findById(Long.parseLong(userDTO.getVotePlaceId()))
            .orElseThrow(VotePlaceNotFoundException::new);
        newUser.setVotePlace(votePlace);
        newUser.setFbId(userDTO.getFbId());
        newUser.setTwitterId(userDTO.getTwitterId());
        newUser.setIssuedBy(userDTO.getIssuedBy());
        newUser.setIssuedDate(userDTO.getIssuedDate());
        newUser.setPassport(userDTO.getPassport());
        userRepository.save(newUser);
        this.clearUserCaches(newUser);
        //send raw password to user in email
        User mailUser = new User();
        mailUser.setLogin(userDTO.getLogin());
        mailUser.setFirstName(Objects.isNull(userDTO.getFirstName()) ? userDTO.getLogin() : userDTO.getFirstName());
        mailUser.setLangKey(Objects.isNull(userDTO.getLangKey()) ? Constants.DEFAULT_LANGUAGE : userDTO.getLangKey());
        mailUser.setEmail(userDTO.getEmail());
        mailUser.setPassword(newUserPassword);
        mailService.sendPasswordEmail(mailUser);
        this.clearUserCaches(mailUser);

        log.debug("Created Information for User: {}", newUser);
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.getActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        this.clearUserCaches(existingUser);
        return true;
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin().toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE);
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        String password = SecurityUtils.generatePassword();
        String encryptedPassword = passwordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO.getAuthorities().stream()
                .map(authorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
        user.setMiddleName(userDTO.getMiddleName());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setBirthday(userDTO.getBirthday());
        user.setCity(userDTO.getCity());
        user.setVotePlaceId(userDTO.getVotePlaceId());
        user.setFbId(userDTO.getFbId());
        user.setTwitterId(userDTO.getTwitterId());
        user.setIssuedBy(userDTO.getIssuedBy());
        user.setIssuedDate(userDTO.getIssuedDate());
        user.setPassport(userDTO.getPassport());
        userRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
            .findById(userDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                this.clearUserCaches(user);
                user.setLogin(userDTO.getLogin().toLowerCase());
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                if (userDTO.getEmail() != null) {
                    user.setEmail(userDTO.getEmail().toLowerCase());
                }
                user.setImageUrl(userDTO.getImageUrl());
                user.setActivated(userDTO.isActivated());
                user.setLangKey(userDTO.getLangKey());
                Set<Authority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                userDTO.getAuthorities().stream()
                    .map(authorityRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(managedAuthorities::add);
                user.setMiddleName(userDTO.getMiddleName());
                user.setPhone(userDTO.getPhone());
                user.setAddress(userDTO.getAddress());
                user.setBirthday(userDTO.getBirthday());
                user.setCity(userDTO.getCity());
                user.setVotePlaceId(userDTO.getVotePlaceId());
                user.setFbId(userDTO.getFbId());
                user.setTwitterId(userDTO.getTwitterId());
                user.setIssuedBy(userDTO.getIssuedBy());
                user.setIssuedDate(userDTO.getIssuedDate());
                user.setPassport(userDTO.getPassport());
                userRepository.save(user);
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
                return user;
            })
            .map(UserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            this.clearUserCaches(user);
            log.debug("Deleted User: {}", user);
        });
    }

    /**
     * Update basic information (first name, last name, email, language and so on) for the current user.
     *
     * @param userDTO userDto.
     */
    public void updateUserData(UserDTO userDTO) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                String email = userDTO.getEmail();
                if (email != null) {
                    user.setEmail(email.toLowerCase());
                }
                user.setLangKey(userDTO.getLangKey());
                user.setImageUrl(userDTO.getImageUrl());
                user.setMiddleName(userDTO.getMiddleName());
                user.setPhone(userDTO.getPhone());
                user.setAddress(userDTO.getAddress());
                user.setBirthday(userDTO.getBirthday());
                user.setCity(userDTO.getCity());
                user.setVotePlaceId(userDTO.getVotePlaceId());
                user.setFbId(userDTO.getFbId());
                user.setTwitterId(userDTO.getTwitterId());
                user.setIssuedBy(userDTO.getIssuedBy());
                user.setIssuedDate(userDTO.getIssuedDate());
                user.setPassport(userDTO.getPassport());
                userRepository.save(user);
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
            });
    }


    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                String currentEncryptedPassword = user.getPassword();
                if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                    throw new InvalidPasswordException();
                }
                String encryptedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encryptedPassword);
                this.clearUserCaches(user);
                log.debug("Changed password for User: {}", user);
            });
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
            .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(user -> {
                log.debug("Deleting not activated user {}", user.getLogin());
                userRepository.delete(user);
                this.clearUserCaches(user);
            });
    }

    /**
     * Gets a list of all the authorities.
     *
     * @return a list of all the authorities.
     */
    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    /**
     * Gets count of users.
     *
     * @return Long.
     */
    public Long getManagedUsersCount() {
        return userRepository.countAllByLoginNot(Constants.ANONYMOUS_USER);
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }
    }
}
