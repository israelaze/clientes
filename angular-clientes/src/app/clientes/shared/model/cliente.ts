/*export interface Cliente {
  idCliente?: number;
  nome: string;
  cpf: string;
  telefone: string;
  email: string;
  observacao: string;
  endereco: {
    idEndereco: number,
    logradouro: string,
    numero: string,
    complemento: string,
    bairro: string,
    municipio: string,
    estado: string,
    cep: string
  }
}*/

export class Cliente {
  idCliente?: number = 0;
  nome: string = '';
  cpf: string = '';
  telefone: string = '';
  email: any = null;
  observacao: string = '';
  endereco!: {
    idEndereco: number;
    logradouro: string;
    numero: string;
    complemento: string;
    bairro: string;
    municipio: string;
    estado: string;
    cep: string;
  };
}
