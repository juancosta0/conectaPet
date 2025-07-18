import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FavoritesService {
  private favoritesSubject = new BehaviorSubject<number[]>([]);
  public favorites$ = this.favoritesSubject.asObservable();

  constructor() {
    this.loadFavorites();
  }

  private loadFavorites(): void {
    const stored = localStorage.getItem('favorites');
    if (stored) {
      this.favoritesSubject.next(JSON.parse(stored));
    }
  }

  private saveFavorites(favorites: number[]): void {
    localStorage.setItem('favorites', JSON.stringify(favorites));
    this.favoritesSubject.next(favorites);
  }

  addToFavorites(petId: number): void {
    const current = this.favoritesSubject.value;
    if (!current.includes(petId)) {
      this.saveFavorites([...current, petId]);
    }
  }

  removeFromFavorites(petId: number): void {
    const current = this.favoritesSubject.value;
    this.saveFavorites(current.filter(id => id !== petId));
  }

  isFavorite(petId: number): boolean {
    return this.favoritesSubject.value.includes(petId);
  }

  getFavorites(): number[] {
    return this.favoritesSubject.value;
  }
}