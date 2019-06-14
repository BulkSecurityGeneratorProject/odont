import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OdontSharedModule } from 'app/shared';
import {
  PlanillaComponent,
  PlanillaDetailComponent,
  PlanillaUpdateComponent,
  PlanillaDeletePopupComponent,
  PlanillaDeleteDialogComponent,
  planillaRoute,
  planillaPopupRoute
} from './';

const ENTITY_STATES = [...planillaRoute, ...planillaPopupRoute];

@NgModule({
  imports: [OdontSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PlanillaComponent,
    PlanillaDetailComponent,
    PlanillaUpdateComponent,
    PlanillaDeleteDialogComponent,
    PlanillaDeletePopupComponent
  ],
  entryComponents: [PlanillaComponent, PlanillaUpdateComponent, PlanillaDeleteDialogComponent, PlanillaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OdontPlanillaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
