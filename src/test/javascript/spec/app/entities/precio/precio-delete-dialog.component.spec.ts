/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OdontTestModule } from '../../../test.module';
import { PrecioDeleteDialogComponent } from 'app/entities/precio/precio-delete-dialog.component';
import { PrecioService } from 'app/entities/precio/precio.service';

describe('Component Tests', () => {
  describe('Precio Management Delete Component', () => {
    let comp: PrecioDeleteDialogComponent;
    let fixture: ComponentFixture<PrecioDeleteDialogComponent>;
    let service: PrecioService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [PrecioDeleteDialogComponent]
      })
        .overrideTemplate(PrecioDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrecioDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrecioService);
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
