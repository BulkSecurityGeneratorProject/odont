import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OdontSharedModule } from 'app/shared';
import {
  PacienteComponent,
  PacienteDetailComponent,
  PacienteUpdateComponent,
  PacienteDeletePopupComponent,
  PacienteDeleteDialogComponent,
  pacienteRoute,
  pacientePopupRoute
} from './';

const ENTITY_STATES = [...pacienteRoute, ...pacientePopupRoute];

@NgModule({
  imports: [OdontSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PacienteComponent,
    PacienteDetailComponent,
    PacienteUpdateComponent,
    PacienteDeleteDialogComponent,
    PacienteDeletePopupComponent
  ],
  entryComponents: [PacienteComponent, PacienteUpdateComponent, PacienteDeleteDialogComponent, PacienteDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OdontPacienteModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
