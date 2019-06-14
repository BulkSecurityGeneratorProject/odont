import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPlanilla } from 'app/shared/model/planilla.model';
import { AccountService } from 'app/core';
import { PlanillaService } from './planilla.service';

@Component({
  selector: 'jhi-planilla',
  templateUrl: './planilla.component.html'
})
export class PlanillaComponent implements OnInit, OnDestroy {
  planillas: IPlanilla[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected planillaService: PlanillaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.planillaService
      .query()
      .pipe(
        filter((res: HttpResponse<IPlanilla[]>) => res.ok),
        map((res: HttpResponse<IPlanilla[]>) => res.body)
      )
      .subscribe(
        (res: IPlanilla[]) => {
          this.planillas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPlanillas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPlanilla) {
    return item.id;
  }

  registerChangeInPlanillas() {
    this.eventSubscriber = this.eventManager.subscribe('planillaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
