import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVotePlace } from '../../shared/model/voteplace.model';

@Component({
  selector: 'jhi-district-detail',
  templateUrl: './voteplace-detail.component.html',
})
export class VoteplaceDetailComponent implements OnInit {
  votePlace: IVotePlace | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ votePlace }) => (this.votePlace = votePlace));
  }

  previousState(): void {
    window.history.back();
  }
}
