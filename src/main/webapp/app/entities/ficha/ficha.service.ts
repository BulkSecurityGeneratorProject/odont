import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFicha } from 'app/shared/model/ficha.model';

type EntityResponseType = HttpResponse<IFicha>;
type EntityArrayResponseType = HttpResponse<IFicha[]>;

@Injectable({ providedIn: 'root' })
export class FichaService {
  public resourceUrl = SERVER_API_URL + 'api/fichas';

  constructor(protected http: HttpClient) {}

  create(ficha: IFicha): Observable<EntityResponseType> {
    return this.http.post<IFicha>(this.resourceUrl, ficha, { observe: 'response' });
  }

  update(ficha: IFicha): Observable<EntityResponseType> {
    return this.http.put<IFicha>(this.resourceUrl, ficha, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFicha>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFicha[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
