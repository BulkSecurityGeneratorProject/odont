import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDentista, Dentista } from 'app/shared/model/dentista.model';
import { DentistaService } from './dentista.service';

@Component({
  selector: 'jhi-dentista-update',
  templateUrl: './dentista-update.component.html'
})
export class DentistaUpdateComponent implements OnInit {
  dentista: IDentista;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    matricula: []
  });

  constructor(protected dentistaService: DentistaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dentista }) => {
      this.updateForm(dentista);
      this.dentista = dentista;
    });
  }

  updateForm(dentista: IDentista) {
    this.editForm.patchValue({
      id: dentista.id,
      firstName: dentista.firstName,
      lastName: dentista.lastName,
      matricula: dentista.matricula
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const dentista = this.createFromForm();
    if (dentista.id !== undefined) {
      this.subscribeToSaveResponse(this.dentistaService.update(dentista));
    } else {
      this.subscribeToSaveResponse(this.dentistaService.create(dentista));
    }
  }

  private createFromForm(): IDentista {
    const entity = {
      ...new Dentista(),
      id: this.editForm.get(['id']).value,
      firstName: this.editForm.get(['firstName']).value,
      lastName: this.editForm.get(['lastName']).value,
      matricula: this.editForm.get(['matricula']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDentista>>) {
    result.subscribe((res: HttpResponse<IDentista>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
