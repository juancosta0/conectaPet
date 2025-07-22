// src/app/pages/favorites/favorites.component.ts
import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { switchMap, map } from 'rxjs/operators'; // Importar o 'map'
import { Pet } from '../../types/pet.type';
import { FavoritesService } from '../../services/favorites.service';
import { PetService } from '../../services/pet.service';

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.scss']
})
export class FavoritesComponent implements OnInit {
  favoritePets$!: Observable<Pet[]>;

  constructor(
    private favoritesService: FavoritesService,
    private petService: PetService
  ) {}

  ngOnInit(): void {
    this.favoritePets$ = this.favoritesService.favorites$.pipe(
      // CORREÇÃO AQUI
      map(idsAsNumbers => idsAsNumbers.map(id => String(id))), // Converte number[] para string[]
      switchMap(idsAsStrings => {
        if (idsAsStrings.length === 0) {
          return of([]);
        }
        return this.petService.getPetsByIds(idsAsStrings);
      })
    );
  }
}
