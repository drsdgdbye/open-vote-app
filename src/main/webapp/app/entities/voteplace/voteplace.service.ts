import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVotePlace } from '../../shared/model/voteplace.model';

type EntityResponseType = HttpResponse<IVotePlace>;
type EntityArrayResponseType = HttpResponse<IVotePlace[]>;

@Injectable({ providedIn: 'root' })
export class VoteplaceService {
  public resourceUrl = SERVER_API_URL + 'api/voteplaces';

  constructor(protected http: HttpClient) {}

  create(votePlace: IVotePlace): Observable<EntityResponseType> {
    return this.http.post<IVotePlace>(this.resourceUrl, votePlace, { observe: 'response' });
  }

  update(votePlace: IVotePlace): Observable<EntityResponseType> {
    return this.http.put<IVotePlace>(this.resourceUrl, votePlace, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVotePlace>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVotePlace[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
