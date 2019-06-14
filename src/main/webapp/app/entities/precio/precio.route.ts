import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Precio } from 'app/shared/model/precio.model';
import { PrecioService } from './precio.service';
import { PrecioComponent } from './precio.component';
import { PrecioDetailComponent } from './precio-detail.component';
import { PrecioUpdateComponent } from './precio-update.component';
import { PrecioDeletePopupComponent } from './precio-delete-dialog.component';
import { IPrecio } from 'app/shared/model/precio.model';

@Injectable({ providedIn: 'root' })
export class PrecioResolve implements Resolve<IPrecio> {
  constructor(private service: PrecioService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPrecio> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Precio>) => response.ok),
        map((precio: HttpResponse<Precio>) => precio.body)
      );
    }
    return of(new Precio());
  }
}

export const precioRoute: Routes = [
  {
    path: '',
    component: PrecioComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.precio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PrecioDetailComponent,
    resolve: {
      precio: PrecioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.precio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PrecioUpdateComponent,
    resolve: {
      precio: PrecioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.precio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PrecioUpdateComponent,
    resolve: {
      precio: PrecioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.precio.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const precioPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PrecioDeletePopupComponent,
    resolve: {
      precio: PrecioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.precio.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
