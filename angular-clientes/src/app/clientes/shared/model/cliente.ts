import { Endereco } from "./endereco";

export class Cliente {
  idCliente!: number;
  nome!: string;
  cpf!: string;
  telefone!: string;
  email!: string;
  observacao!: string;
  endereco: Endereco = new Endereco;
}
