/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OdontTestModule } from '../../../test.module';
import { FichaComponent } from 'app/entities/ficha/ficha.component';
import { FichaService } from 'app/entities/ficha/ficha.service';
import { Ficha } from 'app/shared/model/ficha.model';

describe('Component Tests', () => {
  describe('Ficha Management Component', () => {
    let comp: FichaComponent;
    let fixture: ComponentFixture<FichaComponent>;
    let service: FichaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [FichaComponent],
        providers: []
      })
        .overrideTemplate(FichaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FichaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FichaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Ficha(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fichas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
