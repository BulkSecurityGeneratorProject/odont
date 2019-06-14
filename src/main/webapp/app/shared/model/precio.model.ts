import { Moment } from 'moment';

export interface IPrecio {
  id?: number;
  idTratamiento?: number;
  precio?: number;
  fechaDesde?: Moment;
  fechaHasta?: Moment;
}

export class Precio implements IPrecio {
  constructor(
    public id?: number,
    public idTratamiento?: number,
    public precio?: number,
    public fechaDesde?: Moment,
    public fechaHasta?: Moment
  ) {}
}
