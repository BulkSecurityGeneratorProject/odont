/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OdontTestModule } from '../../../test.module';
import { DentistaDetailComponent } from 'app/entities/dentista/dentista-detail.component';
import { Dentista } from 'app/shared/model/dentista.model';

describe('Component Tests', () => {
  describe('Dentista Management Detail Component', () => {
    let comp: DentistaDetailComponent;
    let fixture: ComponentFixture<DentistaDetailComponent>;
    const route = ({ data: of({ dentista: new Dentista(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OdontTestModule],
        declarations: [DentistaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DentistaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DentistaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dentista).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
