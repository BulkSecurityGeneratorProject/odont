import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ObraSocial } from 'app/shared/model/obra-social.model';
import { ObraSocialService } from './obra-social.service';
import { ObraSocialComponent } from './obra-social.component';
import { ObraSocialDetailComponent } from './obra-social-detail.component';
import { ObraSocialUpdateComponent } from './obra-social-update.component';
import { ObraSocialDeletePopupComponent } from './obra-social-delete-dialog.component';
import { IObraSocial } from 'app/shared/model/obra-social.model';

@Injectable({ providedIn: 'root' })
export class ObraSocialResolve implements Resolve<IObraSocial> {
  constructor(private service: ObraSocialService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IObraSocial> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ObraSocial>) => response.ok),
        map((obraSocial: HttpResponse<ObraSocial>) => obraSocial.body)
      );
    }
    return of(new ObraSocial());
  }
}

export const obraSocialRoute: Routes = [
  {
    path: '',
    component: ObraSocialComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.obraSocial.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ObraSocialDetailComponent,
    resolve: {
      obraSocial: ObraSocialResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.obraSocial.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ObraSocialUpdateComponent,
    resolve: {
      obraSocial: ObraSocialResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.obraSocial.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ObraSocialUpdateComponent,
    resolve: {
      obraSocial: ObraSocialResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.obraSocial.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const obraSocialPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ObraSocialDeletePopupComponent,
    resolve: {
      obraSocial: ObraSocialResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.obraSocial.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
