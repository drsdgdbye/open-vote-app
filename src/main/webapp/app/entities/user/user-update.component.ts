import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUser, User } from 'app/shared/model/user.model';
import { UserService } from './user.service';

@Component({
  selector: 'jhi-user-update',
  templateUrl: './user-update.component.html',
})
export class UserUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
  });

  constructor(protected userService: UserService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ user }) => {
      this.updateForm(user);
    });
  }

  updateForm(user: IUser): void {
    this.editForm.patchValue({
      id: user.id,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const user = this.createFromForm();
    if (user.id !== undefined) {
      this.subscribeToSaveResponse(this.userService.update(user));
    } else {
      this.subscribeToSaveResponse(this.userService.create(user));
    }
  }

  private createFromForm(): IUser {
    return {
      ...new User(),
      id: this.editForm.get(['id'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUser>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
