import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Pet } from '../types/pet.type';

@Injectable({
  providedIn: 'root'
})
export class PetService {
  private apiUrl = 'http://localhost:8080/api/pets';

  constructor(private http: HttpClient) {}

  private getAuthHeaders() {
    const token = sessionStorage.getItem('auth-token');
    return {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    };
  }

  getAllPets(): Observable<Pet[]> {
    return this.http.get<Pet[]>(this.apiUrl, { headers: this.getAuthHeaders() });
  }

  getPetById(id: number): Observable<Pet | undefined> {
    return this.http.get<Pet>(`${this.apiUrl}/${id}`, { headers: this.getAuthHeaders() });
  }

  getFavoritesPets(favoriteIds: number[]): Observable<Pet[]> {
    const params = favoriteIds.join(',');
    return this.http.get<Pet[]>(`${this.apiUrl}/favorites?ids=${params}`, { headers: this.getAuthHeaders() });
  }

  createPet(petData: any): Observable<Pet> {
    return this.http.post<Pet>(this.apiUrl, petData, { headers: this.getAuthHeaders() });
  }
}