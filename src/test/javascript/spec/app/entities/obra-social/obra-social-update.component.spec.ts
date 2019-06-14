/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { OdontTestModule } from '../../../test.module';
import { ObraSocialUpdateComponent } from 'app/entities/obra-social/obra-social-update.component';
import { ObraSocialService } from 'app/entities/obra-social/obra-social.service';
import { ObraSocial } from 'app/shared/model/obra-social.model';

describe('Component Tests', () => {
  describe('ObraSocial Management Update Component', () => {
    let comp: ObraSocialUpdateComponent;
    let fixture: ComponentFixture<ObraSocialUpdateComponent>;
    let service: ObraSocialService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [ObraSocialUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ObraSocialUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ObraSocialUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ObraSocialService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ObraSocial(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ObraSocial();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
