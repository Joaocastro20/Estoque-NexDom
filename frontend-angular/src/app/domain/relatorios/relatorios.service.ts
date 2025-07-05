import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from '../../utils/page';

@Injectable({ providedIn: 'root' })
export class RelatoriosService  {
  private readonly apiUrl = '/api/relatorio';

  constructor(private http: HttpClient) {
  }

  // listarTodos(page: number, size: number, sort: string): Observable<Page<MovimentoModel>> {
  //     let params = new HttpParams()
  //     .set('page', page)
  //     .set('size', size)
  //     .set('sort', sort)
  //   return this.http.get<Page<MovimentoModel>>(this.apiUrl, {params});
  //     }

  // salvarMovimento(movimento: MovimentoSaveModel, codigo: string): Observable<MovimentoSaveModel> {
  //   return this.http.post<MovimentoSaveModel>(`${this.apiUrl}/${codigo} `, movimento);
  // } 

  gerarRelatorioTotal(){
    return this.http.get(`${this.apiUrl}/total`, {responseType: 'blob'});
  }
}