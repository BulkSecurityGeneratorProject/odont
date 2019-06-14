import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Ficha } from 'app/shared/model/ficha.model';
import { FichaService } from './ficha.service';
import { FichaComponent } from './ficha.component';
import { FichaDetailComponent } from './ficha-detail.component';
import { FichaUpdateComponent } from './ficha-update.component';
import { FichaDeletePopupComponent } from './ficha-delete-dialog.component';
import { IFicha } from 'app/shared/model/ficha.model';

@Injectable({ providedIn: 'root' })
export class FichaResolve implements Resolve<IFicha> {
  constructor(private service: FichaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFicha> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Ficha>) => response.ok),
        map((ficha: HttpResponse<Ficha>) => ficha.body)
      );
    }
    return of(new Ficha());
  }
}

export const fichaRoute: Routes = [
  {
    path: '',
    component: FichaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.ficha.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FichaDetailComponent,
    resolve: {
      ficha: FichaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.ficha.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FichaUpdateComponent,
    resolve: {
      ficha: FichaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.ficha.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FichaUpdateComponent,
    resolve: {
      ficha: FichaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.ficha.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fichaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FichaDeletePopupComponent,
    resolve: {
      ficha: FichaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.ficha.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
