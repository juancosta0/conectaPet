// src/app/services/login.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { LoginRequest } from '../types/login-request.type';
import { LoginResponse } from '../types/login-response.type';
import { AuthService } from './auth.service';
import { environment } from '../../environments/environment';
import { User } from '../types/user.type';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private apiUrl = `${environment.apiUrl}/auth`;

  constructor(
    private httpClient: HttpClient,
    private authService: AuthService
  ) { }

  login(loginRequest: LoginRequest): Observable<LoginResponse> {
    return this.httpClient.post<LoginResponse>(`${this.apiUrl}/login`, loginRequest).pipe(
      tap(response => {
        if (response && response.token) {
          sessionStorage.setItem('auth-token', response.token);
          this.authService.login();
        }
      })
    );
  }

  // CORREÇÃO AQUI
  register(registerRequest: Omit<User, 'id' | 'createdAt'>): Observable<User> {
    return this.httpClient.post<User>(`${this.apiUrl}/register`, registerRequest);
  }
}
