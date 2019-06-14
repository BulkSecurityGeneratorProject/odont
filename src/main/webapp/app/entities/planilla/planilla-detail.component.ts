import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlanilla } from 'app/shared/model/planilla.model';

@Component({
  selector: 'jhi-planilla-detail',
  templateUrl: './planilla-detail.component.html'
})
export class PlanillaDetailComponent implements OnInit {
  planilla: IPlanilla;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ planilla }) => {
      this.planilla = planilla;
    });
  }

  previousState() {
    window.history.back();
  }
}
