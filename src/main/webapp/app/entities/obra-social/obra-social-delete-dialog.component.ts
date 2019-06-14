import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IObraSocial } from 'app/shared/model/obra-social.model';
import { ObraSocialService } from './obra-social.service';

@Component({
  selector: 'jhi-obra-social-delete-dialog',
  templateUrl: './obra-social-delete-dialog.component.html'
})
export class ObraSocialDeleteDialogComponent {
  obraSocial: IObraSocial;

  constructor(
    protected obraSocialService: ObraSocialService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.obraSocialService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'obraSocialListModification',
        content: 'Deleted an obraSocial'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-obra-social-delete-popup',
  template: ''
})
export class ObraSocialDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ obraSocial }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ObraSocialDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.obraSocial = obraSocial;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/obra-social', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/obra-social', { outlets: { popup: null } }]);
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
