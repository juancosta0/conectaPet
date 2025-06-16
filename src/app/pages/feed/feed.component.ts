import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
// Caminhos corrigidos para apontar para a pasta 'components'
import { HeaderComponent } from '../../components/header/header.component';
import { PetCardComponent } from '../../components/pet-card/pet-card.component';
import { Pet } from '../../types/pet.type';

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [CommonModule, HeaderComponent, PetCardComponent],
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss']
})
export class FeedComponent {
  pets: Pet[] = [
    {
      id: 1, name: 'Caramelo', species: 'Cachorro', breed: 'SRD', age: 2,
      size: 'Médio', color: 'Caramelo', description: 'Amigável e brincalhão.',
      imageUrls: ['https://i.imgur.com/kH8yXbT.jpeg']
    },
    {
      id: 2, name: 'Mimi', species: 'Gato', breed: 'Siamês', age: 3, size: 'Pequeno',
      color: 'Branco e Cinza', description: 'Calma e carinhosa.',
      imageUrls: ['https://i.imgur.com/kH8yXbT.jpeg']
    },
    {
      id: 3, name: 'Rex', species: 'Cachorro', breed: 'Pastor Alemão', age: 4,
      size: 'Grande', color: 'Preto e Marrom', description: 'Leal e inteligente.',
      imageUrls: []
    },
    {
      id: 4, name: 'Luna', species: 'Gato', breed: 'SRD', age: 1, size: 'Pequeno',
      color: 'Frajola', description: 'Curiosa e cheia de energia.',
      imageUrls: ['https://i.imgur.com/kH8yXbT.jpeg']
    }
  ];
}
