import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Dentista } from 'app/shared/model/dentista.model';
import { DentistaService } from './dentista.service';
import { DentistaComponent } from './dentista.component';
import { DentistaDetailComponent } from './dentista-detail.component';
import { DentistaUpdateComponent } from './dentista-update.component';
import { DentistaDeletePopupComponent } from './dentista-delete-dialog.component';
import { IDentista } from 'app/shared/model/dentista.model';

@Injectable({ providedIn: 'root' })
export class DentistaResolve implements Resolve<IDentista> {
  constructor(private service: DentistaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDentista> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Dentista>) => response.ok),
        map((dentista: HttpResponse<Dentista>) => dentista.body)
      );
    }
    return of(new Dentista());
  }
}

export const dentistaRoute: Routes = [
  {
    path: '',
    component: DentistaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.dentista.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DentistaDetailComponent,
    resolve: {
      dentista: DentistaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.dentista.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DentistaUpdateComponent,
    resolve: {
      dentista: DentistaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.dentista.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DentistaUpdateComponent,
    resolve: {
      dentista: DentistaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.dentista.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dentistaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DentistaDeletePopupComponent,
    resolve: {
      dentista: DentistaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.dentista.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
