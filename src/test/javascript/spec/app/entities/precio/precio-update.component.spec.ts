/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { OdontTestModule } from '../../../test.module';
import { PrecioUpdateComponent } from 'app/entities/precio/precio-update.component';
import { PrecioService } from 'app/entities/precio/precio.service';
import { Precio } from 'app/shared/model/precio.model';

describe('Component Tests', () => {
  describe('Precio Management Update Component', () => {
    let comp: PrecioUpdateComponent;
    let fixture: ComponentFixture<PrecioUpdateComponent>;
    let service: PrecioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [PrecioUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PrecioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrecioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrecioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Precio(123);
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
        const entity = new Precio();
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
