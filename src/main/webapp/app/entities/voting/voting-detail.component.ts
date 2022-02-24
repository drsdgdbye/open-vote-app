import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVoting } from 'app/shared/model/voting.model';

@Component({
  selector: 'jhi-voting-detail',
  templateUrl: './voting-detail.component.html',
})
export class VotingDetailComponent implements OnInit {
  voting: IVoting | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voting }) => (this.voting = voting));
  }

  previousState(): void {
    window.history.back();
  }
}
