import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from '../../utils/page';
import { MensagemModel } from './mensagem.model';

@Injectable({ providedIn: 'root' })
export class MensagemService {
  private readonly apiUrl = '/api/mensagens';

  constructor(private http: HttpClient) {
  }

  listarTodos(): Observable<MensagemModel[]> {
    return this.http.get<MensagemModel[]>(this.apiUrl);
  }
}