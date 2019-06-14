import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FichaDetalle } from 'app/shared/model/ficha-detalle.model';
import { FichaDetalleService } from './ficha-detalle.service';
import { FichaDetalleComponent } from './ficha-detalle.component';
import { FichaDetalleDetailComponent } from './ficha-detalle-detail.component';
import { FichaDetalleUpdateComponent } from './ficha-detalle-update.component';
import { FichaDetalleDeletePopupComponent } from './ficha-detalle-delete-dialog.component';
import { IFichaDetalle } from 'app/shared/model/ficha-detalle.model';

@Injectable({ providedIn: 'root' })
export class FichaDetalleResolve implements Resolve<IFichaDetalle> {
  constructor(private service: FichaDetalleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFichaDetalle> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FichaDetalle>) => response.ok),
        map((fichaDetalle: HttpResponse<FichaDetalle>) => fichaDetalle.body)
      );
    }
    return of(new FichaDetalle());
  }
}

export const fichaDetalleRoute: Routes = [
  {
    path: '',
    component: FichaDetalleComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.fichaDetalle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FichaDetalleDetailComponent,
    resolve: {
      fichaDetalle: FichaDetalleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.fichaDetalle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FichaDetalleUpdateComponent,
    resolve: {
      fichaDetalle: FichaDetalleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.fichaDetalle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FichaDetalleUpdateComponent,
    resolve: {
      fichaDetalle: FichaDetalleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.fichaDetalle.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fichaDetallePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FichaDetalleDeletePopupComponent,
    resolve: {
      fichaDetalle: FichaDetalleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.fichaDetalle.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
