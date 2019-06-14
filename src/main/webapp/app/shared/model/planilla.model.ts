import { Moment } from 'moment';
import { IFicha } from 'app/shared/model/ficha.model';

export interface IPlanilla {
  id?: number;
  fechaDesde?: Moment;
  fechaHasta?: Moment;
  obraSocialId?: number;
  fichas?: IFicha[];
}

export class Planilla implements IPlanilla {
  constructor(
    public id?: number,
    public fechaDesde?: Moment,
    public fechaHasta?: Moment,
    public obraSocialId?: number,
    public fichas?: IFicha[]
  ) {}
}
