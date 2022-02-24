import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { JhiLanguageService } from 'ng-jhipster';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { LANGUAGES } from 'app/core/language/language.constants';

@Component({
  selector: 'jhi-settings',
  templateUrl: './settings.component.html',
})
export class SettingsComponent implements OnInit {
  account!: Account;
  success = false;
  languages = LANGUAGES;
  settingsForm = this.fb.group({
    firstName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    lastName: [undefined, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
    email: [undefined, [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    langKey: [undefined],
    middleName: [undefined, [Validators.maxLength(50)]],
    passport: [undefined, [Validators.minLength(10), Validators.maxLength(12)]],
    issuedBy: [undefined, [Validators.maxLength(255)]],
    issuedDate: [undefined, [Validators.maxLength(16)]],
    birthday: [undefined, [Validators.maxLength(16)]],
    phone: [undefined, [Validators.minLength(12), Validators.maxLength(16)]],
  });

  constructor(private accountService: AccountService, private fb: FormBuilder, private languageService: JhiLanguageService) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.settingsForm.patchValue({
          firstName: account.firstName,
          lastName: account.lastName,
          email: account.email,
          langKey: account.langKey,
          middleName: account.middleName,
          passport: account.passport,
          issuedBy: account.issuedBy,
          issuedDate: account.issuedDate,
          birthday: account.birthday,
          phone: account.phone,
        });

        this.account = account;
      }
    });
  }

  save(): void {
    this.success = false;

    this.account.firstName = this.settingsForm.get('firstName')!.value;
    this.account.lastName = this.settingsForm.get('lastName')!.value;
    this.account.email = this.settingsForm.get('email')!.value;
    this.account.langKey = this.settingsForm.get('langKey')!.value;
    this.account.middleName = this.settingsForm.get(['middleName'])!.value;
    this.account.passport = this.settingsForm.get(['passport'])!.value;
    this.account.issuedBy = this.settingsForm.get(['issuedBy'])!.value;
    this.account.issuedDate = this.settingsForm.get(['issuedDate'])!.value;
    this.account.birthday = this.settingsForm.get(['birthday'])!.value;
    this.account.phone = this.settingsForm.get(['phone'])!.value;

    this.accountService.save(this.account).subscribe(() => {
      this.success = true;

      this.accountService.authenticate(this.account);

      if (this.account.langKey !== this.languageService.getCurrentLanguage()) {
        this.languageService.changeLanguage(this.account.langKey);
      }
    });
  }
}
