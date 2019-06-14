/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OdontTestModule } from '../../../test.module';
import { FichaDetailComponent } from 'app/entities/ficha/ficha-detail.component';
import { Ficha } from 'app/shared/model/ficha.model';

describe('Component Tests', () => {
  describe('Ficha Management Detail Component', () => {
    let comp: FichaDetailComponent;
    let fixture: ComponentFixture<FichaDetailComponent>;
    const route = ({ data: of({ ficha: new Ficha(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [FichaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FichaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FichaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ficha).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
