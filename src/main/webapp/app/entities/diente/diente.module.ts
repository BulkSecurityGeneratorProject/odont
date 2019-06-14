import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OdontSharedModule } from 'app/shared';
import {
  DienteComponent,
  DienteDetailComponent,
  DienteUpdateComponent,
  DienteDeletePopupComponent,
  DienteDeleteDialogComponent,
  dienteRoute,
  dientePopupRoute
} from './';

const ENTITY_STATES = [...dienteRoute, ...dientePopupRoute];

@NgModule({
  imports: [OdontSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [DienteComponent, DienteDetailComponent, DienteUpdateComponent, DienteDeleteDialogComponent, DienteDeletePopupComponent],
  entryComponents: [DienteComponent, DienteUpdateComponent, DienteDeleteDialogComponent, DienteDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OdontDienteModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
