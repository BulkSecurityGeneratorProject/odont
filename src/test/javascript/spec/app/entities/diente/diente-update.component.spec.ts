/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { OdontTestModule } from '../../../test.module';
import { DienteUpdateComponent } from 'app/entities/diente/diente-update.component';
import { DienteService } from 'app/entities/diente/diente.service';
import { Diente } from 'app/shared/model/diente.model';

describe('Component Tests', () => {
  describe('Diente Management Update Component', () => {
    let comp: DienteUpdateComponent;
    let fixture: ComponentFixture<DienteUpdateComponent>;
    let service: DienteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [DienteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DienteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DienteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DienteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Diente(123);
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
        const entity = new Diente();
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
