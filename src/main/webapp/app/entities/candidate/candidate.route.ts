import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICandidate, Candidate } from 'app/shared/model/candidate.model';
import { CandidateService } from './candidate.service';
import { CandidateComponent } from './candidate.component';
import { CandidateDetailComponent } from './candidate-detail.component';
import { CandidateUpdateComponent } from './candidate-update.component';

@Injectable({ providedIn: 'root' })
export class CandidateResolve implements Resolve<ICandidate> {
  constructor(private service: CandidateService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICandidate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((candidate: HttpResponse<Candidate>) => {
          if (candidate.body) {
            return of(candidate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Candidate());
  }
}

export const candidateRoute: Routes = [
  {
    path: '',
    component: CandidateComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.candidate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CandidateDetailComponent,
    resolve: {
      candidate: CandidateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.candidate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CandidateUpdateComponent,
    resolve: {
      candidate: CandidateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.candidate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CandidateUpdateComponent,
    resolve: {
      candidate: CandidateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'openVoteBackApp.candidate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
