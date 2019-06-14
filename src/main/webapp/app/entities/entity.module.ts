import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'paciente',
        loadChildren: './paciente/paciente.module#OdontPacienteModule'
      },
      {
        path: 'dentista',
        loadChildren: './dentista/dentista.module#OdontDentistaModule'
      },
      {
        path: 'obra-social',
        loadChildren: './obra-social/obra-social.module#OdontObraSocialModule'
      },
      {
        path: 'tratamiento',
        loadChildren: './tratamiento/tratamiento.module#OdontTratamientoModule'
      },
      {
        path: 'diente',
        loadChildren: './diente/diente.module#OdontDienteModule'
      },
      {
        path: 'precio',
        loadChildren: './precio/precio.module#OdontPrecioModule'
      },
      {
        path: 'ficha',
        loadChildren: './ficha/ficha.module#OdontFichaModule'
      },
      {
        path: 'ficha-detalle',
        loadChildren: './ficha-detalle/ficha-detalle.module#OdontFichaDetalleModule'
      },
      {
        path: 'planilla',
        loadChildren: './planilla/planilla.module#OdontPlanillaModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OdontEntityModule {}
