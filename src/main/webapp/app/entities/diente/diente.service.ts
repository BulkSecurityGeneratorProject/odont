import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDiente } from 'app/shared/model/diente.model';

type EntityResponseType = HttpResponse<IDiente>;
type EntityArrayResponseType = HttpResponse<IDiente[]>;

@Injectable({ providedIn: 'root' })
export class DienteService {
  public resourceUrl = SERVER_API_URL + 'api/dientes';

  constructor(protected http: HttpClient) {}

  create(diente: IDiente): Observable<EntityResponseType> {
    return this.http.post<IDiente>(this.resourceUrl, diente, { observe: 'response' });
  }

  update(diente: IDiente): Observable<EntityResponseType> {
    return this.http.put<IDiente>(this.resourceUrl, diente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDiente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDiente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
