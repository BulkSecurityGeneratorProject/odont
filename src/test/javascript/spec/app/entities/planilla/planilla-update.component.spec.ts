/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { OdontTestModule } from '../../../test.module';
import { PlanillaUpdateComponent } from 'app/entities/planilla/planilla-update.component';
import { PlanillaService } from 'app/entities/planilla/planilla.service';
import { Planilla } from 'app/shared/model/planilla.model';

describe('Component Tests', () => {
  describe('Planilla Management Update Component', () => {
    let comp: PlanillaUpdateComponent;
    let fixture: ComponentFixture<PlanillaUpdateComponent>;
    let service: PlanillaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [PlanillaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PlanillaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlanillaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlanillaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Planilla(123);
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
        const entity = new Planilla();
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
