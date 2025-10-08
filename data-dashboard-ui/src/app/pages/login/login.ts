// Local: src/app/pages/login/login.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // Para usar [(ngModel)]
import { RouterLink } from '@angular/router'; // Para usar routerLink
import { AuthService } from '../../services/auth.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink], // Módulos necessários
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {
  credentials = {
    email: '',
    password: ''
  };
  errorMessage: string | null = null;

  constructor(private authService: AuthService) { }

  onSubmit(): void {
    this.errorMessage = null;
    this.authService.login(this.credentials).subscribe({
      // O sucesso é tratado dentro do serviço, que redireciona para /dashboard
      error: (err: HttpErrorResponse) => {
        this.errorMessage = err.error?.message || 'Falha no login. Verifique as suas credenciais.';
        console.error(err);
      }
    });
  }
}