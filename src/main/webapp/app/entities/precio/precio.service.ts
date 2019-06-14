import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPrecio } from 'app/shared/model/precio.model';

type EntityResponseType = HttpResponse<IPrecio>;
type EntityArrayResponseType = HttpResponse<IPrecio[]>;

@Injectable({ providedIn: 'root' })
export class PrecioService {
  public resourceUrl = SERVER_API_URL + 'api/precios';

  constructor(protected http: HttpClient) {}

  create(precio: IPrecio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(precio);
    return this.http
      .post<IPrecio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(precio: IPrecio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(precio);
    return this.http
      .put<IPrecio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPrecio>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPrecio[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(precio: IPrecio): IPrecio {
    const copy: IPrecio = Object.assign({}, precio, {
      fechaDesde: precio.fechaDesde != null && precio.fechaDesde.isValid() ? precio.fechaDesde.format(DATE_FORMAT) : null,
      fechaHasta: precio.fechaHasta != null && precio.fechaHasta.isValid() ? precio.fechaHasta.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaDesde = res.body.fechaDesde != null ? moment(res.body.fechaDesde) : null;
      res.body.fechaHasta = res.body.fechaHasta != null ? moment(res.body.fechaHasta) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((precio: IPrecio) => {
        precio.fechaDesde = precio.fechaDesde != null ? moment(precio.fechaDesde) : null;
        precio.fechaHasta = precio.fechaHasta != null ? moment(precio.fechaHasta) : null;
      });
    }
    return res;
  }
}
