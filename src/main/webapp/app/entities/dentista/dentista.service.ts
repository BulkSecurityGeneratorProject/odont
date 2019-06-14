import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDentista } from 'app/shared/model/dentista.model';

type EntityResponseType = HttpResponse<IDentista>;
type EntityArrayResponseType = HttpResponse<IDentista[]>;

@Injectable({ providedIn: 'root' })
export class DentistaService {
  public resourceUrl = SERVER_API_URL + 'api/dentistas';

  constructor(protected http: HttpClient) {}

  create(dentista: IDentista): Observable<EntityResponseType> {
    return this.http.post<IDentista>(this.resourceUrl, dentista, { observe: 'response' });
  }

  update(dentista: IDentista): Observable<EntityResponseType> {
    return this.http.put<IDentista>(this.resourceUrl, dentista, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDentista>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDentista[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
