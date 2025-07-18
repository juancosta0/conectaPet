import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserProfile } from '../types/user.type';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users';
  private currentUserSubject = new BehaviorSubject<UserProfile | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadCurrentUser();
  }

  private loadCurrentUser(): void {
    // Mock user data - em produção viria do backend
    const mockUser: UserProfile = {
      id: 1,
      name: 'João Silva',
      email: 'joao@email.com',
      userType: 'adotante',
      createdAt: new Date(),
      favoritesPets: [],
      adoptedPets: []
    };
    this.currentUserSubject.next(mockUser);
  }

  getCurrentUser(): UserProfile | null {
    return this.currentUserSubject.value;
  }

  updateProfile(userData: Partial<UserProfile>): Observable<UserProfile | null> {
    // Mock implementation - em produção faria requisição HTTP
    const current = this.currentUserSubject.value;
    if (current) {
      const updated = { ...current, ...userData };
      this.currentUserSubject.next(updated);
    }
    return this.currentUser$;
  }

  getUserById(id: number): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${this.apiUrl}/${id}`);
  }
}