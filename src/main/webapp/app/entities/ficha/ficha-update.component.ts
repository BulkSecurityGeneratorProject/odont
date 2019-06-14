import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFicha, Ficha } from 'app/shared/model/ficha.model';
import { FichaService } from './ficha.service';
import { IPaciente } from 'app/shared/model/paciente.model';
import { PacienteService } from 'app/entities/paciente';
import { IPlanilla } from 'app/shared/model/planilla.model';
import { PlanillaService } from 'app/entities/planilla';

@Component({
  selector: 'jhi-ficha-update',
  templateUrl: './ficha-update.component.html'
})
export class FichaUpdateComponent implements OnInit {
  ficha: IFicha;
  isSaving: boolean;

  pacientes: IPaciente[];

  planillas: IPlanilla[];

  editForm = this.fb.group({
    id: [],
    month: [],
    urgency: [],
    pacienteId: [],
    planillaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected fichaService: FichaService,
    protected pacienteService: PacienteService,
    protected planillaService: PlanillaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ficha }) => {
      this.updateForm(ficha);
      this.ficha = ficha;
    });
    this.pacienteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPaciente[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPaciente[]>) => response.body)
      )
      .subscribe((res: IPaciente[]) => (this.pacientes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.planillaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPlanilla[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPlanilla[]>) => response.body)
      )
      .subscribe((res: IPlanilla[]) => (this.planillas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(ficha: IFicha) {
    this.editForm.patchValue({
      id: ficha.id,
      month: ficha.month,
      urgency: ficha.urgency,
      pacienteId: ficha.pacienteId,
      planillaId: ficha.planillaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const ficha = this.createFromForm();
    if (ficha.id !== undefined) {
      this.subscribeToSaveResponse(this.fichaService.update(ficha));
    } else {
      this.subscribeToSaveResponse(this.fichaService.create(ficha));
    }
  }

  private createFromForm(): IFicha {
    const entity = {
      ...new Ficha(),
      id: this.editForm.get(['id']).value,
      month: this.editForm.get(['month']).value,
      urgency: this.editForm.get(['urgency']).value,
      pacienteId: this.editForm.get(['pacienteId']).value,
      planillaId: this.editForm.get(['planillaId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFicha>>) {
    result.subscribe((res: HttpResponse<IFicha>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackPacienteById(index: number, item: IPaciente) {
    return item.id;
  }

  trackPlanillaById(index: number, item: IPlanilla) {
    return item.id;
  }
}
