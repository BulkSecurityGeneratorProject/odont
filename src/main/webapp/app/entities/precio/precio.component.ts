import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPrecio } from 'app/shared/model/precio.model';
import { AccountService } from 'app/core';
import { PrecioService } from './precio.service';

@Component({
  selector: 'jhi-precio',
  templateUrl: './precio.component.html'
})
export class PrecioComponent implements OnInit, OnDestroy {
  precios: IPrecio[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected precioService: PrecioService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.precioService
      .query()
      .pipe(
        filter((res: HttpResponse<IPrecio[]>) => res.ok),
        map((res: HttpResponse<IPrecio[]>) => res.body)
      )
      .subscribe(
        (res: IPrecio[]) => {
          this.precios = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPrecios();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPrecio) {
    return item.id;
  }

  registerChangeInPrecios() {
    this.eventSubscriber = this.eventManager.subscribe('precioListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
