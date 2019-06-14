import { Moment } from 'moment';
import { IFicha } from 'app/shared/model/ficha.model';

export interface IPaciente {
  id?: number;
  identifier?: string;
  firtsName?: string;
  lastName?: string;
  email?: string;
  address?: string;
  phone?: string;
  birthDate?: Moment;
  fichas?: IFicha[];
  obraSocialId?: number;
}

export class Paciente implements IPaciente {
  constructor(
    public id?: number,
    public identifier?: string,
    public firtsName?: string,
    public lastName?: string,
    public email?: string,
    public address?: string,
    public phone?: string,
    public birthDate?: Moment,
    public fichas?: IFicha[],
    public obraSocialId?: number
  ) {}
}
