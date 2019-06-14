/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OdontTestModule } from '../../../test.module';
import { ObraSocialDetailComponent } from 'app/entities/obra-social/obra-social-detail.component';
import { ObraSocial } from 'app/shared/model/obra-social.model';

describe('Component Tests', () => {
  describe('ObraSocial Management Detail Component', () => {
    let comp: ObraSocialDetailComponent;
    let fixture: ComponentFixture<ObraSocialDetailComponent>;
    const route = ({ data: of({ obraSocial: new ObraSocial(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [ObraSocialDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ObraSocialDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ObraSocialDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.obraSocial).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
