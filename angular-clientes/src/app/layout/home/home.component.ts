import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/usuarios/shared/services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  
  // objeto para armazenar os dados do usuario autenticado.. 
  authGet = {
    idUsuario: 0,
    nome: '',
    email: '',
    accessToken: ''
  };

  //injeção de depend~encia
  constructor(private router: Router, private authService: AuthService) { }

  //método executado antes do componente ser carregado..
  ngOnInit(): void {
    
    //capturando os dados do usuário autenticado para exibir no componente
    this.authGet = this.authService.usuarioAutenticado();
  }

  //LOGOUT
  logout(): void {

    //apagar os dados em sessão
    localStorage.removeItem('AUTH');

    //navegando para a página de login
    this.router.navigate(['login']);
  }

}
