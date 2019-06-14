import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDiente } from 'app/shared/model/diente.model';
import { AccountService } from 'app/core';
import { DienteService } from './diente.service';

@Component({
  selector: 'jhi-diente',
  templateUrl: './diente.component.html'
})
export class DienteComponent implements OnInit, OnDestroy {
  dientes: IDiente[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected dienteService: DienteService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.dienteService
      .query()
      .pipe(
        filter((res: HttpResponse<IDiente[]>) => res.ok),
        map((res: HttpResponse<IDiente[]>) => res.body)
      )
      .subscribe(
        (res: IDiente[]) => {
          this.dientes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDientes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDiente) {
    return item.id;
  }

  registerChangeInDientes() {
    this.eventSubscriber = this.eventManager.subscribe('dienteListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
