// Local: src/app/components/charts/bar-chart/bar-chart.component.ts
import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
// 1. Importe a BaseChartDirective em vez de NgChartsModule
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration, ChartData, ChartType } from 'chart.js';

@Component({
  selector: 'app-bar-chart',
  standalone: true,
  // 2. Use a BaseChartDirective aqui
  imports: [CommonModule, BaseChartDirective],
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.css']
})
export class BarChartComponent implements OnChanges {
  @Input() chartTitle: string = 'Gráfico';
  @Input() inputData: { label: string, value: number }[] = [];

  public barChartOptions: ChartConfiguration['options'] = {
    responsive: true,
  };
  public barChartType: ChartType = 'bar';
  public barChartData: ChartData<'bar'> = {
    labels: [],
    datasets: [{ data: [], label: 'Valores' }]
  };

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['inputData'] && this.inputData) {
      this.updateChartData();
    }
  }

  private updateChartData(): void {
    const labels = this.inputData.map(item => item.label);
    const data = this.inputData.map(item => item.value);

    this.barChartData = {
      labels: labels,
      datasets: [{ data: data, label: this.chartTitle, backgroundColor: '#3f51b5' }]
    };
  }
}