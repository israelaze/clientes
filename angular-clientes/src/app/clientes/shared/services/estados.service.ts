import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EstadosService {

  // URL API WEB
  endpoint = environment.baseUrl + 'estados';

  // INJEÇÃO DE DEPENDÊNCIA
  constructor(private httpClient: HttpClient) { }

  // BUSCAR TODOS
  buscarTodos(){
    return this.httpClient.get(this.endpoint);
  }
}
