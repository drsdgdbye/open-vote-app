import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OpenVoteBackSharedModule } from 'app/shared/shared.module';
import { VotingComponent } from './voting.component';
import { VotingDetailComponent } from './voting-detail.component';
import { VotingUpdateComponent } from './voting-update.component';
import { VotingDeleteDialogComponent } from './voting-delete-dialog.component';
import { votingRoute } from './voting.route';
import { VotingResultsComponent } from './voting-results.component';

@NgModule({
  imports: [OpenVoteBackSharedModule, RouterModule.forChild(votingRoute)],
  declarations: [VotingComponent, VotingDetailComponent, VotingUpdateComponent, VotingDeleteDialogComponent, VotingResultsComponent],
  entryComponents: [VotingDeleteDialogComponent],
})
export class OpenVoteBackVotingModule {}
