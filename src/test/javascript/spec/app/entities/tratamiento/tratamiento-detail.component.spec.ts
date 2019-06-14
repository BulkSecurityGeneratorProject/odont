/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OdontTestModule } from '../../../test.module';
import { TratamientoDetailComponent } from 'app/entities/tratamiento/tratamiento-detail.component';
import { Tratamiento } from 'app/shared/model/tratamiento.model';

describe('Component Tests', () => {
  describe('Tratamiento Management Detail Component', () => {
    let comp: TratamientoDetailComponent;
    let fixture: ComponentFixture<TratamientoDetailComponent>;
    const route = ({ data: of({ tratamiento: new Tratamiento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [TratamientoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TratamientoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TratamientoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tratamiento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
