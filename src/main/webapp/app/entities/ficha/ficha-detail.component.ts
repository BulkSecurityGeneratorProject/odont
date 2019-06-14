import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFicha } from 'app/shared/model/ficha.model';

@Component({
  selector: 'jhi-ficha-detail',
  templateUrl: './ficha-detail.component.html'
})
export class FichaDetailComponent implements OnInit {
  ficha: IFicha;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ficha }) => {
      this.ficha = ficha;
    });
  }

  previousState() {
    window.history.back();
  }
}
