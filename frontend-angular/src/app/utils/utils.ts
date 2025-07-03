export function gerarNumerosAleatorios(qtd: number) {
  return Array.from({ length: qtd }, () => Math.floor(Math.random() * 10)).join('');
}