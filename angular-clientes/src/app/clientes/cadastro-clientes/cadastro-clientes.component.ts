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
 /* cliente = {
    idCliente: 0,
    nome: '',
    cpf: '',
    telefone: '',
    email: '',
    observacao: '',
    endereco : {
      idEndereco: 0,
      logradouro: '',
      numero: '',
      complemento: '',
      bairro: '',
      municipio: '',
      estado:'', 
      cep: ''
    }
  }
*/

  cliente:Cliente = new Cliente;
  //objeto para armazenar todos os Estados(array)
  estados = [];

  //inicialização por meio de injeção de dependencia
  constructor(private formBuilder: FormBuilder, private clientesService: ClientesService, private estadosService: EstadosService) { }

  ngOnInit(): void {

    this.buscarEstados();
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
    cep: ['',
     [Validators.pattern('^[0-9]{8}')] 
     //[Validators.pattern("^(\\d{5}(\\-\\d{3})?)?$")] regex com traço -

    ]

  });

  //criando um objeto pra utilizar o formulário na página
  get form(): any {
    return this.formCadastro.controls;
  }

  // BUSCAR TODOS OS ESTADOS
  buscarEstados(): void {

    this.estadosService
      .buscarEstados()
      .subscribe(
        (data) => {
          this.estados = (data as []);
        },
        (e) => {
          console.log(e)
        }
      );
  }

  //CADASTRAR
  cadastrar(): void {

    this.cliente = this.formCadastro.value;

    //limpar o conteúdo ds mensagens (sucesso ou erro)
    this.mensagemSucesso = '';
    this.mensagemErro = '';

    this.clientesService.cadastrar(this.cliente)
      .subscribe(
        (data) => {
          this.cliente = data as any;
          this.mensagemSucesso = 'Ok'; //incializando a variável
          this.formCadastro.reset();
        },
        (e) => {
          if (e.status == 400) {
            console.log(e.error.message);
            this.mensagemErro = e.error.message;
          } else {
            console.log(e.error.message);
          }
        }
      )
  }

}
