import { Injectable } from '@angular/core';
import { ProdutoModel } from './produto.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from '../../utils/page';

@Injectable({ providedIn: 'root' })
export class ProdutoService  {
  private readonly apiUrl = '/api/produtos';

  constructor(private http: HttpClient) {
  }

  listarTodos(): Observable<Page<ProdutoModel>> {
  return this.http.get<Page<ProdutoModel>>(this.apiUrl);
    }

  salvar(produto: ProdutoModel): Observable<ProdutoModel> {
    return this.http.post<ProdutoModel>(this.apiUrl, produto);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}