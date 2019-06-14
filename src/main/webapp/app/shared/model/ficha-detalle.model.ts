export interface IFichaDetalle {
  id?: number;
  fichaId?: number;
  tratamientoId?: number;
  dienteId?: number;
}

export class FichaDetalle implements IFichaDetalle {
  constructor(public id?: number, public fichaId?: number, public tratamientoId?: number, public dienteId?: number) {}
}
