import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVote } from 'app/shared/model/vote.model';
import { VoteService } from './vote.service';

@Component({
  templateUrl: './vote-delete-dialog.component.html',
})
export class VoteDeleteDialogComponent {
  vote?: IVote;

  constructor(protected voteService: VoteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.voteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('voteListModification');
      this.activeModal.close();
    });
  }
}
