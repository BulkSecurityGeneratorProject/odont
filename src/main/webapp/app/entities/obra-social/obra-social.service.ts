import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IObraSocial } from 'app/shared/model/obra-social.model';

type EntityResponseType = HttpResponse<IObraSocial>;
type EntityArrayResponseType = HttpResponse<IObraSocial[]>;

@Injectable({ providedIn: 'root' })
export class ObraSocialService {
  public resourceUrl = SERVER_API_URL + 'api/obra-socials';

  constructor(protected http: HttpClient) {}

  create(obraSocial: IObraSocial): Observable<EntityResponseType> {
    return this.http.post<IObraSocial>(this.resourceUrl, obraSocial, { observe: 'response' });
  }

  update(obraSocial: IObraSocial): Observable<EntityResponseType> {
    return this.http.put<IObraSocial>(this.resourceUrl, obraSocial, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IObraSocial>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IObraSocial[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
