import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Paciente } from 'app/shared/model/paciente.model';
import { PacienteService } from './paciente.service';
import { PacienteComponent } from './paciente.component';
import { PacienteDetailComponent } from './paciente-detail.component';
import { PacienteUpdateComponent } from './paciente-update.component';
import { PacienteDeletePopupComponent } from './paciente-delete-dialog.component';
import { IPaciente } from 'app/shared/model/paciente.model';

@Injectable({ providedIn: 'root' })
export class PacienteResolve implements Resolve<IPaciente> {
  constructor(private service: PacienteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPaciente> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Paciente>) => response.ok),
        map((paciente: HttpResponse<Paciente>) => paciente.body)
      );
    }
    return of(new Paciente());
  }
}

export const pacienteRoute: Routes = [
  {
    path: '',
    component: PacienteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.paciente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PacienteDetailComponent,
    resolve: {
      paciente: PacienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.paciente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PacienteUpdateComponent,
    resolve: {
      paciente: PacienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.paciente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PacienteUpdateComponent,
    resolve: {
      paciente: PacienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.paciente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pacientePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PacienteDeletePopupComponent,
    resolve: {
      paciente: PacienteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'odontApp.paciente.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
