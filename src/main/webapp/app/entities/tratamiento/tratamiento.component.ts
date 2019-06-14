import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITratamiento } from 'app/shared/model/tratamiento.model';
import { AccountService } from 'app/core';
import { TratamientoService } from './tratamiento.service';

@Component({
  selector: 'jhi-tratamiento',
  templateUrl: './tratamiento.component.html'
})
export class TratamientoComponent implements OnInit, OnDestroy {
  tratamientos: ITratamiento[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected tratamientoService: TratamientoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.tratamientoService
      .query()
      .pipe(
        filter((res: HttpResponse<ITratamiento[]>) => res.ok),
        map((res: HttpResponse<ITratamiento[]>) => res.body)
      )
      .subscribe(
        (res: ITratamiento[]) => {
          this.tratamientos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTratamientos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITratamiento) {
    return item.id;
  }

  registerChangeInTratamientos() {
    this.eventSubscriber = this.eventManager.subscribe('tratamientoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
