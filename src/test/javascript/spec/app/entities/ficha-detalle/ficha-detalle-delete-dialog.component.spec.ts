/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OdontTestModule } from '../../../test.module';
import { FichaDetalleDeleteDialogComponent } from 'app/entities/ficha-detalle/ficha-detalle-delete-dialog.component';
import { FichaDetalleService } from 'app/entities/ficha-detalle/ficha-detalle.service';

describe('Component Tests', () => {
  describe('FichaDetalle Management Delete Component', () => {
    let comp: FichaDetalleDeleteDialogComponent;
    let fixture: ComponentFixture<FichaDetalleDeleteDialogComponent>;
    let service: FichaDetalleService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [FichaDetalleDeleteDialogComponent]
      })
        .overrideTemplate(FichaDetalleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FichaDetalleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FichaDetalleService);
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
