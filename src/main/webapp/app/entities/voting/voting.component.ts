import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVoting } from 'app/shared/model/voting.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { VotingService } from './voting.service';
import { VotingDeleteDialogComponent } from './voting-delete-dialog.component';

@Component({
  selector: 'jhi-voting',
  templateUrl: './voting.component.html',
})
export class VotingComponent implements OnInit, OnDestroy {
  votings: IVoting[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected votingService: VotingService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.votings = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.votingService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IVoting[]>) => this.paginateVotings(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.votings = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInVotings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVoting): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInVotings(): void {
    this.eventSubscriber = this.eventManager.subscribe('votingListModification', () => this.reset());
  }

  delete(voting: IVoting): void {
    const modalRef = this.modalService.open(VotingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.voting = voting;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateVotings(data: IVoting[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.votings.push(data[i]);
      }
    }
  }
}
