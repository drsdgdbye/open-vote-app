import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICandidate } from 'app/shared/model/candidate.model';
import { CandidateService } from './candidate.service';

@Component({
  templateUrl: './candidate-delete-dialog.component.html',
})
export class CandidateDeleteDialogComponent {
  candidate?: ICandidate;

  constructor(protected candidateService: CandidateService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.candidateService.delete(id).subscribe(() => {
      this.eventManager.broadcast('candidateListModification');
      this.activeModal.close();
    });
  }
}
