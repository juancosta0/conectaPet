import { Component, Input } from '@angular/core';
import { Pet } from '../../types/pet.type';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-pet-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './pet-card.component.html',
  styleUrls: ['./pet-card.component.scss']
})
export class PetCardComponent {
  @Input({ required: true }) pet!: Pet;
}
