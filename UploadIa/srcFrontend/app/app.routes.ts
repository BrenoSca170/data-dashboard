// Local: src/app/app.routes.ts
import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login';
import { RegisterComponent } from './pages/register/register';
// ==============================================================================
//  ▼▼▼ A CORREÇÃO ESTÁ AQUI ▼▼▼
// ==============================================================================
import { DashboardComponent } from './components/dashboard/dashboard.component'; 
import { authGuard } from './guards/auth-guard';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] },
    // A linha abaixo é crucial: ela redireciona a página inicial para /login
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    // Opcional: uma rota "catch-all" para redirecionar rotas inválidas
    { path: '**', redirectTo: '/login' }
];