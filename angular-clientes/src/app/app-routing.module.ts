 //Módulos
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

//Rotas
const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'home'},
  //rotas filho de carregamento lento.(lazy-load)
  { path: 'home', loadChildren: () => import('./layout/layout.module').then(m => m.LayoutModule) }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
