import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICandidate, Candidate } from 'app/shared/model/candidate.model';
import { CandidateService } from './candidate.service';

@Component({
  selector: 'jhi-candidate-update',
  templateUrl: './candidate-update.component.html',
})
export class CandidateUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    type: [],
    politicalParty: [],
    description: [],
    imageUrl: [],
    cikCandidateId: [null, [Validators.required]],
  });

  constructor(protected candidateService: CandidateService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ candidate }) => {
      this.updateForm(candidate);
    });
  }

  updateForm(candidate: ICandidate): void {
    this.editForm.patchValue({
      id: candidate.id,
      name: candidate.name,
      type: candidate.type,
      politicalParty: candidate.politicalParty,
      description: candidate.description,
      imageUrl: candidate.imageUrl,
      cikCandidateId: candidate.cikCandidateId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const candidate = this.createFromForm();
    if (candidate.id !== undefined) {
      this.subscribeToSaveResponse(this.candidateService.update(candidate));
    } else {
      this.subscribeToSaveResponse(this.candidateService.create(candidate));
    }
  }

  private createFromForm(): ICandidate {
    return {
      ...new Candidate(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      type: this.editForm.get(['type'])!.value,
      politicalParty: this.editForm.get(['politicalParty'])!.value,
      description: this.editForm.get(['description'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      cikCandidateId: this.editForm.get(['cikCandidateId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidate>>): void {
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
