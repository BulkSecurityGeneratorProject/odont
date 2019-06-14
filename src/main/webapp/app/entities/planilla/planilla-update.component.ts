import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPlanilla, Planilla } from 'app/shared/model/planilla.model';
import { PlanillaService } from './planilla.service';
import { IObraSocial } from 'app/shared/model/obra-social.model';
import { ObraSocialService } from 'app/entities/obra-social';

@Component({
  selector: 'jhi-planilla-update',
  templateUrl: './planilla-update.component.html'
})
export class PlanillaUpdateComponent implements OnInit {
  planilla: IPlanilla;
  isSaving: boolean;

  obrasocials: IObraSocial[];
  fechaDesdeDp: any;
  fechaHastaDp: any;

  editForm = this.fb.group({
    id: [],
    fechaDesde: [],
    fechaHasta: [],
    obraSocialId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected planillaService: PlanillaService,
    protected obraSocialService: ObraSocialService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ planilla }) => {
      this.updateForm(planilla);
      this.planilla = planilla;
    });
    this.obraSocialService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IObraSocial[]>) => mayBeOk.ok),
        map((response: HttpResponse<IObraSocial[]>) => response.body)
      )
      .subscribe((res: IObraSocial[]) => (this.obrasocials = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(planilla: IPlanilla) {
    this.editForm.patchValue({
      id: planilla.id,
      fechaDesde: planilla.fechaDesde,
      fechaHasta: planilla.fechaHasta,
      obraSocialId: planilla.obraSocialId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const planilla = this.createFromForm();
    if (planilla.id !== undefined) {
      this.subscribeToSaveResponse(this.planillaService.update(planilla));
    } else {
      this.subscribeToSaveResponse(this.planillaService.create(planilla));
    }
  }

  private createFromForm(): IPlanilla {
    const entity = {
      ...new Planilla(),
      id: this.editForm.get(['id']).value,
      fechaDesde: this.editForm.get(['fechaDesde']).value,
      fechaHasta: this.editForm.get(['fechaHasta']).value,
      obraSocialId: this.editForm.get(['obraSocialId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanilla>>) {
    result.subscribe((res: HttpResponse<IPlanilla>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackObraSocialById(index: number, item: IObraSocial) {
    return item.id;
  }
}
