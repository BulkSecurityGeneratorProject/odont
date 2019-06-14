/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { OdontTestModule } from '../../../test.module';
import { DentistaUpdateComponent } from 'app/entities/dentista/dentista-update.component';
import { DentistaService } from 'app/entities/dentista/dentista.service';
import { Dentista } from 'app/shared/model/dentista.model';

describe('Component Tests', () => {
  describe('Dentista Management Update Component', () => {
    let comp: DentistaUpdateComponent;
    let fixture: ComponentFixture<DentistaUpdateComponent>;
    let service: DentistaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [DentistaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DentistaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DentistaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DentistaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Dentista(123);
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
        const entity = new Dentista();
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
