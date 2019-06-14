/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OdontTestModule } from '../../../test.module';
import { PlanillaComponent } from 'app/entities/planilla/planilla.component';
import { PlanillaService } from 'app/entities/planilla/planilla.service';
import { Planilla } from 'app/shared/model/planilla.model';

describe('Component Tests', () => {
  describe('Planilla Management Component', () => {
    let comp: PlanillaComponent;
    let fixture: ComponentFixture<PlanillaComponent>;
    let service: PlanillaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [PlanillaComponent],
        providers: []
      })
        .overrideTemplate(PlanillaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlanillaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlanillaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Planilla(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.planillas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
