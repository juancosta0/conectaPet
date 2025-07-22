// src/app/pages/pet-details/pet-details.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PetService } from '../../services/pet.service';
import { Pet } from '../../types/pet.type';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-pet-details',
  templateUrl: './pet-details.component.html',
  styleUrls: ['./pet-details.component.scss']
})
export class PetDetailsComponent implements OnInit {
  pet$!: Observable<Pet>;

  constructor(
    private route: ActivatedRoute,
    private petService: PetService
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      // CORREÇÃO AQUI
      this.pet$ = this.petService.getPetById(id);
    }
  }

  solicitarAdocao(petId: string) {
    // Lógica para solicitação de adoção
    console.log(`Solicitação de adoção para o pet ${petId}`);
  }
}
