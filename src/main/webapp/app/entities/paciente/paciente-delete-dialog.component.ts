import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaciente } from 'app/shared/model/paciente.model';
import { PacienteService } from './paciente.service';

@Component({
  selector: 'jhi-paciente-delete-dialog',
  templateUrl: './paciente-delete-dialog.component.html'
})
export class PacienteDeleteDialogComponent {
  paciente: IPaciente;

  constructor(protected pacienteService: PacienteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pacienteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pacienteListModification',
        content: 'Deleted an paciente'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-paciente-delete-popup',
  template: ''
})
export class PacienteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paciente }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PacienteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.paciente = paciente;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/paciente', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/paciente', { outlets: { popup: null } }]);
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
