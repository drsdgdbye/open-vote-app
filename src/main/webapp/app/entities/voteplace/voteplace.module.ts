import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OpenVoteBackSharedModule } from 'app/shared/shared.module';
import { VoteplaceComponent } from './voteplace.component';
import { VoteplaceDetailComponent } from './voteplace-detail.component';
import { VoteplaceUpdateComponent } from './voteplace-update.component';
import { VoteplaceDeleteDialogComponent } from './voteplace-delete-dialog.component';
import { voteplaceRoute } from './voteplace.route';

@NgModule({
  imports: [OpenVoteBackSharedModule, RouterModule.forChild(voteplaceRoute)],
  declarations: [VoteplaceComponent, VoteplaceDetailComponent, VoteplaceUpdateComponent, VoteplaceDeleteDialogComponent],
  entryComponents: [VoteplaceDeleteDialogComponent],
})
export class OpenVoteBackVotePlaceModule {}
