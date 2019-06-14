import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IPrecio, Precio } from 'app/shared/model/precio.model';
import { PrecioService } from './precio.service';

@Component({
  selector: 'jhi-precio-update',
  templateUrl: './precio-update.component.html'
})
export class PrecioUpdateComponent implements OnInit {
  precio: IPrecio;
  isSaving: boolean;
  fechaDesdeDp: any;
  fechaHastaDp: any;

  editForm = this.fb.group({
    id: [],
    idTratamiento: [],
    precio: [],
    fechaDesde: [],
    fechaHasta: []
  });

  constructor(protected precioService: PrecioService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ precio }) => {
      this.updateForm(precio);
      this.precio = precio;
    });
  }

  updateForm(precio: IPrecio) {
    this.editForm.patchValue({
      id: precio.id,
      idTratamiento: precio.idTratamiento,
      precio: precio.precio,
      fechaDesde: precio.fechaDesde,
      fechaHasta: precio.fechaHasta
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const precio = this.createFromForm();
    if (precio.id !== undefined) {
      this.subscribeToSaveResponse(this.precioService.update(precio));
    } else {
      this.subscribeToSaveResponse(this.precioService.create(precio));
    }
  }

  private createFromForm(): IPrecio {
    const entity = {
      ...new Precio(),
      id: this.editForm.get(['id']).value,
      idTratamiento: this.editForm.get(['idTratamiento']).value,
      precio: this.editForm.get(['precio']).value,
      fechaDesde: this.editForm.get(['fechaDesde']).value,
      fechaHasta: this.editForm.get(['fechaHasta']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrecio>>) {
    result.subscribe((res: HttpResponse<IPrecio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
