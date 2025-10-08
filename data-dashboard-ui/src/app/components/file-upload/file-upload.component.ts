// Local: src/app/components/file-upload/file-upload.component.ts

import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../services/api.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-file-upload',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.css']
})
export class FileUploadComponent {
  
  statusMessage: string | null = null;
  analysisResult: any = null;

  constructor(private apiService: ApiService) {}

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      this.statusMessage = `Enviando arquivo: ${file.name}...`;
      this.analysisResult = null;

      this.apiService.uploadFile(file).subscribe({
        next: (result) => {
          console.log('Análise recebida com sucesso:', result);
          this.analysisResult = result;
          this.statusMessage = 'Análise concluída!';
        },
        error: (error: HttpErrorResponse) => {
          console.error('Falha no upload:', error);
          this.analysisResult = null;
          this.statusMessage = `Erro: ${error.error.message || error.statusText}`;
        }
      });
    }
  }
}