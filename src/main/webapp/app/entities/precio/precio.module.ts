import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OdontSharedModule } from 'app/shared';
import {
  PrecioComponent,
  PrecioDetailComponent,
  PrecioUpdateComponent,
  PrecioDeletePopupComponent,
  PrecioDeleteDialogComponent,
  precioRoute,
  precioPopupRoute
} from './';

const ENTITY_STATES = [...precioRoute, ...precioPopupRoute];

@NgModule({
  imports: [OdontSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PrecioComponent, PrecioDetailComponent, PrecioUpdateComponent, PrecioDeleteDialogComponent, PrecioDeletePopupComponent],
  entryComponents: [PrecioComponent, PrecioUpdateComponent, PrecioDeleteDialogComponent, PrecioDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OdontPrecioModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
