import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Pet } from '../../types/pet.type';
import { CommonModule } from '@angular/common';
import { FavoritesService } from '../../services/favorites.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pet-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './pet-card.component.html',
  styleUrls: ['./pet-card.component.scss']
})
export class PetCardComponent {
  @Input({ required: true }) pet!: Pet;
  @Output() favoriteToggled = new EventEmitter<{ petId: number, isFavorite: boolean }>();

  constructor(
    private favoritesService: FavoritesService,
    private router: Router
  ) {}

  get isFavorite(): boolean {
    return this.favoritesService.isFavorite(this.pet.id);
  }

  toggleFavorite(event: Event): void {
    event.stopPropagation();

    const currentState = this.isFavorite;

    if (currentState) {
      this.favoritesService.removeFromFavorites(this.pet.id);
    } else {
      this.favoritesService.addToFavorites(this.pet.id);
    }

    // Emite o novo estado do "favorito"
    this.favoriteToggled.emit({
      petId: this.pet.id,
      isFavorite: !currentState
    });
  }

  onCardClick(): void {
    this.router.navigate(['/pet', this.pet.id]);
  }
}
