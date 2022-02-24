import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVotePlace } from '../../shared/model/voteplace.model';
import { VoteplaceService } from './voteplace.service';

@Component({
  templateUrl: './voteplace-delete-dialog.component.html',
})
export class VoteplaceDeleteDialogComponent {
  votePlace?: IVotePlace;

  constructor(protected voteplaceService: VoteplaceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.voteplaceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('districtListModification');
      this.activeModal.close();
    });
  }
}
