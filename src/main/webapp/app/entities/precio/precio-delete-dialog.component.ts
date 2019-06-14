import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrecio } from 'app/shared/model/precio.model';
import { PrecioService } from './precio.service';

@Component({
  selector: 'jhi-precio-delete-dialog',
  templateUrl: './precio-delete-dialog.component.html'
})
export class PrecioDeleteDialogComponent {
  precio: IPrecio;

  constructor(protected precioService: PrecioService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.precioService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'precioListModification',
        content: 'Deleted an precio'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-precio-delete-popup',
  template: ''
})
export class PrecioDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ precio }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PrecioDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.precio = precio;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/precio', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/precio', { outlets: { popup: null } }]);
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
