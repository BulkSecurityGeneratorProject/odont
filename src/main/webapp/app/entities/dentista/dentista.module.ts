import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OdontSharedModule } from 'app/shared';
import {
  DentistaComponent,
  DentistaDetailComponent,
  DentistaUpdateComponent,
  DentistaDeletePopupComponent,
  DentistaDeleteDialogComponent,
  dentistaRoute,
  dentistaPopupRoute
} from './';

const ENTITY_STATES = [...dentistaRoute, ...dentistaPopupRoute];

@NgModule({
  imports: [OdontSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DentistaComponent,
    DentistaDetailComponent,
    DentistaUpdateComponent,
    DentistaDeleteDialogComponent,
    DentistaDeletePopupComponent
  ],
  entryComponents: [DentistaComponent, DentistaUpdateComponent, DentistaDeleteDialogComponent, DentistaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OdontDentistaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
