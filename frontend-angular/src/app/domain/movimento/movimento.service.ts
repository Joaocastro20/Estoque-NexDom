import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MovimentoSaveModel } from './movimento-save.model';

@Injectable({ providedIn: 'root' })
export class MovimentoService  {
  private readonly apiUrl = '/api/movimentoestoque';

  constructor(private http: HttpClient) {
  }

  salvarMovimento(movimento: MovimentoSaveModel, codigo: string): Observable<MovimentoSaveModel> {
    return this.http.post<MovimentoSaveModel>(`${this.apiUrl}/${codigo} `, movimento);
  }
}