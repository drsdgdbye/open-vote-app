import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVote, Vote } from 'app/shared/model/vote.model';
import { VoteService } from './vote.service';
import { VoteComponent } from './vote.component';
import { VoteDetailComponent } from './vote-detail.component';
import { VoteUpdateComponent } from './vote-update.component';

@Injectable({ providedIn: 'root' })
export class VoteResolve implements Resolve<IVote> {
  constructor(private service: VoteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVote> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((vote: HttpResponse<Vote>) => {
          if (vote.body) {
            return of(vote.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Vote());
  }
}

export const voteRoute: Routes = [
  {
    path: '',
    component: VoteComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.vote.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VoteDetailComponent,
    resolve: {
      vote: VoteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.vote.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VoteUpdateComponent,
    resolve: {
      vote: VoteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.vote.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VoteUpdateComponent,
    resolve: {
      vote: VoteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.vote.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
