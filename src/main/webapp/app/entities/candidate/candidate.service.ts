import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICandidate } from 'app/shared/model/candidate.model';

type EntityResponseType = HttpResponse<ICandidate>;
type EntityArrayResponseType = HttpResponse<ICandidate[]>;

@Injectable({ providedIn: 'root' })
export class CandidateService {
  public resourceUrl = SERVER_API_URL + 'api/candidates';

  constructor(protected http: HttpClient) {}

  create(candidate: ICandidate): Observable<EntityResponseType> {
    return this.http.post<ICandidate>(this.resourceUrl, candidate, { observe: 'response' });
  }

  update(candidate: ICandidate): Observable<EntityResponseType> {
    return this.http.put<ICandidate>(this.resourceUrl, candidate, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICandidate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICandidate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
