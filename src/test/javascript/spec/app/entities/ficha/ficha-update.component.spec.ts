/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { OdontTestModule } from '../../../test.module';
import { FichaUpdateComponent } from 'app/entities/ficha/ficha-update.component';
import { FichaService } from 'app/entities/ficha/ficha.service';
import { Ficha } from 'app/shared/model/ficha.model';

describe('Component Tests', () => {
  describe('Ficha Management Update Component', () => {
    let comp: FichaUpdateComponent;
    let fixture: ComponentFixture<FichaUpdateComponent>;
    let service: FichaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [FichaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FichaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FichaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FichaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Ficha(123);
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
        const entity = new Ficha();
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
