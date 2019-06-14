import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IObraSocial } from 'app/shared/model/obra-social.model';

@Component({
  selector: 'jhi-obra-social-detail',
  templateUrl: './obra-social-detail.component.html'
})
export class ObraSocialDetailComponent implements OnInit {
  obraSocial: IObraSocial;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ obraSocial }) => {
      this.obraSocial = obraSocial;
    });
  }

  previousState() {
    window.history.back();
  }
}
