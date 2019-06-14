import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDentista } from 'app/shared/model/dentista.model';
import { AccountService } from 'app/core';
import { DentistaService } from './dentista.service';

@Component({
  selector: 'jhi-dentista',
  templateUrl: './dentista.component.html'
})
export class DentistaComponent implements OnInit, OnDestroy {
  dentistas: IDentista[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected dentistaService: DentistaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.dentistaService
      .query()
      .pipe(
        filter((res: HttpResponse<IDentista[]>) => res.ok),
        map((res: HttpResponse<IDentista[]>) => res.body)
      )
      .subscribe(
        (res: IDentista[]) => {
          this.dentistas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDentistas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDentista) {
    return item.id;
  }

  registerChangeInDentistas() {
    this.eventSubscriber = this.eventManager.subscribe('dentistaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
