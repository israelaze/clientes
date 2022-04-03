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


@NgModule({
  declarations: [
    AppComponent,
    ConsultaClientesComponent,
    CadastroClientesComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,                         
    ReactiveFormsModule, 
    NgxPaginationModule,                 
    Ng2SearchPipeModule,  
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
