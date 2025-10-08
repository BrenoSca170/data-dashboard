// Local: src/app/interceptors/auth.interceptor.ts

import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();

  if (token) {
    // Clona a requisição para adicionar o novo cabeçalho de Autorização
    const cloned = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    // Passa a requisição clonada e com o token para o próximo passo
    return next(cloned);
  }

  // Se não houver token, simplesmente passa a requisição original
  return next(req);
};