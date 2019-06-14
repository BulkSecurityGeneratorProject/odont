import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDentista } from 'app/shared/model/dentista.model';
import { DentistaService } from './dentista.service';

@Component({
  selector: 'jhi-dentista-delete-dialog',
  templateUrl: './dentista-delete-dialog.component.html'
})
export class DentistaDeleteDialogComponent {
  dentista: IDentista;

  constructor(protected dentistaService: DentistaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.dentistaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'dentistaListModification',
        content: 'Deleted an dentista'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-dentista-delete-popup',
  template: ''
})
export class DentistaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dentista }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DentistaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.dentista = dentista;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/dentista', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/dentista', { outlets: { popup: null } }]);
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
