import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVotePlace } from '../../shared/model/voteplace.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { VoteplaceService } from './voteplace.service';
import { VoteplaceDeleteDialogComponent } from './voteplace-delete-dialog.component';

@Component({
  selector: 'jhi-district',
  templateUrl: './voteplace.component.html',
})
export class VoteplaceComponent implements OnInit, OnDestroy {
  votePlaces: IVotePlace[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected voteplaceService: VoteplaceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.votePlaces = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.voteplaceService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IVotePlace[]>) => this.paginateVotePlaces(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.votePlaces = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInVotePlaces();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVotePlace): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInVotePlaces(): void {
    this.eventSubscriber = this.eventManager.subscribe('districtListModification', () => this.reset());
  }

  delete(votePlace: IVotePlace): void {
    const modalRef = this.modalService.open(VoteplaceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.votePlace = votePlace;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateVotePlaces(data: IVotePlace[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.votePlaces.push(data[i]);
      }
    }
  }
}
