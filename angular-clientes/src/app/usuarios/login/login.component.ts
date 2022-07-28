import { localizedString } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthGet } from '../shared/model/authGet';
import { Login } from '../shared/model/login.model';
import { AuthService } from '../shared/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  
  //mensagens
  mensagemErro = '';
  mensagemSucesso= '';
  
  //objeto para armazenar os dados do usuario autenticado.. 
  authGet = new AuthGet;

  //objeto para armazenar os dados inseridos formulário
  login = new Login;
 
  //injeção de dependencia..
  constructor(private formBuilder: FormBuilder, private router: Router,
    private authService: AuthService 
  ) {}

  // método executado antes do componente ser carregado..
  ngOnInit(): void { }

  //LOGIN

  //objeto para capturar os campos do formulário
  formLogin = this.formBuilder.group({
    email: ['',
      [Validators.required, //campo obrigatório
      Validators.pattern(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{3,3})+$/) //expressão regular (REGEX)
      ]
    ],

    senha: ['',
      [Validators.required,
      Validators.pattern('^[A-Za-zÀ-Üà-ü0-9\\s]{4,6}$') 
      ]
    ]
  });

  //criando um objeto pra validar o formulário na página
  get form(): any {
    return this.formLogin.controls;
  }

  //método para logar
  logar(): void {

    this.login = this.formLogin.value;

    //função recebe um objeto (usuário autenticado)
    this.authService.autenticar(this.login)
      .subscribe({
        next: (data) => {
          //recebendo os dados do usuário autenticado
          this.authGet = (data as any);
          //gravando os dados do usuario em uma localStorage..
          localStorage.setItem("AUTH", JSON.stringify(this.authGet));
          //navegando para rota vazia
          return this.router.navigate(['']);
        },
        error: (e) => {
          this.mensagemErro = e.error.message;
        }, 
        complete: () => console.log('Usuário logado')
      })
  }
  
}
