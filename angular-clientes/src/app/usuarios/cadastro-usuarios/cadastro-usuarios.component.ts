import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { UsuariosService } from '../shared/services/usuarios.service';

@Component({
  selector: 'app-cadastro-usuarios',
  templateUrl: './cadastro-usuarios.component.html',
  styleUrls: ['./cadastro-usuarios.component.css']
})
export class CadastroUsuariosComponent implements OnInit {

  //mensagens
  mensagemErro = '';
  mensagemSucesso = '';
  
  //objeto para armazenar os dados do usuario cadastrado.. 
  usuario = {
    idUsuario: 0,
    nome: '',
    email: ''
  };

  //injeção de dependencia..
  constructor(private formBuilder: FormBuilder, private usuariosService: UsuariosService) { }

  // método executado antes do componente ser carregado..
  ngOnInit(): void { }

  //objeto para capturar os campos do formulário
  formCadastro = this.formBuilder.group({

    //declarando o campo 'nome' do formulário
    nome: ['',
      [Validators.required, //torna o campo obrigatório
      Validators.pattern(/\b[A-Za-zÀ-ú][A-Za-zÀ-ú]+,?\s[A-Za-zÀ-ú][A-Za-zÀ-ú]{2,19}\b/) //Regex para duas strings, separadas com espaço e com no mínimo 3 caracteres cada. Aceita acentuação e rejeita números.
      ]
    ],

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
    return this.formCadastro.controls;
  }

  //CADASTRAR
  cadastrar(): void {

    //limpar o conteúdo ds mensagens (sucesso ou erro)
    this.mensagemSucesso = '';
    this.mensagemErro = '';

    this.usuariosService.cadastrar(this.formCadastro.value)
      .subscribe(
        (data) => {
          this.usuario = data as any;
          this.mensagemSucesso = 'ok';
          this.formCadastro.reset({ nome: '', email: '', senha: '' });
        },
        (e) => {
          if (e.status == 400) {
            this.mensagemErro = 'O email informado já encontra-se cadastrado, por favor tente outro.'
          }else{
            console.log(e.error)
          } 
        }
      )
  }

}
