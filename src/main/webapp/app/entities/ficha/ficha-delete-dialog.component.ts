import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFicha } from 'app/shared/model/ficha.model';
import { FichaService } from './ficha.service';

@Component({
  selector: 'jhi-ficha-delete-dialog',
  templateUrl: './ficha-delete-dialog.component.html'
})
export class FichaDeleteDialogComponent {
  ficha: IFicha;

  constructor(protected fichaService: FichaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fichaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fichaListModification',
        content: 'Deleted an ficha'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-ficha-delete-popup',
  template: ''
})
export class FichaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ficha }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FichaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.ficha = ficha;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/ficha', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/ficha', { outlets: { popup: null } }]);
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
