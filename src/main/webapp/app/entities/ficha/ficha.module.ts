import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OdontSharedModule } from 'app/shared';
import {
  FichaComponent,
  FichaDetailComponent,
  FichaUpdateComponent,
  FichaDeletePopupComponent,
  FichaDeleteDialogComponent,
  fichaRoute,
  fichaPopupRoute
} from './';

const ENTITY_STATES = [...fichaRoute, ...fichaPopupRoute];

@NgModule({
  imports: [OdontSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [FichaComponent, FichaDetailComponent, FichaUpdateComponent, FichaDeleteDialogComponent, FichaDeletePopupComponent],
  entryComponents: [FichaComponent, FichaUpdateComponent, FichaDeleteDialogComponent, FichaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OdontFichaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
