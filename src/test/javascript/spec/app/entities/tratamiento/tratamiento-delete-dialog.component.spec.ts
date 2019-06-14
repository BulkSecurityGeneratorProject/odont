/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OdontTestModule } from '../../../test.module';
import { TratamientoDeleteDialogComponent } from 'app/entities/tratamiento/tratamiento-delete-dialog.component';
import { TratamientoService } from 'app/entities/tratamiento/tratamiento.service';

describe('Component Tests', () => {
  describe('Tratamiento Management Delete Component', () => {
    let comp: TratamientoDeleteDialogComponent;
    let fixture: ComponentFixture<TratamientoDeleteDialogComponent>;
    let service: TratamientoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [TratamientoDeleteDialogComponent]
      })
        .overrideTemplate(TratamientoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TratamientoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TratamientoService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
