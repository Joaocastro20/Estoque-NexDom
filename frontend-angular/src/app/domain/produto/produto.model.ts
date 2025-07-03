import { TipoMovimento } from '../movimento/tipo-movimento.enum';
import { TipoProduto } from './tipo-produto.enum';

export interface ProdutoModel {
  id: number;
  codigo: string;
  descricao: string;
  tipoProduto: TipoProduto;
  valorFornecedor: number;
  quantidadeEstoque: number;
  movimentos: MovimentoEstoque[];
}

export interface MovimentoEstoque {
  id: number;
  tipoMovimentacao: TipoMovimento;
  valorVenda: number;
  dataVenda: Date;
  quantidadeMovimentada: number;
}