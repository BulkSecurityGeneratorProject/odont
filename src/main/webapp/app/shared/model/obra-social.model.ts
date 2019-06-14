import { IPaciente } from 'app/shared/model/paciente.model';
import { ITratamiento } from 'app/shared/model/tratamiento.model';
import { IPlanilla } from 'app/shared/model/planilla.model';

export interface IObraSocial {
  id?: number;
  name?: string;
  description?: string;
  dentistaId?: number;
  pacientes?: IPaciente[];
  tratamientos?: ITratamiento[];
  planillas?: IPlanilla[];
}

export class ObraSocial implements IObraSocial {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public dentistaId?: number,
    public pacientes?: IPaciente[],
    public tratamientos?: ITratamiento[],
    public planillas?: IPlanilla[]
  ) {}
}
