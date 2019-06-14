import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { OdontSharedModule } from 'app/shared';
import {
  TratamientoComponent,
  TratamientoDetailComponent,
  TratamientoUpdateComponent,
  TratamientoDeletePopupComponent,
  TratamientoDeleteDialogComponent,
  tratamientoRoute,
  tratamientoPopupRoute
} from './';

const ENTITY_STATES = [...tratamientoRoute, ...tratamientoPopupRoute];

@NgModule({
  imports: [OdontSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TratamientoComponent,
    TratamientoDetailComponent,
    TratamientoUpdateComponent,
    TratamientoDeleteDialogComponent,
    TratamientoDeletePopupComponent
  ],
  entryComponents: [TratamientoComponent, TratamientoUpdateComponent, TratamientoDeleteDialogComponent, TratamientoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OdontTratamientoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
