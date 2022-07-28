import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Cliente } from '../shared/model/cliente';
import { ClientesService } from '../shared/services/clientes.service';
import { EstadosService } from '../shared/services/estados.service';

@Component({
  selector: 'app-consulta-clientes',
  templateUrl: './consulta-clientes.component.html',
  styleUrls: ['./consulta-clientes.component.css']
})
export class ConsultaClientesComponent implements OnInit {

  //atributos
  mensagemSucesso = '';
  mensagemErro = '';

  mensagemSucessoEdicao = '';
  mensagemErroEdicao = '';

  //defini o campo de pesquisa como uma string vazia
  pesquisa = "";

  //armazena a página atual do componente de paginação
  page = 1;

  //atributo para armazenar os dados de apenas 1 Cliente
  cliente: Cliente = new Cliente;
  clienteGet: Cliente = new Cliente;


  //atributo para armazenar a listagem de clientes(array de Cliente)
  clientes: Cliente[] = [];

  //objeto para armazenar todos os Estados(array)
  estados = [];

  // INJEÇÃO DE DEPENDÊNCIA
  constructor(private clientesService: ClientesService, private formBuilder: FormBuilder, private estadosService: EstadosService) { }

  //objeto para capturar os campos do formulário
  formEdicao = this.formBuilder.group({

    //declarando o campo 'idCliente' do formulário
    idCliente: 0,

    //declarando o campo 'nome' do formulário
    nome: ['',
      //torna o campo obrigatório
      [Validators.required,
      //Regex para duas strings, separadas com espaço e com no mínimo 3 caracteres cada. Aceita acentuação e rejeita números.
      Validators.pattern(/\b[A-Za-zÀ-ú][A-Za-zÀ-ú]+,?\s[A-Za-zÀ-ú][A-Za-zÀ-ú]{2,19}\b/)
      ]
    ],

    //declarando o campo 'telefone' do formulário
    telefone: ['',
      [Validators.required, 
      Validators.pattern (/^[0-9]{8,12}$/) // SOMENTE NÚMEROS
      //Validators.pattern (/^\(?\d{2}\)?[\s-]?\d{5}-?\d{4}$/) 
      ]
    ],

    //declarando o campo 'email' do formulário
    email: ['',
      Validators.pattern(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{3,3})+$/)
    ],

    //declarando o campo 'observacao' do formulário
    observacao: [''],

    //declarando o campo 'idEndereco' do formulário
    idEndereco: 0,

    //declarando o campo 'logradouro' do formulário
    logradouro: ['',
      [Validators.required,
      Validators.pattern(/^([a-zA-Z]{0,1}[a-zA-Z]{1,}'?-?[a-zA-Z]\s?([a-zA-Z]{1,})?)/) ]
    ],

    //declarando o campo 'numero' do formulário
    numero: ['',
      [Validators.required,
      Validators.pattern('^[0-9]{1,6}')
      ]
    ],

    //declarando o campo 'complemento' do formulário
    complemento: [''],

    //declarando o campo 'bairro' do formulário
    bairro: ['',
      [Validators.required,
      Validators.pattern(/^([a-zA-Z]{1,}[a-zA-Z]{1,}'?-?[a-zA-Z]\s?([a-zA-Z]{1,})?)/)]
    ],

    //declarando o campo 'municipio' do formulário
    municipio: [''],

    //declarando o campo 'estado' do formulário
    estado: [''],

    //declarando o campo 'cep' do formulário
    cep: ['',
      [Validators.pattern(/^(\d{5}|\d{5}\-?\d{3})$/)] // aceita traço
    ]

  });

  //criando um objeto pra utilizar o formulário na página
  get form(): any {
    return this.formEdicao.controls;
  }

  //FUNÇÃO EXECUTADA QUANDO O COMPONENTE É CARREGADO
  ngOnInit(): void {
    this.buscarTodos();
    this.buscarEstados();

  }

  // BUSCAR TODOS OS ESTADOS
  buscarEstados(): void {

    this.estadosService.buscarEstados()
      .subscribe({
        next: data => this.estados = (data as []),
        error: e => console.log(e.error)
      })
  }

  // BUSCAR TODOS
  buscarTodos(): void {
    this.clientesService.buscarTodos()
    .subscribe({
      next: clientes => this.clientes = clientes,
      error: e => console.log(e.error)
    })
  }

  // BUSCAR ID
  buscarId(idCliente: number): void {

    // limpando mensagens
    this.mensagemSucesso = '';
    this.mensagemErro = '';

    this.mensagemSucessoEdicao = '';
    this.mensagemErroEdicao = '';

    this.clientesService.buscarId(idCliente)
    .subscribe({
      next: cliente => this.cliente = cliente,
      error: e => console.log(e.error)
    })
  }
  
  // ATUALIZAR
  atualizar(): void {

    // limpando mensagens
    this.mensagemErroEdicao = '';

    this.clientesService.atualizar(this.formEdicao.value)
      .subscribe({
        next: clienteGet => {
          this.clienteGet = clienteGet;
          this.mensagemSucessoEdicao = ' atualizado com sucesso.';
          this.formEdicao.reset();
          this.ngOnInit();
        },
        error: e => {
          this.mensagemErroEdicao = e.error.message,
          this.ngOnInit();
        }
      })

  }

  // EXCLUIR
  excluir(idCliente: number): void {
    this.clientesService.excluir(idCliente)
      .subscribe({
        next: data => (
          this.mensagemSucesso = data, 
          this.ngOnInit()
        ),
        error: e => this.mensagemErro = e.error.message
      })

  }

  // PAGINAÇÃO
  handlePageChange(event: number) {
    this.page = event;
  }

}
