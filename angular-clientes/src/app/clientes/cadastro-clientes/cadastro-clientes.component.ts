import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Cliente } from '../shared/model/cliente';
import { ClientesService } from '../shared/services/clientes.service';
import { EstadosService } from '../shared/services/estados.service';

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
    telefone: '',
    email: '',
    observacao: '',
    endereco : {
      idEndereco: '',
      logradouro: '',
      numero: '',
      complemento: '',
      bairro: '',
      municipio: '',
      estado:'',
      cep: ''
    }
  }

  estados = [];

  //inicialização por meio de injeção de dependencia
  constructor(private formBuilder: FormBuilder, private clientesService: ClientesService, private estadosService: EstadosService) { }

  ngOnInit(): void {
 
  // BUSCAR TODOS OS ESTADOS
    this.estadosService
    .buscarTodos()
    .subscribe(
      (data) => {
        this.estados = (data as []);
      },
      (e) => {
        console.log(e)
      }
    );
  }

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

    //declarando o campo 'telefone' do formulário
    telefone: ['',
      [Validators.required, //torna o campo obrigatório
      Validators.pattern (/^\(?\d{2}\)?[\s-]?\d{5}-?\d{4}$/) // expressão regular (REGEX)
     //  Validators.pattern (/^[0-9]{12}$/) // expressão regular (REGEX)

      ]
    ],

    //declarando o campo 'email' do formulário
    email: ['',
      Validators.pattern(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{3,3})+$/) // expressão regular (REGEX) 
    ],

    //declarando o campo 'logradouro' do formulário
    logradouro: ['',
      //torna o campo obrigatório
      [Validators.required]
    ],

    //declarando o campo 'numero' do formulário
    numero: ['',
      //torna o campo obrigatório
      [Validators.required,
      Validators.pattern('^[0-9]{1,6}')
      ]
    ],

    //declarando o campo 'complemento' do formulário
    complemento: [''],

    
    //declarando o campo 'bairro' do formulário
    bairro: ['',
      [Validators.required]
    ],

    //declarando o campo 'municipio' do formulário
    municipio: [''],

    //declarando o campo 'estado' do formulário
    estado: [''],

    //declarando o campo 'cep' do formulário
    cep: ['',
      [Validators.pattern("^(\\d{5}(\\-\\d{3})?)?$")] 
    ],

    //declarando o campo 'observacao' do formulário
    observacao: ['']

  });

  //criando um objeto pra utilizar o formulário na página
  get form(): any {
    return this.formCadastro.controls;
  }

  //CADASTRAR
  cadastrar(): void {

    console.log(this.formCadastro.value.email)
    console.log(this.cliente.endereco)


    //limpar o conteúdo ds mensagens (sucesso ou erro)
    this.mensagemSucesso = '';
    this.mensagemErro = '';


  

    this.clientesService.cadastrar(this.formCadastro.value)
      .subscribe(
        (data) => {
          this.cliente = data as any;
          this.mensagemSucesso = 'Ok'; //incializando a variável
          console.log(this.formCadastro.value.email)
          this.formCadastro.reset();
        },
        (e) => {
          if (e.status == 400) {
            this.mensagemErro = "O CPF informado já encontra-se cadastrado. Tente outro.";
          } else {
            console.log(e.error);
          }
        }
      )
  }

}
