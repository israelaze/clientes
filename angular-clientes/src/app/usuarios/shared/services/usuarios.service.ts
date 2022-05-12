import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Usuario } from '../model/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class UsuariosService {

  // URL API WEB  
  endpoint = environment.baseUrl + "usuarios";

  // INJEÇÃO DE DEPENDÊNCIA
  constructor(private httpClient: HttpClient) { }

  // CADASTRAR
  cadastrar(usuario: Usuario) {
    return this.httpClient.post(this.endpoint, usuario);
  }

}
