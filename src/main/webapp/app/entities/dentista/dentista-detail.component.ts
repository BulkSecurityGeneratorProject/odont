import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDentista } from 'app/shared/model/dentista.model';

@Component({
  selector: 'jhi-dentista-detail',
  templateUrl: './dentista-detail.component.html'
})
export class DentistaDetailComponent implements OnInit {
  dentista: IDentista;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dentista }) => {
      this.dentista = dentista;
    });
  }

  previousState() {
    window.history.back();
  }
}
