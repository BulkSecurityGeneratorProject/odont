import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { OdontSharedLibsModule, OdontSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [OdontSharedLibsModule, OdontSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [OdontSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OdontSharedModule {
  static forRoot() {
    return {
      ngModule: OdontSharedModule
    };
  }
}
