import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVoting, Voting } from 'app/shared/model/voting.model';
import { VotingService } from './voting.service';

@Component({
  selector: 'jhi-voting-update',
  templateUrl: './voting-update.component.html',
})
export class VotingUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    date: [null, [Validators.required]],
    cikVotingId: [null, [Validators.required]],
  });

  constructor(protected votingService: VotingService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voting }) => {
      this.updateForm(voting);
    });
  }

  updateForm(voting: IVoting): void {
    this.editForm.patchValue({
      id: voting.id,
      name: voting.name,
      date: voting.date,
      cikVotingId: voting.cikVotingId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const voting = this.createFromForm();
    if (voting.id !== undefined) {
      this.subscribeToSaveResponse(this.votingService.update(voting));
    } else {
      this.subscribeToSaveResponse(this.votingService.create(voting));
    }
  }

  private createFromForm(): IVoting {
    return {
      ...new Voting(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      date: this.editForm.get(['date'])!.value,
      cikVotingId: this.editForm.get(['cikVotingId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoting>>): void {
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
