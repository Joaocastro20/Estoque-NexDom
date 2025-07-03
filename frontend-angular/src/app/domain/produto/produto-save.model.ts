export interface ProdutoSaveModel {
  codigo: string;
  descricao: string;
  tipoProduto: string;
  valorFornecedor: number;
  quantidadeEstoque: number;
  movimentos: [];
}