import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Endereco } from '../model/endereco';

@Injectable({
  providedIn: 'root'
})
export class EnderecoService {
  
// URL API WEB
endPoint = environment.baseUrl + 'cep';

  constructor(private httpCliente: HttpClient) { }

  buscarEnderecoAPI(cep: String): Observable<Endereco> {
    return this.httpCliente.get<Endereco>(this.endPoint + '/' + cep);
  }
  
}
