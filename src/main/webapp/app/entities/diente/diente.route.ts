import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Diente } from 'app/shared/model/diente.model';
import { DienteService } from './diente.service';
import { DienteComponent } from './diente.component';
import { DienteDetailComponent } from './diente-detail.component';
import { DienteUpdateComponent } from './diente-update.component';
import { DienteDeletePopupComponent } from './diente-delete-dialog.component';
import { IDiente } from 'app/shared/model/diente.model';

@Injectable({ providedIn: 'root' })
export class DienteResolve implements Resolve<IDiente> {
  constructor(private service: DienteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDiente> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Diente>) => response.ok),
        map((diente: HttpResponse<Diente>) => diente.body)
      );
    }
    return of(new Diente());
  }
}

export const dienteRoute: Routes = [
  {
    path: '',
    component: DienteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.diente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DienteDetailComponent,
    resolve: {
      diente: DienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.diente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DienteUpdateComponent,
    resolve: {
      diente: DienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.diente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DienteUpdateComponent,
    resolve: {
      diente: DienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.diente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dientePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DienteDeletePopupComponent,
    resolve: {
      diente: DienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.diente.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
