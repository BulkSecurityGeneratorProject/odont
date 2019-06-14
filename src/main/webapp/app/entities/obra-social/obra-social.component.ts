import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IObraSocial } from 'app/shared/model/obra-social.model';
import { AccountService } from 'app/core';
import { ObraSocialService } from './obra-social.service';

@Component({
  selector: 'jhi-obra-social',
  templateUrl: './obra-social.component.html'
})
export class ObraSocialComponent implements OnInit, OnDestroy {
  obraSocials: IObraSocial[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected obraSocialService: ObraSocialService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.obraSocialService
      .query()
      .pipe(
        filter((res: HttpResponse<IObraSocial[]>) => res.ok),
        map((res: HttpResponse<IObraSocial[]>) => res.body)
      )
      .subscribe(
        (res: IObraSocial[]) => {
          this.obraSocials = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInObraSocials();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IObraSocial) {
    return item.id;
  }

  registerChangeInObraSocials() {
    this.eventSubscriber = this.eventManager.subscribe('obraSocialListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
