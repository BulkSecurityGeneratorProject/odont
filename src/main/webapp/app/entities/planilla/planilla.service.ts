import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPlanilla } from 'app/shared/model/planilla.model';

type EntityResponseType = HttpResponse<IPlanilla>;
type EntityArrayResponseType = HttpResponse<IPlanilla[]>;

@Injectable({ providedIn: 'root' })
export class PlanillaService {
  public resourceUrl = SERVER_API_URL + 'api/planillas';

  constructor(protected http: HttpClient) {}

  create(planilla: IPlanilla): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(planilla);
    return this.http
      .post<IPlanilla>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(planilla: IPlanilla): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(planilla);
    return this.http
      .put<IPlanilla>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlanilla>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlanilla[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(planilla: IPlanilla): IPlanilla {
    const copy: IPlanilla = Object.assign({}, planilla, {
      fechaDesde: planilla.fechaDesde != null && planilla.fechaDesde.isValid() ? planilla.fechaDesde.format(DATE_FORMAT) : null,
      fechaHasta: planilla.fechaHasta != null && planilla.fechaHasta.isValid() ? planilla.fechaHasta.format(DATE_FORMAT) : null
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
      res.body.forEach((planilla: IPlanilla) => {
        planilla.fechaDesde = planilla.fechaDesde != null ? moment(planilla.fechaDesde) : null;
        planilla.fechaHasta = planilla.fechaHasta != null ? moment(planilla.fechaHasta) : null;
      });
    }
    return res;
  }
}
