import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVotePlace, VotePlace } from '../../shared/model/voteplace.model';
import { VoteplaceService } from './voteplace.service';
import { VoteplaceComponent } from './voteplace.component';
import { VoteplaceDetailComponent } from './voteplace-detail.component';
import { VoteplaceUpdateComponent } from './voteplace-update.component';

@Injectable({ providedIn: 'root' })
export class VotePlaceResolve implements Resolve<IVotePlace> {
  constructor(private service: VoteplaceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVotePlace> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((votePlace: HttpResponse<VotePlace>) => {
          if (votePlace.body) {
            return of(votePlace.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VotePlace());
  }
}

export const voteplaceRoute: Routes = [
  {
    path: '',
    component: VoteplaceComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.votePlace.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VoteplaceDetailComponent,
    resolve: {
      votePlace: VotePlaceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.votePlace.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VoteplaceUpdateComponent,
    resolve: {
      votePlace: VotePlaceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.votePlace.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VoteplaceUpdateComponent,
    resolve: {
      votePlace: VotePlaceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.votePlace.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
