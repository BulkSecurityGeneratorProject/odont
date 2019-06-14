import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFichaDetalle } from 'app/shared/model/ficha-detalle.model';
import { AccountService } from 'app/core';
import { FichaDetalleService } from './ficha-detalle.service';

@Component({
  selector: 'jhi-ficha-detalle',
  templateUrl: './ficha-detalle.component.html'
})
export class FichaDetalleComponent implements OnInit, OnDestroy {
  fichaDetalles: IFichaDetalle[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected fichaDetalleService: FichaDetalleService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.fichaDetalleService
      .query()
      .pipe(
        filter((res: HttpResponse<IFichaDetalle[]>) => res.ok),
        map((res: HttpResponse<IFichaDetalle[]>) => res.body)
      )
      .subscribe(
        (res: IFichaDetalle[]) => {
          this.fichaDetalles = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFichaDetalles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFichaDetalle) {
    return item.id;
  }

  registerChangeInFichaDetalles() {
    this.eventSubscriber = this.eventManager.subscribe('fichaDetalleListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
