// src/app/services/pet.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pet } from '../types/pet.type';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PetService {
  private apiUrl = `${environment.apiUrl}/api/pets`;

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = sessionStorage.getItem('auth-token');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  getPets(): Observable<Pet[]> {
    return this.http.get<Pet[]>(this.apiUrl);
  }

  // NOVO MÉTODO ADICIONADO
  getPetsByIds(ids: string[]): Observable<Pet[]> {
    const params = new HttpParams().set('ids', ids.join(','));
    return this.http.get<Pet[]>(`${this.apiUrl}/by-ids`, { params });
  }


  getPetById(id: string): Observable<Pet> {
    return this.http.get<Pet>(`${this.apiUrl}/${id}`);
  }

  createPet(pet: Omit<Pet, 'id' | 'user' | 'adoptionRequests'>): Observable<Pet> {
    return this.http.post<Pet>(this.apiUrl, pet, { headers: this.getAuthHeaders() });
  }

  updatePet(id: string, pet: Partial<Pet>): Observable<Pet> {
    return this.http.put<Pet>(`${this.apiUrl}/${id}`, pet, { headers: this.getAuthHeaders() });
  }

  deletePet(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getAuthHeaders() });
  }
}
