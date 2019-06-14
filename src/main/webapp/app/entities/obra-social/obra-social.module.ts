import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OdontSharedModule } from 'app/shared';
import {
  ObraSocialComponent,
  ObraSocialDetailComponent,
  ObraSocialUpdateComponent,
  ObraSocialDeletePopupComponent,
  ObraSocialDeleteDialogComponent,
  obraSocialRoute,
  obraSocialPopupRoute
} from './';

const ENTITY_STATES = [...obraSocialRoute, ...obraSocialPopupRoute];

@NgModule({
  imports: [OdontSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ObraSocialComponent,
    ObraSocialDetailComponent,
    ObraSocialUpdateComponent,
    ObraSocialDeleteDialogComponent,
    ObraSocialDeletePopupComponent
  ],
  entryComponents: [ObraSocialComponent, ObraSocialUpdateComponent, ObraSocialDeleteDialogComponent, ObraSocialDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OdontObraSocialModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
