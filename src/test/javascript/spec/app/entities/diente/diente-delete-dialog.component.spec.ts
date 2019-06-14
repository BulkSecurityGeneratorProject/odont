/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OdontTestModule } from '../../../test.module';
import { DienteDeleteDialogComponent } from 'app/entities/diente/diente-delete-dialog.component';
import { DienteService } from 'app/entities/diente/diente.service';

describe('Component Tests', () => {
  describe('Diente Management Delete Component', () => {
    let comp: DienteDeleteDialogComponent;
    let fixture: ComponentFixture<DienteDeleteDialogComponent>;
    let service: DienteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [DienteDeleteDialogComponent]
      })
        .overrideTemplate(DienteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DienteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DienteService);
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
