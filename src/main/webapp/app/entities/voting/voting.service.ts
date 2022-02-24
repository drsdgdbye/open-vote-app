import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVoting } from 'app/shared/model/voting.model';

type EntityResponseType = HttpResponse<IVoting>;
type EntityArrayResponseType = HttpResponse<IVoting[]>;

@Injectable({ providedIn: 'root' })
export class VotingService {
  public resourceUrl = SERVER_API_URL + 'api/votings';

  constructor(protected http: HttpClient) {}

  create(voting: IVoting): Observable<EntityResponseType> {
    return this.http.post<IVoting>(this.resourceUrl, voting, { observe: 'response' });
  }

  update(voting: IVoting): Observable<EntityResponseType> {
    return this.http.put<IVoting>(this.resourceUrl, voting, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVoting>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVoting[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
