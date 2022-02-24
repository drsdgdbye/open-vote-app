import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVoting } from 'app/shared/model/voting.model';
import { VotingService } from './voting.service';

@Component({
  templateUrl: './voting-delete-dialog.component.html',
})
export class VotingDeleteDialogComponent {
  voting?: IVoting;

  constructor(protected votingService: VotingService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.votingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('votingListModification');
      this.activeModal.close();
    });
  }
}
