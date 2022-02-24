import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICandidate } from 'app/shared/model/candidate.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CandidateService } from './candidate.service';
import { CandidateDeleteDialogComponent } from './candidate-delete-dialog.component';

@Component({
  selector: 'jhi-candidate',
  templateUrl: './candidate.component.html',
})
export class CandidateComponent implements OnInit, OnDestroy {
  candidates: ICandidate[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected candidateService: CandidateService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.candidates = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.candidateService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<ICandidate[]>) => this.paginateCandidates(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.candidates = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCandidates();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICandidate): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCandidates(): void {
    this.eventSubscriber = this.eventManager.subscribe('candidateListModification', () => this.reset());
  }

  delete(candidate: ICandidate): void {
    const modalRef = this.modalService.open(CandidateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.candidate = candidate;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCandidates(data: ICandidate[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.candidates.push(data[i]);
      }
    }
  }
}
