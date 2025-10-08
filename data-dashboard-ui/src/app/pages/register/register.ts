// Local: src/app/pages/register/register.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrls: ['./register.css']
})
export class RegisterComponent {
  credentials = {
    email: '',
    password: ''
  };
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(private authService: AuthService, private router: Router) { }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;
    this.authService.register(this.credentials).subscribe({
      next: (response) => {
        this.successMessage = `${response.message} A redirecionar para o login...`;
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000); // Espera 2 segundos antes de redirecionar
      },
      error: (err: HttpErrorResponse) => {
        this.errorMessage = err.error?.message || 'Falha no registo.';
        console.error(err);
      }
    });
  }
}