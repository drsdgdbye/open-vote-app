import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVote } from 'app/shared/model/vote.model';
import { IVoteCount } from '../../shared/model/votecount.model';

type EntityResponseType = HttpResponse<IVote>;
type EntityArrayResponseType = HttpResponse<IVote[]>;

@Injectable({ providedIn: 'root' })
export class VoteService {
  public resourceUrl = SERVER_API_URL + 'api/votes';

  constructor(protected http: HttpClient) {}

  create(vote: IVote): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vote);
    return this.http
      .post<IVote>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vote: IVote): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vote);
    return this.http
      .put<IVote>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVote>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVote[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  statistics(id: string, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVoteCount[]>(`${this.resourceUrl}/on/${id}`, { params: options, observe: 'response' });
  }

  getAllVotesCount(id: string): Observable<string> {
    const headers = new HttpHeaders().set('Content-Type', 'text/plain; charset=utf-8');
    return this.http.get(`${this.resourceUrl}/count/by/${id}`, { headers, responseType: 'text' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(vote: IVote): IVote {
    const copy: IVote = Object.assign({}, vote, {
      createDate: vote.createDate && vote.createDate.isValid() ? vote.createDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createDate = res.body.createDate ? moment(res.body.createDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vote: IVote) => {
        vote.createDate = vote.createDate ? moment(vote.createDate) : undefined;
      });
    }
    return res;
  }
}
