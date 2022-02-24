import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVoting, Voting } from 'app/shared/model/voting.model';
import { VotingService } from './voting.service';
import { VotingComponent } from './voting.component';
import { VotingDetailComponent } from './voting-detail.component';
import { VotingUpdateComponent } from './voting-update.component';
import { VotingResultsComponent } from './voting-results.component';

@Injectable({ providedIn: 'root' })
export class VotingResolve implements Resolve<IVoting> {
  constructor(private service: VotingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVoting> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((voting: HttpResponse<Voting>) => {
          if (voting.body) {
            return of(voting.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Voting());
  }
}

export const votingRoute: Routes = [
  {
    path: '',
    component: VotingComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.voting.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VotingDetailComponent,
    resolve: {
      voting: VotingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.voting.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VotingUpdateComponent,
    resolve: {
      voting: VotingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.voting.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VotingUpdateComponent,
    resolve: {
      voting: VotingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.voting.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':cikVotingId/results',
    component: VotingResultsComponent,
    resolve: {
      voting: VotingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.voting.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
