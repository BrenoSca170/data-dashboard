// Local: src/app/app.component.ts
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FileUploadComponent } from './components/file-upload/file-upload.component'; // 1. Importar

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [RouterOutlet, FileUploadComponent], // 2. Adicionar aqui
    templateUrl: './app.component.html',
    styleUrl: './app.css'
})
export class AppComponent {
  title = 'data-dashboard-ui';
}