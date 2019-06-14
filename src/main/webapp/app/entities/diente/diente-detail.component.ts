import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDiente } from 'app/shared/model/diente.model';

@Component({
  selector: 'jhi-diente-detail',
  templateUrl: './diente-detail.component.html'
})
export class DienteDetailComponent implements OnInit {
  diente: IDiente;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ diente }) => {
      this.diente = diente;
    });
  }

  previousState() {
    window.history.back();
  }
}
