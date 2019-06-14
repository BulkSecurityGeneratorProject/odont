import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITratamiento } from 'app/shared/model/tratamiento.model';
import { TratamientoService } from './tratamiento.service';

@Component({
  selector: 'jhi-tratamiento-delete-dialog',
  templateUrl: './tratamiento-delete-dialog.component.html'
})
export class TratamientoDeleteDialogComponent {
  tratamiento: ITratamiento;

  constructor(
    protected tratamientoService: TratamientoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tratamientoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tratamientoListModification',
        content: 'Deleted an tratamiento'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tratamiento-delete-popup',
  template: ''
})
export class TratamientoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tratamiento }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TratamientoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tratamiento = tratamiento;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tratamiento', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tratamiento', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
