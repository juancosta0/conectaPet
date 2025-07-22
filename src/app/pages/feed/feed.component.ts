// src/app/pages/feed/feed.component.ts
import { Component, OnInit } from '@angular/core';
import { PetService } from '../../services/pet.service';
import { Pet } from '../../types/pet.type';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss']
})
export class FeedComponent implements OnInit {
  pets: Pet[] = [];
  filteredPets: Pet[] = [];

  constructor(private petService: PetService) { }

  ngOnInit(): void {
    // CORREÇÃO AQUI
    this.petService.getPets().subscribe((data: Pet[]) => {
      this.pets = data;
      this.filteredPets = data;
    });
  }

  onSearch(searchTerm: string): void {
    this.filteredPets = this.pets.filter(pet =>
      pet.name.toLowerCase().includes(searchTerm.toLowerCase())
    );
  }
}
