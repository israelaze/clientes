//Módulos
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { NgxPaginationModule } from 'ngx-pagination';
import { AppRoutingModule } from './app-routing.module';
//Componentes
import { AppComponent } from './app.component';
import { CadastroClientesComponent } from './clientes/cadastro-clientes/cadastro-clientes.component';
import { ConsultaClientesComponent } from './clientes/consulta-clientes/consulta-clientes.component';
import { AutenticacaoComponent } from './layout/autenticacao/autenticacao.component';
import { HomeComponent } from './layout/home/home.component';
import { CadastroUsuariosComponent } from './usuarios/cadastro-usuarios/cadastro-usuarios.component';
import { LoginComponent } from './usuarios/login/login.component';
import { httpInterceptorProviders } from './_interceptors';


@NgModule({
  declarations: [
    AppComponent,
    AutenticacaoComponent,
    HomeComponent,
    LoginComponent,
    CadastroUsuariosComponent,
    CadastroClientesComponent,
    ConsultaClientesComponent
  ],
  imports: [
    BrowserModule,   
    AppRoutingModule,      
    NgxPaginationModule,                 
    Ng2SearchPipeModule,      
    HttpClientModule,
    FormsModule,                         
    ReactiveFormsModule.withConfig({warnOnNgModelWithFormControl: 'always'})
  ],
  //provedores de interceptação
  providers: [
    httpInterceptorProviders
  ],

  bootstrap: [AppComponent]
})
export class AppModule { }
