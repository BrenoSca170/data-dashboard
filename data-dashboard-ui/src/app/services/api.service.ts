// Local: src/app/services/api.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  // A URL do nosso API Gateway em Node.js
  private apiUrl = 'http://localhost:3000/api/upload';

  constructor(private http: HttpClient) { }

  /**
   * Envia um arquivo para o backend para análise.
   * @param file O arquivo a ser enviado.
   * @returns Um Observable com os dados da análise.
   */
  uploadFile(file: File): Observable<any> {
    const formData = new FormData();
    // A chave 'file' aqui deve ser a mesma que o gateway Node.js espera
    formData.append('file', file, file.name);

    // Faz a requisição POST para o gateway
    return this.http.post(this.apiUrl, formData);
  }
}