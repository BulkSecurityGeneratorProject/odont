/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OdontTestModule } from '../../../test.module';
import { DienteComponent } from 'app/entities/diente/diente.component';
import { DienteService } from 'app/entities/diente/diente.service';
import { Diente } from 'app/shared/model/diente.model';

describe('Component Tests', () => {
  describe('Diente Management Component', () => {
    let comp: DienteComponent;
    let fixture: ComponentFixture<DienteComponent>;
    let service: DienteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [DienteComponent],
        providers: []
      })
        .overrideTemplate(DienteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DienteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DienteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Diente(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dientes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
