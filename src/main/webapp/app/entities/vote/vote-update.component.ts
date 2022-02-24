import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IVote, Vote } from 'app/shared/model/vote.model';
import { VoteService } from './vote.service';
import { ICandidate } from 'app/shared/model/candidate.model';
import { CandidateService } from 'app/entities/candidate/candidate.service';
import { IVoting } from 'app/shared/model/voting.model';
import { VotingService } from 'app/entities/voting/voting.service';

type SelectableEntity = ICandidate | IVoting;

@Component({
  selector: 'jhi-vote-update',
  templateUrl: './vote-update.component.html',
})
export class VoteUpdateComponent implements OnInit {
  isSaving = false;
  candidates: ICandidate[] = [];
  votings: IVoting[] = [];

  editForm = this.fb.group({
    id: [],
    userId: [null, [Validators.required]],
    createDate: [null, [Validators.required]],
    candidateId: [],
    votingId: [],
  });

  constructor(
    protected voteService: VoteService,
    protected candidateService: CandidateService,
    protected votingService: VotingService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vote }) => {
      if (!vote.id) {
        const today = moment().startOf('day');
        vote.createDate = today;
      }

      this.updateForm(vote);

      this.candidateService.query().subscribe((res: HttpResponse<ICandidate[]>) => (this.candidates = res.body || []));

      this.votingService.query().subscribe((res: HttpResponse<IVoting[]>) => (this.votings = res.body || []));
    });
  }

  updateForm(vote: IVote): void {
    this.editForm.patchValue({
      id: vote.id,
      userId: vote.userId,
      createDate: vote.createDate ? vote.createDate.format(DATE_TIME_FORMAT) : null,
      candidateId: vote.candidateId,
      votingId: vote.votingId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vote = this.createFromForm();
    if (vote.id !== undefined) {
      this.subscribeToSaveResponse(this.voteService.update(vote));
    } else {
      this.subscribeToSaveResponse(this.voteService.create(vote));
    }
  }

  private createFromForm(): IVote {
    return {
      ...new Vote(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      createDate: this.editForm.get(['createDate'])!.value ? moment(this.editForm.get(['createDate'])!.value, DATE_TIME_FORMAT) : undefined,
      candidateId: this.editForm.get(['candidateId'])!.value,
      votingId: this.editForm.get(['votingId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVote>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
