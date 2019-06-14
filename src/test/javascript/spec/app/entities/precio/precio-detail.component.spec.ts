/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OdontTestModule } from '../../../test.module';
import { PrecioDetailComponent } from 'app/entities/precio/precio-detail.component';
import { Precio } from 'app/shared/model/precio.model';

describe('Component Tests', () => {
  describe('Precio Management Detail Component', () => {
    let comp: PrecioDetailComponent;
    let fixture: ComponentFixture<PrecioDetailComponent>;
    const route = ({ data: of({ precio: new Precio(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [PrecioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PrecioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrecioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.precio).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
