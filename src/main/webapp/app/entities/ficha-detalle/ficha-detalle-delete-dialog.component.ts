import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFichaDetalle } from 'app/shared/model/ficha-detalle.model';
import { FichaDetalleService } from './ficha-detalle.service';

@Component({
  selector: 'jhi-ficha-detalle-delete-dialog',
  templateUrl: './ficha-detalle-delete-dialog.component.html'
})
export class FichaDetalleDeleteDialogComponent {
  fichaDetalle: IFichaDetalle;

  constructor(
    protected fichaDetalleService: FichaDetalleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fichaDetalleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fichaDetalleListModification',
        content: 'Deleted an fichaDetalle'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-ficha-detalle-delete-popup',
  template: ''
})
export class FichaDetalleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fichaDetalle }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FichaDetalleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.fichaDetalle = fichaDetalle;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/ficha-detalle', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/ficha-detalle', { outlets: { popup: null } }]);
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
