import { IObraSocial } from 'app/shared/model/obra-social.model';

export interface IDentista {
  id?: number;
  firstName?: string;
  lastName?: string;
  matricula?: number;
  obrasSociales?: IObraSocial[];
}

export class Dentista implements IDentista {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public matricula?: number,
    public obrasSociales?: IObraSocial[]
  ) {}
}
