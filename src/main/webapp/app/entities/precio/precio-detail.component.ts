import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrecio } from 'app/shared/model/precio.model';

@Component({
  selector: 'jhi-precio-detail',
  templateUrl: './precio-detail.component.html'
})
export class PrecioDetailComponent implements OnInit {
  precio: IPrecio;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ precio }) => {
      this.precio = precio;
    });
  }

  previousState() {
    window.history.back();
  }
}
