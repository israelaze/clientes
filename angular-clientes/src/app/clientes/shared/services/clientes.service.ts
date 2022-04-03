import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Cliente } from '../model/cliente';

@Injectable({
  providedIn: 'root'
})
export class ClientesService {

  // URL API WEB
  endpoint = environment.baseUrl + "/clientes";

  // INJEÇÃO DE DEPENDÊNCIA
  constructor(private httpClient: HttpClient) { }

  // CADASTRAR
  cadastrar(cliente: Cliente): Observable<Cliente> {
    return this.httpClient.post<Cliente>(this.endpoint, cliente);
  }

  // BUSCAR TODOS
  buscarTodos(): Observable<Cliente[]> {
    return this.httpClient.get<Cliente[]>(this.endpoint);
  }

  // BUSCAR ID
  buscarId(idCliente: number): Observable<Cliente> {
    return this.httpClient.get<Cliente>(this.endpoint + "/" + idCliente);
  }

  // ATUALIZAR
  atualizar(cliente: Cliente){
    return this.httpClient.put(this.endpoint, cliente, { responseType: 'text' });
  }

  // EXCLUIR
  excluir(idCliente: number) {
    return this.httpClient.delete(this.endpoint + "/" + idCliente, { responseType: 'text' })
  }
  

}