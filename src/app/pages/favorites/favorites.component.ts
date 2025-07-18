import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HeaderComponent } from '../../components/header/header.component';
import { PetCardComponent } from '../../components/pet-card/pet-card.component';
import { Pet } from '../../types/pet.type';
import { FavoritesService } from '../../services/favorites.service';
import { PetService } from '../../services/pet.service';

@Component({
  selector: 'app-favorites',
  standalone: true,
  imports: [CommonModule, HeaderComponent, PetCardComponent],
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.scss']
})
export class FavoritesComponent implements OnInit {
  favoritePets: Pet[] = [];

  constructor(
    private favoritesService: FavoritesService,
    private petService: PetService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadFavorites();
  }

  private loadFavorites(): void {
    const favoriteIds = this.favoritesService.getFavorites();
    if (favoriteIds.length > 0) {
      this.petService.getFavoritesPets(favoriteIds).subscribe({
        next: (pets) => {
          this.favoritePets = pets;
        },
        error: (error) => {
          console.error('Erro ao carregar pets favoritos:', error);
        }
      });
    }
  }

  onFavoriteToggled(event: { petId: number, isFavorite: boolean }): void {
    // Remove o pet da lista se foi desfavoritado
    if (!event.isFavorite) {
      this.favoritePets = this.favoritePets.filter(pet => pet.id !== event.petId);
    }
  }

  goToFeed(): void {
    this.router.navigate(['/feed']);
  }
}