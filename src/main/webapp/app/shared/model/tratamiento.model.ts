export interface ITratamiento {
  id?: number;
  code?: number;
  description?: string;
  precio?: number;
  obraSocialId?: number;
}

export class Tratamiento implements ITratamiento {
  constructor(
    public id?: number,
    public code?: number,
    public description?: string,
    public precio?: number,
    public obraSocialId?: number
  ) {}
}
