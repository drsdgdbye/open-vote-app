import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVote } from 'app/shared/model/vote.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { VoteService } from './vote.service';
import { VoteDeleteDialogComponent } from './vote-delete-dialog.component';

@Component({
  selector: 'jhi-vote',
  templateUrl: './vote.component.html',
})
export class VoteComponent implements OnInit, OnDestroy {
  votes: IVote[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected voteService: VoteService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.votes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.voteService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IVote[]>) => this.paginateVotes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.votes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInVotes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVote): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInVotes(): void {
    this.eventSubscriber = this.eventManager.subscribe('voteListModification', () => this.reset());
  }

  delete(vote: IVote): void {
    const modalRef = this.modalService.open(VoteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.vote = vote;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateVotes(data: IVote[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.votes.push(data[i]);
      }
    }
  }
}
