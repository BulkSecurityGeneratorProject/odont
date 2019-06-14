import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITratamiento } from 'app/shared/model/tratamiento.model';

@Component({
  selector: 'jhi-tratamiento-detail',
  templateUrl: './tratamiento-detail.component.html'
})
export class TratamientoDetailComponent implements OnInit {
  tratamiento: ITratamiento;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tratamiento }) => {
      this.tratamiento = tratamiento;
    });
  }

  previousState() {
    window.history.back();
  }
}
