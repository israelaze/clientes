import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ClientesService } from '../shared/services/clientes.service';

@Component({
  selector: 'app-cadastro-clientes',
  templateUrl: './cadastro-clientes.component.html',
  styleUrls: ['./cadastro-clientes.component.css']
})
export class CadastroClientesComponent implements OnInit {

  //atributos (campos)
  mensagemSucesso = '';
  mensagemErro = '';

  //objeto para armazenar os dados do cliente cadastrado
  cliente = {
    idCliente: 0,
    nome: '',
    cpf: '',
    email: ''   
  }
  
  //inicialização por meio de injeção de dependencia
  constructor(private formBuilder: FormBuilder, private clientesService: ClientesService) { }

  ngOnInit(): void { }

  //objeto para capturar os campos do formulário
  formCadastro = this.formBuilder.group({

    //declarando o campo 'nome' do formulário
    nome: ['',
      //torna o campo obrigatório
      [Validators.required, 
      //Regex para duas strings, separadas com espaço e com no mínimo 3 caracteres cada. Aceita acentuação e rejeita números.
      Validators.pattern(/\b[A-Za-zÀ-ú][A-Za-zÀ-ú]+,?\s[A-Za-zÀ-ú][A-Za-zÀ-ú]{2,19}\b/) 
      ]
    ],

    //declarando o campo 'cpf' do formulário
    cpf: ['', 
      [Validators.required,
      Validators.pattern('^[0-9]{3}\.?[0-9]{3}\.?[0-9]{3}\-?[0-9]{2}$') // expressão regular (REGEX)
      ]
    ],

    //declarando o campo 'email' do formulário
    email: ['', 
      [Validators.required, //torna o campo obrigatório
      Validators.pattern(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{3,3})+$/) // expressão regular (REGEX) 
      ]
    ]
  });

  //criando um objeto pra utilizar o formulário na página
  get form(): any {
    return this.formCadastro.controls;
  }

  //CADASTRAR
  cadastrar(): void {

    //limpar o conteúdo ds mensagens (sucesso ou erro)
    this.mensagemSucesso = '';
    this.mensagemErro = '';

    this.clientesService.cadastrar(this.formCadastro.value)
      .subscribe(
        (data) => {
          this.cliente = data as any;
          this.mensagemSucesso = 'Ok'; //incializando a variável
          this.formCadastro.reset();
        },
        (e) => {
          if(e.status == 400){
            this.mensagemErro = "O CPF informado já encontra-se cadastrado. Tente outro.";
          }else{
            console.log(e.error);
          } 
        }
      )
  }

}
