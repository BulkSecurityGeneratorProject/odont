export const enum Cara {
  VESTIBULAR = 'VESTIBULAR',
  DISTAL = 'DISTAL',
  MESIAL = 'MESIAL',
  OCLUSAL = 'OCLUSAL',
  INCISAL = 'INCISAL',
  PALATINO = 'PALATINO',
  LINGUAL = 'LINGUAL'
}

export interface IDiente {
  id?: number;
  cara?: Cara;
  numero?: number;
}

export class Diente implements IDiente {
  constructor(public id?: number, public cara?: Cara, public numero?: number) {}
}
