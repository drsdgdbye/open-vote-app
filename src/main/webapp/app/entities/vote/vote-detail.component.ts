import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVote } from 'app/shared/model/vote.model';

@Component({
  selector: 'jhi-vote-detail',
  templateUrl: './vote-detail.component.html',
})
export class VoteDetailComponent implements OnInit {
  vote: IVote | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vote }) => (this.vote = vote));
  }

  previousState(): void {
    window.history.back();
  }
}
