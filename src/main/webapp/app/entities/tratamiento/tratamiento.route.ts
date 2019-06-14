import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Tratamiento } from 'app/shared/model/tratamiento.model';
import { TratamientoService } from './tratamiento.service';
import { TratamientoComponent } from './tratamiento.component';
import { TratamientoDetailComponent } from './tratamiento-detail.component';
import { TratamientoUpdateComponent } from './tratamiento-update.component';
import { TratamientoDeletePopupComponent } from './tratamiento-delete-dialog.component';
import { ITratamiento } from 'app/shared/model/tratamiento.model';

@Injectable({ providedIn: 'root' })
export class TratamientoResolve implements Resolve<ITratamiento> {
  constructor(private service: TratamientoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITratamiento> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Tratamiento>) => response.ok),
        map((tratamiento: HttpResponse<Tratamiento>) => tratamiento.body)
      );
    }
    return of(new Tratamiento());
  }
}

export const tratamientoRoute: Routes = [
  {
    path: '',
    component: TratamientoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.tratamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TratamientoDetailComponent,
    resolve: {
      tratamiento: TratamientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.tratamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TratamientoUpdateComponent,
    resolve: {
      tratamiento: TratamientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.tratamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TratamientoUpdateComponent,
    resolve: {
      tratamiento: TratamientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.tratamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tratamientoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TratamientoDeletePopupComponent,
    resolve: {
      tratamiento: TratamientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.tratamiento.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
