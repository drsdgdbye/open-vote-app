import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { IVoteCount } from '../../shared/model/votecount.model';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { VoteService } from '../vote/vote.service';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { ITEMS_PER_PAGE } from '../../shared/constants/pagination.constants';

@Component({
  selector: 'jhi-voting-results',
  templateUrl: './voting-results.component.html',
})
export class VotingResultsComponent implements OnInit, OnDestroy {
  voteCounts: IVoteCount[];
  id: string | null = null;
  allVotesCount: number | null = null;
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected voteService: VoteService,
    protected activatedRoute: ActivatedRoute,
    protected eventManager: JhiEventManager,
    protected parseLinks: JhiParseLinks
  ) {
    this.voteCounts = [];
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
      .statistics(this.id!, {
        page: this.page,
        size: this.itemsPerPage,
      })
      .subscribe((res: HttpResponse<IVoteCount[]>) => this.paginateVoteCounts(res.body, res.headers));
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  reset(): void {
    this.page = 0;
    this.voteCounts = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  protected paginateVoteCounts(data: IVoteCount[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.voteCounts.push(data[i]);
      }
    }
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.paramMap.get('cikVotingId');
    this.voteService.getAllVotesCount(this.id!).subscribe(data => (this.allVotesCount = +data));
    this.loadAll();
    this.registerChangeInVotes();
  }

  registerChangeInVotes(): void {
    this.eventSubscriber = this.eventManager.subscribe('votingResultsListModification', () => this.reset());
  }

  previousState(): void {
    window.history.back();
  }
}
