import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFichaDetalle } from 'app/shared/model/ficha-detalle.model';

@Component({
  selector: 'jhi-ficha-detalle-detail',
  templateUrl: './ficha-detalle-detail.component.html'
})
export class FichaDetalleDetailComponent implements OnInit {
  fichaDetalle: IFichaDetalle;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fichaDetalle }) => {
      this.fichaDetalle = fichaDetalle;
    });
  }

  previousState() {
    window.history.back();
  }
}
