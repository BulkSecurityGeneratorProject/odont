import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IObraSocial, ObraSocial } from 'app/shared/model/obra-social.model';
import { ObraSocialService } from './obra-social.service';
import { IDentista } from 'app/shared/model/dentista.model';
import { DentistaService } from 'app/entities/dentista';

@Component({
  selector: 'jhi-obra-social-update',
  templateUrl: './obra-social-update.component.html'
})
export class ObraSocialUpdateComponent implements OnInit {
  obraSocial: IObraSocial;
  isSaving: boolean;

  dentistas: IDentista[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    dentistaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected obraSocialService: ObraSocialService,
    protected dentistaService: DentistaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ obraSocial }) => {
      this.updateForm(obraSocial);
      this.obraSocial = obraSocial;
    });
    this.dentistaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDentista[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDentista[]>) => response.body)
      )
      .subscribe((res: IDentista[]) => (this.dentistas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(obraSocial: IObraSocial) {
    this.editForm.patchValue({
      id: obraSocial.id,
      name: obraSocial.name,
      description: obraSocial.description,
      dentistaId: obraSocial.dentistaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const obraSocial = this.createFromForm();
    if (obraSocial.id !== undefined) {
      this.subscribeToSaveResponse(this.obraSocialService.update(obraSocial));
    } else {
      this.subscribeToSaveResponse(this.obraSocialService.create(obraSocial));
    }
  }

  private createFromForm(): IObraSocial {
    const entity = {
      ...new ObraSocial(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      dentistaId: this.editForm.get(['dentistaId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObraSocial>>) {
    result.subscribe((res: HttpResponse<IObraSocial>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackDentistaById(index: number, item: IDentista) {
    return item.id;
  }
}
