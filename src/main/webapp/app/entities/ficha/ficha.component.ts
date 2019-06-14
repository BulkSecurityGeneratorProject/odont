import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFicha } from 'app/shared/model/ficha.model';
import { AccountService } from 'app/core';
import { FichaService } from './ficha.service';

@Component({
  selector: 'jhi-ficha',
  templateUrl: './ficha.component.html'
})
export class FichaComponent implements OnInit, OnDestroy {
  fichas: IFicha[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected fichaService: FichaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.fichaService
      .query()
      .pipe(
        filter((res: HttpResponse<IFicha[]>) => res.ok),
        map((res: HttpResponse<IFicha[]>) => res.body)
      )
      .subscribe(
        (res: IFicha[]) => {
          this.fichas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFichas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFicha) {
    return item.id;
  }

  registerChangeInFichas() {
    this.eventSubscriber = this.eventManager.subscribe('fichaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
