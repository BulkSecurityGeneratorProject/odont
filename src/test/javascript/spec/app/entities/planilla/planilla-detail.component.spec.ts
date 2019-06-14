/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OdontTestModule } from '../../../test.module';
import { PlanillaDetailComponent } from 'app/entities/planilla/planilla-detail.component';
import { Planilla } from 'app/shared/model/planilla.model';

describe('Component Tests', () => {
  describe('Planilla Management Detail Component', () => {
    let comp: PlanillaDetailComponent;
    let fixture: ComponentFixture<PlanillaDetailComponent>;
    const route = ({ data: of({ planilla: new Planilla(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [PlanillaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PlanillaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlanillaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.planilla).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
