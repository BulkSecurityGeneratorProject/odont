import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OdontSharedModule } from 'app/shared';
import {
  FichaDetalleComponent,
  FichaDetalleDetailComponent,
  FichaDetalleUpdateComponent,
  FichaDetalleDeletePopupComponent,
  FichaDetalleDeleteDialogComponent,
  fichaDetalleRoute,
  fichaDetallePopupRoute
} from './';

const ENTITY_STATES = [...fichaDetalleRoute, ...fichaDetallePopupRoute];

@NgModule({
  imports: [OdontSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FichaDetalleComponent,
    FichaDetalleDetailComponent,
    FichaDetalleUpdateComponent,
    FichaDetalleDeleteDialogComponent,
    FichaDetalleDeletePopupComponent
  ],
  entryComponents: [
    FichaDetalleComponent,
    FichaDetalleUpdateComponent,
    FichaDetalleDeleteDialogComponent,
    FichaDetalleDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OdontFichaDetalleModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
