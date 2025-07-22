// src/app/services/user.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { UserProfile } from '../types/user.type';
import { AuthService } from './auth.service';
import { environment } from '../../environments/environment';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = `${environment.apiUrl}/api/users`;
  private currentUserSubject = new BehaviorSubject<UserProfile | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(
    private http: HttpClient,
    private authService: AuthService
    ) {
    this.loadCurrentUserFromToken();
  }

  private getAuthHeaders(): HttpHeaders {
    // Pega o token diretamente do sessionStorage
    const token = sessionStorage.getItem('auth-token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  private loadCurrentUserFromToken() {
    const token = sessionStorage.getItem('auth-token');
    if (token) {
      try {
        const decodedToken: any = jwtDecode(token);
        const userId = decodedToken.userId;
        if (userId) {
          this.getUserProfile(userId).subscribe(user => {
            this.currentUserSubject.next(user);
          });
        }
      } catch (error) {
        console.error("Failed to decode token, logging out.", error);
        // Chama o método logout do AuthService para limpar tudo
        this.authService.logout();
      }
    }
  }

  getUserProfile(userId: string): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${this.apiUrl}/${userId}`, { headers: this.getAuthHeaders() }).pipe(
      tap(user => {
        this.currentUserSubject.next(user);
      })
    );
  }

  updateUserProfile(userId: string, profile: Partial<UserProfile>): Observable<UserProfile> {
    return this.http.put<UserProfile>(`${this.apiUrl}/${userId}`, profile, { headers: this.getAuthHeaders() }).pipe(
      tap(updatedUser => {
        this.currentUserSubject.next(updatedUser);
      })
    );
  }
}
