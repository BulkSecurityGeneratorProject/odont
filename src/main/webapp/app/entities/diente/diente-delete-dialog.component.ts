import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDiente } from 'app/shared/model/diente.model';
import { DienteService } from './diente.service';

@Component({
  selector: 'jhi-diente-delete-dialog',
  templateUrl: './diente-delete-dialog.component.html'
})
export class DienteDeleteDialogComponent {
  diente: IDiente;

  constructor(protected dienteService: DienteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.dienteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'dienteListModification',
        content: 'Deleted an diente'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-diente-delete-popup',
  template: ''
})
export class DienteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ diente }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DienteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.diente = diente;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/diente', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/diente', { outlets: { popup: null } }]);
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
