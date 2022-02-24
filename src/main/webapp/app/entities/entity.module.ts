import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'candidate',
        loadChildren: () => import('./candidate/candidate.module').then(m => m.OpenVoteBackCandidateModule),
      },
      {
        path: 'votePlace',
        loadChildren: () => import('./voteplace/voteplace.module').then(m => m.OpenVoteBackVotePlaceModule),
      },
      {
        path: 'vote',
        loadChildren: () => import('./vote/vote.module').then(m => m.OpenVoteBackVoteModule),
      },
      {
        path: 'voting',
        loadChildren: () => import('./voting/voting.module').then(m => m.OpenVoteBackVotingModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class OpenVoteBackEntityModule {}
