import { IFichaDetalle } from 'app/shared/model/ficha-detalle.model';

export interface IFicha {
  id?: number;
  month?: string;
  urgency?: boolean;
  pacienteId?: number;
  detalles?: IFichaDetalle[];
  planillaId?: number;
}

export class Ficha implements IFicha {
  constructor(
    public id?: number,
    public month?: string,
    public urgency?: boolean,
    public pacienteId?: number,
    public detalles?: IFichaDetalle[],
    public planillaId?: number
  ) {
    this.urgency = this.urgency || false;
  }
}
