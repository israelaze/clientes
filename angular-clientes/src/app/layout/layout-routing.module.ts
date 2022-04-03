 //Módulos
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadastroClientesComponent } from '../clientes/cadastro-clientes/cadastro-clientes.component';
import { ConsultaClientesComponent } from '../clientes/consulta-clientes/consulta-clientes.component';
import { HomeComponent } from './home/home.component';

//Rotas
const routes: Routes = [
    //rota pai (carrega o componente principal)
  { path: '', component: HomeComponent, 
    //rotas filhas (carregam dentro do componente principal)
    children: [
      { path: 'consulta-clientes', component: ConsultaClientesComponent},
      { path: 'cadastro-clientes', component: CadastroClientesComponent}
    ] 
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LayoutRoutingModule { }
