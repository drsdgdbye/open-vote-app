import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUser, User } from 'app/shared/model/user.model';
import { UserService } from './user.service';
import { UserComponent } from './user.component';
import { UserDetailComponent } from './user-detail.component';
import { UserUpdateComponent } from './user-update.component';

@Injectable({ providedIn: 'root' })
export class UserResolve implements Resolve<IUser> {
  constructor(private service: UserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((user: HttpResponse<User>) => {
          if (user.body) {
            return of(user.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new User());
  }
}

export const userRoute: Routes = [
  {
    path: '',
    component: UserComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.user.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserDetailComponent,
    resolve: {
      user: UserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.user.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserUpdateComponent,
    resolve: {
      user: UserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.user.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserUpdateComponent,
    resolve: {
      user: UserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.user.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
