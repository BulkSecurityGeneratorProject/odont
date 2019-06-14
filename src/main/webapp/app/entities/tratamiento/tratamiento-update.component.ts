import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITratamiento, Tratamiento } from 'app/shared/model/tratamiento.model';
import { TratamientoService } from './tratamiento.service';
import { IObraSocial } from 'app/shared/model/obra-social.model';
import { ObraSocialService } from 'app/entities/obra-social';

@Component({
  selector: 'jhi-tratamiento-update',
  templateUrl: './tratamiento-update.component.html'
})
export class TratamientoUpdateComponent implements OnInit {
  tratamiento: ITratamiento;
  isSaving: boolean;

  obrasocials: IObraSocial[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required]],
    description: [null, [Validators.required]],
    precio: [],
    obraSocialId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tratamientoService: TratamientoService,
    protected obraSocialService: ObraSocialService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tratamiento }) => {
      this.updateForm(tratamiento);
      this.tratamiento = tratamiento;
    });
    this.obraSocialService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IObraSocial[]>) => mayBeOk.ok),
        map((response: HttpResponse<IObraSocial[]>) => response.body)
      )
      .subscribe((res: IObraSocial[]) => (this.obrasocials = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(tratamiento: ITratamiento) {
    this.editForm.patchValue({
      id: tratamiento.id,
      code: tratamiento.code,
      description: tratamiento.description,
      precio: tratamiento.precio,
      obraSocialId: tratamiento.obraSocialId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tratamiento = this.createFromForm();
    if (tratamiento.id !== undefined) {
      this.subscribeToSaveResponse(this.tratamientoService.update(tratamiento));
    } else {
      this.subscribeToSaveResponse(this.tratamientoService.create(tratamiento));
    }
  }

  private createFromForm(): ITratamiento {
    const entity = {
      ...new Tratamiento(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      description: this.editForm.get(['description']).value,
      precio: this.editForm.get(['precio']).value,
      obraSocialId: this.editForm.get(['obraSocialId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITratamiento>>) {
    result.subscribe((res: HttpResponse<ITratamiento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
