 //Módulos
import { NgModule } from '@angular/core';
import { RouterModule, Routes} from '@angular/router';

import { CadastroUsuariosComponent } from './usuarios/cadastro-usuarios/cadastro-usuarios.component';
import { LoginComponent } from './usuarios/login/login.component';

import { CadastroClientesComponent } from './clientes/cadastro-clientes/cadastro-clientes.component';
import { ConsultaClientesComponent } from './clientes/consulta-clientes/consulta-clientes.component';

import { AutenticacaoComponent } from './layout/autenticacao/autenticacao.component';
import { HomeComponent } from './layout/home/home.component';

import { AuthGuard } from './usuarios/shared/auth.guard';

//Rotas
const routes: Routes = [ 
  //rota pai (carrega o componente principal)
  { path: '', redirectTo: 'home', pathMatch: "full"},
  { path: 'home', component: HomeComponent,
    //rotas filhas (carregam dentro do componente principal)
    children: [
      { path: 'cadastro-clientes', component: CadastroClientesComponent },
      { path: 'consulta-clientes', component: ConsultaClientesComponent},
    ],
    //guarda (decide se uma rota pode ser ativada)
    canActivate: [AuthGuard]
  },

  { path: '', 
    component: AutenticacaoComponent,
    children: [
      { path: '', redirectTo: 'login', pathMatch: 'full' },
      { path: 'login', component: LoginComponent },
      { path: 'cadastro-usuarios', component: CadastroUsuariosComponent }
    ]
  },
  { path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
