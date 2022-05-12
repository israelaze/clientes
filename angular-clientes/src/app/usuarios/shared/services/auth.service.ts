import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Login } from '../model/login.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // URL API WEB
  endpoint = environment.baseUrl + "auth";

  // INJEÇÃO DE DEPENDÊNCIA
  constructor(private httpClient: HttpClient) { }

  // AUTENTICAR
  autenticar(login: Login){
    return this.httpClient.post(this.endpoint, login);
  }

  // BUSCAR USUÁRIO AUTENTICADO
  usuarioAutenticado() {

    const authUser = JSON.parse(localStorage.getItem('AUTH') as any);
    return authUser;
  }

}