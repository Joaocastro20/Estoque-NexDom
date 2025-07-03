import { Injectable } from '@angular/core';
import { ProdutoModel } from './produto.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from '../../utils/page';
import { ProdutoUpdateModel } from './produto-update.model';
import { ProdutoSaveModel } from './produto-save.model';

@Injectable({ providedIn: 'root' })
export class ProdutoService  {
  private readonly apiUrl = '/api/produtos';

  constructor(private http: HttpClient) {
  }

  listarTodos(): Observable<Page<ProdutoModel>> {
  return this.http.get<Page<ProdutoModel>>(this.apiUrl);
    }

  salvar(produto: ProdutoSaveModel): Observable<ProdutoModel> {
    return this.http.post<ProdutoModel>(this.apiUrl, produto);
  }

  editar(produto: ProdutoUpdateModel, codigo: string): Observable<ProdutoModel> {
    return this.http.put<ProdutoModel>(`${this.apiUrl}/${codigo}`, produto);
  }

  excluir(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${codigo}`);
  }
}