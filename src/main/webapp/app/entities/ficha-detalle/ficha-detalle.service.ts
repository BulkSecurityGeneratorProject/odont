import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFichaDetalle } from 'app/shared/model/ficha-detalle.model';

type EntityResponseType = HttpResponse<IFichaDetalle>;
type EntityArrayResponseType = HttpResponse<IFichaDetalle[]>;

@Injectable({ providedIn: 'root' })
export class FichaDetalleService {
  public resourceUrl = SERVER_API_URL + 'api/ficha-detalles';

  constructor(protected http: HttpClient) {}

  create(fichaDetalle: IFichaDetalle): Observable<EntityResponseType> {
    return this.http.post<IFichaDetalle>(this.resourceUrl, fichaDetalle, { observe: 'response' });
  }

  update(fichaDetalle: IFichaDetalle): Observable<EntityResponseType> {
    return this.http.put<IFichaDetalle>(this.resourceUrl, fichaDetalle, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFichaDetalle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFichaDetalle[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
