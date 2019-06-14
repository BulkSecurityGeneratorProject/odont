import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDiente, Diente } from 'app/shared/model/diente.model';
import { DienteService } from './diente.service';

@Component({
  selector: 'jhi-diente-update',
  templateUrl: './diente-update.component.html'
})
export class DienteUpdateComponent implements OnInit {
  diente: IDiente;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    cara: [],
    numero: []
  });

  constructor(protected dienteService: DienteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ diente }) => {
      this.updateForm(diente);
      this.diente = diente;
    });
  }

  updateForm(diente: IDiente) {
    this.editForm.patchValue({
      id: diente.id,
      cara: diente.cara,
      numero: diente.numero
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const diente = this.createFromForm();
    if (diente.id !== undefined) {
      this.subscribeToSaveResponse(this.dienteService.update(diente));
    } else {
      this.subscribeToSaveResponse(this.dienteService.create(diente));
    }
  }

  private createFromForm(): IDiente {
    const entity = {
      ...new Diente(),
      id: this.editForm.get(['id']).value,
      cara: this.editForm.get(['cara']).value,
      numero: this.editForm.get(['numero']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiente>>) {
    result.subscribe((res: HttpResponse<IDiente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
