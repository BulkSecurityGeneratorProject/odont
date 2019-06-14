/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OdontTestModule } from '../../../test.module';
import { FichaDetalleComponent } from 'app/entities/ficha-detalle/ficha-detalle.component';
import { FichaDetalleService } from 'app/entities/ficha-detalle/ficha-detalle.service';
import { FichaDetalle } from 'app/shared/model/ficha-detalle.model';

describe('Component Tests', () => {
  describe('FichaDetalle Management Component', () => {
    let comp: FichaDetalleComponent;
    let fixture: ComponentFixture<FichaDetalleComponent>;
    let service: FichaDetalleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [FichaDetalleComponent],
        providers: []
      })
        .overrideTemplate(FichaDetalleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FichaDetalleComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FichaDetalleService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FichaDetalle(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fichaDetalles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
