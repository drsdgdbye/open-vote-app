import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUser } from 'app/shared/model/user.model';
import { UserService } from './user.service';

@Component({
  templateUrl: './user-delete-dialog.component.html',
})
export class UserDeleteDialogComponent {
  user?: IUser;

  constructor(protected userService: UserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userListModification');
      this.activeModal.close();
    });
  }
}
