import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Cliente } from '../shared/model/cliente';
import { Endereco } from '../shared/model/endereco';
import { ClientesService } from '../shared/services/clientes.service';
import { EnderecoService } from '../shared/services/endereco.service';
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
  mensagemErroCep = '';

  //atributo para armazenar os dados de apenas 1 Cliente
  cliente: Cliente = new Cliente;

  //atributo para armazenar os dados do endereco
  endereco: Endereco = new Endereco;

  //objeto para armazenar todos os Estados(array)
  estados = [];

  //inicialização por meio de injeção de dependencia
  constructor(private formBuilder: FormBuilder, private clientesService: ClientesService, 
    private estadosService: EstadosService, private enderecoService: EnderecoService) { }

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
      Validators.pattern('^[0-9]{3}\.?[0-9]{3}\.?[0-9]{3}\-?[0-9]{2}$') 
      ]
    ],

    //declarando o campo 'telefone' do formulário
    telefone: ['',
      [Validators.required, //torna o campo obrigatório 
      Validators.pattern (/^[0-9]{8,12}$/)
     //Validators.pattern (/^\(?\d{2}\)?[\s-]?\d{5}-?\d{4}$/)

      ]
    ],

    //declarando o campo 'email' do formulário
    email: ['',
      Validators.pattern(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{3,3})+$/) 
    ],

    //declarando o campo 'observacao' do formulário
    observacao: [''],

    //declarando o campo 'logradouro' do formulário
    logradouro: ['',
      //torna o campo obrigatório
      [Validators.required,
      Validators.pattern(/^([a-zA-Z]{0,1}[a-zA-Z]{1,}'?-?[a-zA-Z]\s?([a-zA-Z]{1,})?)/) 
      ]
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
      [Validators.required,
      Validators.pattern(/^([a-zA-Z]{1,}[a-zA-Z]{1,}'?-?[a-zA-Z]\s?([a-zA-Z]{1,})?)/)
      ]
    ],

    //declarando o campo 'municipio' do formulário
    municipio: [''],

    //declarando o campo 'estado' do formulário
    estado: [''],

    //declarando o campo 'cep' do formulário
    cep: ['']

  });

  //objeto para capturar o CEP
  formCep = this.formBuilder.group({
    //declarando o campo 'cep' do formulário
    cep: ["" ,
    [Validators.pattern(/^(\d{5}|\d{5}\-?\d{3})$/)] // aceita traço
    ]
    
  });

  //criando um objeto pra utilizar o formulário na página
  get form(): any {
    return this.formCadastro.controls;
  }

   //criando um objeto pra utilizar o formulário na página
   get buscarCep(): any {
    return this.formCep.controls;
  }

  ngOnInit(): void {
    this.buscarEstados();
  }

  // BUSCAR TODOS OS ESTADOS
  buscarEstados(): void {

    this.estadosService
      .buscarEstados()
      .subscribe({
        next: data => this.estados = (data as []),
        error: _e => this.mensagemErro = 'Não foi possível carregar os Estados.'
    })
  }

  // BUSCAR ENDERECO POR CEP
  buscarEnderecoAPI(): void {

    // limpando mensagens
    this.mensagemErroCep ='';
    
    let cep = this.formCep.value.cep;

    if (cep) {
    
      this.enderecoService.buscarEnderecoAPI(cep)
        .subscribe({
          next: endereco => this.endereco = endereco,    
          error: _e  => this.mensagemErroCep = 'Cep inválido.'
        })
    }
  
  }

  //CADASTRAR 
  cadastrar(): void {

    let cliente = this.formCadastro.value;

    //limpar o conteúdo das mensagens
    this.mensagemSucesso = '';
    this.mensagemErro = '';

    this.clientesService.cadastrar(cliente)
      .subscribe({
        next: cliente => {
          this.cliente = cliente;
          this.mensagemSucesso = 'cadastrado com sucesso.';
          this.formCadastro.reset();
        },
        error: (e) => this.mensagemErro = e.error.message
      })
  }

}
