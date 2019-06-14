/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OdontTestModule } from '../../../test.module';
import { FichaDetalleDetailComponent } from 'app/entities/ficha-detalle/ficha-detalle-detail.component';
import { FichaDetalle } from 'app/shared/model/ficha-detalle.model';

describe('Component Tests', () => {
  describe('FichaDetalle Management Detail Component', () => {
    let comp: FichaDetalleDetailComponent;
    let fixture: ComponentFixture<FichaDetalleDetailComponent>;
    const route = ({ data: of({ fichaDetalle: new FichaDetalle(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [FichaDetalleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FichaDetalleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FichaDetalleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fichaDetalle).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
