import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVotePlace, VotePlace } from '../../shared/model/voteplace.model';
import { VoteplaceService } from './voteplace.service';

@Component({
  selector: 'jhi-district-update',
  templateUrl: './voteplace-update.component.html',
})
export class VoteplaceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    vrn: [],
    name: [],
    subjCode: [],
    numKsa: [],
    vid: [],
    address: [],
    descr: [],
    phone: [],
    lat: [],
    lon: [],
    votingAddress: [],
    votingDescr: [],
    votingPhone: [],
    votingLat: [],
    votingLon: [],
  });

  constructor(protected votePlaceService: VoteplaceService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ votePlace }) => {
      this.updateForm(votePlace);
    });
  }

  updateForm(votePlace: IVotePlace): void {
    this.editForm.patchValue({
      id: votePlace.id,
      vrn: votePlace.vrn,
      name: votePlace.name,
      subjCode: votePlace.subjCode,
      numKsa: votePlace.numKsa,
      vid: votePlace.vid,
      address: votePlace.address,
      descr: votePlace.descr,
      phone: votePlace.phone,
      lat: votePlace.lat,
      lon: votePlace.lon,
      votingAddress: votePlace.votingAddress,
      votingDescr: votePlace.votingDescr,
      votingPhone: votePlace.votingPhone,
      votingLat: votePlace.votingLat,
      votingLon: votePlace.votingLon,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const votePlace = this.createFromForm();
    if (votePlace.id !== undefined) {
      this.subscribeToSaveResponse(this.votePlaceService.update(votePlace));
    } else {
      this.subscribeToSaveResponse(this.votePlaceService.create(votePlace));
    }
  }

  private createFromForm(): IVotePlace {
    return {
      ...new VotePlace(),
      id: this.editForm.get(['id'])!.value,
      vrn: this.editForm.get(['vrn'])!.value,
      name: this.editForm.get(['name'])!.value,
      subjCode: this.editForm.get(['subjCode'])!.value,
      numKsa: this.editForm.get(['numKsa'])!.value,
      vid: this.editForm.get(['vid'])!.value,
      address: this.editForm.get(['address'])!.value,
      descr: this.editForm.get(['descr'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      lat: this.editForm.get(['lat'])!.value,
      lon: this.editForm.get(['lon'])!.value,
      votingAddress: this.editForm.get(['votingAddress'])!.value,
      votingDescr: this.editForm.get(['votingDescr'])!.value,
      votingPhone: this.editForm.get(['votingPhone'])!.value,
      votingLat: this.editForm.get(['votingLat'])!.value,
      votingLon: this.editForm.get(['votingLon'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVotePlace>>): void {
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
