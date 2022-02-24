import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUser } from 'app/shared/model/user.model';

@Component({
  selector: 'jhi-user-detail',
  templateUrl: './user-detail.component.html',
})
export class UserDetailComponent implements OnInit {
  user: IUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ user }) => (this.user = user));
  }

  previousState(): void {
    window.history.back();
  }
}
