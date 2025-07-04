import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MovimentoSaveModel } from './movimento-save.model';
import { MovimentoModel } from './movimento.model';
import { Page } from '../../utils/page';

@Injectable({ providedIn: 'root' })
export class MovimentoService  {
  private readonly apiUrl = '/api/movimentoestoque';

  constructor(private http: HttpClient) {
  }

  listarTodos(page: number, size: number, sort: string): Observable<Page<MovimentoModel>> {
      let params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sort', sort)
    return this.http.get<Page<MovimentoModel>>(this.apiUrl, {params});
      }

  salvarMovimento(movimento: MovimentoSaveModel, codigo: string): Observable<MovimentoSaveModel> {
    return this.http.post<MovimentoSaveModel>(`${this.apiUrl}/${codigo} `, movimento);
  }
}