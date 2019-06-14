/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { OdontTestModule } from '../../../test.module';
import { FichaDetalleUpdateComponent } from 'app/entities/ficha-detalle/ficha-detalle-update.component';
import { FichaDetalleService } from 'app/entities/ficha-detalle/ficha-detalle.service';
import { FichaDetalle } from 'app/shared/model/ficha-detalle.model';

describe('Component Tests', () => {
  describe('FichaDetalle Management Update Component', () => {
    let comp: FichaDetalleUpdateComponent;
    let fixture: ComponentFixture<FichaDetalleUpdateComponent>;
    let service: FichaDetalleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [FichaDetalleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FichaDetalleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FichaDetalleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FichaDetalleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FichaDetalle(123);
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
        const entity = new FichaDetalle();
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
