/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OdontTestModule } from '../../../test.module';
import { DentistaComponent } from 'app/entities/dentista/dentista.component';
import { DentistaService } from 'app/entities/dentista/dentista.service';
import { Dentista } from 'app/shared/model/dentista.model';

describe('Component Tests', () => {
  describe('Dentista Management Component', () => {
    let comp: DentistaComponent;
    let fixture: ComponentFixture<DentistaComponent>;
    let service: DentistaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [DentistaComponent],
        providers: []
      })
        .overrideTemplate(DentistaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DentistaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DentistaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Dentista(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dentistas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
