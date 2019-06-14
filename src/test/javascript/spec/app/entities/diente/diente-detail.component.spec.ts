/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OdontTestModule } from '../../../test.module';
import { DienteDetailComponent } from 'app/entities/diente/diente-detail.component';
import { Diente } from 'app/shared/model/diente.model';

describe('Component Tests', () => {
  describe('Diente Management Detail Component', () => {
    let comp: DienteDetailComponent;
    let fixture: ComponentFixture<DienteDetailComponent>;
    const route = ({ data: of({ diente: new Diente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [DienteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DienteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DienteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.diente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
