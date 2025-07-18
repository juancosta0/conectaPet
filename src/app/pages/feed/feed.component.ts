import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../../components/header/header.component';
import { PetCardComponent } from '../../components/pet-card/pet-card.component';
import { Pet } from '../../types/pet.type';
import { PetService } from '../../services/pet.service';

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [CommonModule, HeaderComponent, PetCardComponent],
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss']
})
export class FeedComponent {
  pets: Pet[] = [];

  constructor(private petService: PetService) {}

  ngOnInit(): void {
    this.loadPets();
  }

  private loadPets(): void {
    this.petService.getAllPets().subscribe({
      next: (pets) => {
        this.pets = pets;
      },
      error: (error) => {
        console.error('Erro ao carregar pets:', error);
      }
    });
  }

  onFavoriteToggled(event: { petId: number, isFavorite: boolean }): void {
    // Lógica adicional se necessário
  }
}
