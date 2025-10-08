// Local: src/app/components/dashboard/dashboard.component.ts

import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../../services/api.service';
import { HttpErrorResponse } from '@angular/common/http';
// ==============================================================================
//  👇 A CORREÇÃO ESTÁ AQUI 👇
// ==============================================================================
import { AuthService } from '../../services/auth.service'; // Caminho corrigido
// ==============================================================================
import { BarChartComponent } from '../charts/bar-chart/bar-chart.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, BarChartComponent],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardComponent {
  
  statusMessage: string | null = null;
  analysisResult: any = null;
  public uniqueValuesData: { label: string, value: number }[] = [];

  constructor(private apiService: ApiService, private authService: AuthService) {}

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      this.statusMessage = `A enviar ficheiro: ${file.name}...`;
      this.analysisResult = null;
      this.uniqueValuesData = [];

      this.apiService.uploadFile(file).subscribe({
        next: (result) => {
          console.log('Análise recebida com sucesso:', result);
          this.analysisResult = result;
          this.statusMessage = 'Análise concluída!';
          this.prepareChartData(result.detailedStatistics);
        },
        error: (error: HttpErrorResponse) => {
          console.error('Falha no upload:', error);
          this.analysisResult = null;
          this.statusMessage = `Erro: ${error.error.message || error.statusText}`;
        }
      });
    }
  }

  prepareChartData(detailedStats: any): void {
    if (!detailedStats) {
      this.uniqueValuesData = [];
      return;
    }

    this.uniqueValuesData = Object.keys(detailedStats).map(key => {
      return {
        label: key,
        value: detailedStats[key].uniqueValues
      };
    });
  }

  logout(): void {
    this.authService.logout();
  }
}