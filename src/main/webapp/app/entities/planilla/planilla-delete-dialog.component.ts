import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanilla } from 'app/shared/model/planilla.model';
import { PlanillaService } from './planilla.service';

@Component({
  selector: 'jhi-planilla-delete-dialog',
  templateUrl: './planilla-delete-dialog.component.html'
})
export class PlanillaDeleteDialogComponent {
  planilla: IPlanilla;

  constructor(protected planillaService: PlanillaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.planillaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'planillaListModification',
        content: 'Deleted an planilla'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-planilla-delete-popup',
  template: ''
})
export class PlanillaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ planilla }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PlanillaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.planilla = planilla;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/planilla', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/planilla', { outlets: { popup: null } }]);
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
