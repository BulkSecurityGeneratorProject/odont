import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFichaDetalle, FichaDetalle } from 'app/shared/model/ficha-detalle.model';
import { FichaDetalleService } from './ficha-detalle.service';
import { IFicha } from 'app/shared/model/ficha.model';
import { FichaService } from 'app/entities/ficha';
import { ITratamiento } from 'app/shared/model/tratamiento.model';
import { TratamientoService } from 'app/entities/tratamiento';
import { IDiente } from 'app/shared/model/diente.model';
import { DienteService } from 'app/entities/diente';

@Component({
  selector: 'jhi-ficha-detalle-update',
  templateUrl: './ficha-detalle-update.component.html'
})
export class FichaDetalleUpdateComponent implements OnInit {
  fichaDetalle: IFichaDetalle;
  isSaving: boolean;

  fichas: IFicha[];

  tratamientos: ITratamiento[];

  dientes: IDiente[];

  editForm = this.fb.group({
    id: [],
    fichaId: [],
    tratamientoId: [],
    dienteId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected fichaDetalleService: FichaDetalleService,
    protected fichaService: FichaService,
    protected tratamientoService: TratamientoService,
    protected dienteService: DienteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fichaDetalle }) => {
      this.updateForm(fichaDetalle);
      this.fichaDetalle = fichaDetalle;
    });
    this.fichaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFicha[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFicha[]>) => response.body)
      )
      .subscribe((res: IFicha[]) => (this.fichas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tratamientoService
      .query({ filter: 'fichadetalle-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ITratamiento[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITratamiento[]>) => response.body)
      )
      .subscribe(
        (res: ITratamiento[]) => {
          if (!this.fichaDetalle.tratamientoId) {
            this.tratamientos = res;
          } else {
            this.tratamientoService
              .find(this.fichaDetalle.tratamientoId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ITratamiento>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ITratamiento>) => subResponse.body)
              )
              .subscribe(
                (subRes: ITratamiento) => (this.tratamientos = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.dienteService
      .query({ filter: 'fichadetalle-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IDiente[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDiente[]>) => response.body)
      )
      .subscribe(
        (res: IDiente[]) => {
          if (!this.fichaDetalle.dienteId) {
            this.dientes = res;
          } else {
            this.dienteService
              .find(this.fichaDetalle.dienteId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IDiente>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IDiente>) => subResponse.body)
              )
              .subscribe(
                (subRes: IDiente) => (this.dientes = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(fichaDetalle: IFichaDetalle) {
    this.editForm.patchValue({
      id: fichaDetalle.id,
      fichaId: fichaDetalle.fichaId,
      tratamientoId: fichaDetalle.tratamientoId,
      dienteId: fichaDetalle.dienteId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const fichaDetalle = this.createFromForm();
    if (fichaDetalle.id !== undefined) {
      this.subscribeToSaveResponse(this.fichaDetalleService.update(fichaDetalle));
    } else {
      this.subscribeToSaveResponse(this.fichaDetalleService.create(fichaDetalle));
    }
  }

  private createFromForm(): IFichaDetalle {
    const entity = {
      ...new FichaDetalle(),
      id: this.editForm.get(['id']).value,
      fichaId: this.editForm.get(['fichaId']).value,
      tratamientoId: this.editForm.get(['tratamientoId']).value,
      dienteId: this.editForm.get(['dienteId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFichaDetalle>>) {
    result.subscribe((res: HttpResponse<IFichaDetalle>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackFichaById(index: number, item: IFicha) {
    return item.id;
  }

  trackTratamientoById(index: number, item: ITratamiento) {
    return item.id;
  }

  trackDienteById(index: number, item: IDiente) {
    return item.id;
  }
}
