import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Planilla } from 'app/shared/model/planilla.model';
import { PlanillaService } from './planilla.service';
import { PlanillaComponent } from './planilla.component';
import { PlanillaDetailComponent } from './planilla-detail.component';
import { PlanillaUpdateComponent } from './planilla-update.component';
import { PlanillaDeletePopupComponent } from './planilla-delete-dialog.component';
import { IPlanilla } from 'app/shared/model/planilla.model';

@Injectable({ providedIn: 'root' })
export class PlanillaResolve implements Resolve<IPlanilla> {
  constructor(private service: PlanillaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPlanilla> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Planilla>) => response.ok),
        map((planilla: HttpResponse<Planilla>) => planilla.body)
      );
    }
    return of(new Planilla());
  }
}

export const planillaRoute: Routes = [
  {
    path: '',
    component: PlanillaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.planilla.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlanillaDetailComponent,
    resolve: {
      planilla: PlanillaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.planilla.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlanillaUpdateComponent,
    resolve: {
      planilla: PlanillaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.planilla.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlanillaUpdateComponent,
    resolve: {
      planilla: PlanillaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.planilla.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const planillaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PlanillaDeletePopupComponent,
    resolve: {
      planilla: PlanillaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.planilla.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
