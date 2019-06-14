/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OdontTestModule } from '../../../test.module';
import { PrecioComponent } from 'app/entities/precio/precio.component';
import { PrecioService } from 'app/entities/precio/precio.service';
import { Precio } from 'app/shared/model/precio.model';

describe('Component Tests', () => {
  describe('Precio Management Component', () => {
    let comp: PrecioComponent;
    let fixture: ComponentFixture<PrecioComponent>;
    let service: PrecioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [PrecioComponent],
        providers: []
      })
        .overrideTemplate(PrecioComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrecioComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrecioService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Precio(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.precios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
