/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OdontTestModule } from '../../../test.module';
import { ObraSocialComponent } from 'app/entities/obra-social/obra-social.component';
import { ObraSocialService } from 'app/entities/obra-social/obra-social.service';
import { ObraSocial } from 'app/shared/model/obra-social.model';

describe('Component Tests', () => {
  describe('ObraSocial Management Component', () => {
    let comp: ObraSocialComponent;
    let fixture: ComponentFixture<ObraSocialComponent>;
    let service: ObraSocialService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [ObraSocialComponent],
        providers: []
      })
        .overrideTemplate(ObraSocialComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ObraSocialComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ObraSocialService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ObraSocial(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.obraSocials[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
