/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OdontTestModule } from '../../../test.module';
import { ObraSocialDeleteDialogComponent } from 'app/entities/obra-social/obra-social-delete-dialog.component';
import { ObraSocialService } from 'app/entities/obra-social/obra-social.service';

describe('Component Tests', () => {
  describe('ObraSocial Management Delete Component', () => {
    let comp: ObraSocialDeleteDialogComponent;
    let fixture: ComponentFixture<ObraSocialDeleteDialogComponent>;
    let service: ObraSocialService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [ObraSocialDeleteDialogComponent]
      })
        .overrideTemplate(ObraSocialDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ObraSocialDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ObraSocialService);
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
