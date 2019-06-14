import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPaciente, Paciente } from 'app/shared/model/paciente.model';
import { PacienteService } from './paciente.service';
import { IObraSocial } from 'app/shared/model/obra-social.model';
import { ObraSocialService } from 'app/entities/obra-social';

@Component({
  selector: 'jhi-paciente-update',
  templateUrl: './paciente-update.component.html'
})
export class PacienteUpdateComponent implements OnInit {
  paciente: IPaciente;
  isSaving: boolean;

  obrasocials: IObraSocial[];
  birthDateDp: any;

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]],
    firtsName: [],
    lastName: [],
    email: [],
    address: [],
    phone: [],
    birthDate: [],
    obraSocialId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pacienteService: PacienteService,
    protected obraSocialService: ObraSocialService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ paciente }) => {
      this.updateForm(paciente);
      this.paciente = paciente;
    });
    this.obraSocialService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IObraSocial[]>) => mayBeOk.ok),
        map((response: HttpResponse<IObraSocial[]>) => response.body)
      )
      .subscribe((res: IObraSocial[]) => (this.obrasocials = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(paciente: IPaciente) {
    this.editForm.patchValue({
      id: paciente.id,
      identifier: paciente.identifier,
      firtsName: paciente.firtsName,
      lastName: paciente.lastName,
      email: paciente.email,
      address: paciente.address,
      phone: paciente.phone,
      birthDate: paciente.birthDate,
      obraSocialId: paciente.obraSocialId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const paciente = this.createFromForm();
    if (paciente.id !== undefined) {
      this.subscribeToSaveResponse(this.pacienteService.update(paciente));
    } else {
      this.subscribeToSaveResponse(this.pacienteService.create(paciente));
    }
  }

  private createFromForm(): IPaciente {
    const entity = {
      ...new Paciente(),
      id: this.editForm.get(['id']).value,
      identifier: this.editForm.get(['identifier']).value,
      firtsName: this.editForm.get(['firtsName']).value,
      lastName: this.editForm.get(['lastName']).value,
      email: this.editForm.get(['email']).value,
      address: this.editForm.get(['address']).value,
      phone: this.editForm.get(['phone']).value,
      birthDate: this.editForm.get(['birthDate']).value,
      obraSocialId: this.editForm.get(['obraSocialId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaciente>>) {
    result.subscribe((res: HttpResponse<IPaciente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
