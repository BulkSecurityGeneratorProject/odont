import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPaciente } from 'app/shared/model/paciente.model';
import { AccountService } from 'app/core';
import { PacienteService } from './paciente.service';

@Component({
  selector: 'jhi-paciente',
  templateUrl: './paciente.component.html'
})
export class PacienteComponent implements OnInit, OnDestroy {
  pacientes: IPaciente[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pacienteService: PacienteService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pacienteService
      .query()
      .pipe(
        filter((res: HttpResponse<IPaciente[]>) => res.ok),
        map((res: HttpResponse<IPaciente[]>) => res.body)
      )
      .subscribe(
        (res: IPaciente[]) => {
          this.pacientes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPacientes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPaciente) {
    return item.id;
  }

  registerChangeInPacientes() {
    this.eventSubscriber = this.eventManager.subscribe('pacienteListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
