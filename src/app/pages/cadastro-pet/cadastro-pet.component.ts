// src/app/pages/cadastro-pet/cadastro-pet.component.ts
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PetService } from '../../services/pet.service';
import { Router } from '@angular/router';
import { Pet } from '../../types/pet.type';

@Component({
  selector: 'app-cadastro-pet',
  templateUrl: './cadastro-pet.component.html',
  styleUrls: ['./cadastro-pet.component.scss']
})
export class CadastroPetComponent {
  petForm: FormGroup;
  imageUrls: string[] = [];

  constructor(
    private fb: FormBuilder,
    private petService: PetService,
    private router: Router
  ) {
    this.petForm = this.fb.group({
      name: ['', Validators.required],
      species: ['', Validators.required],
      breed: ['', Validators.required],
      gender: ['', Validators.required],
      age: [null, [Validators.required, Validators.min(0)]],
      size: ['', Validators.required],
      color: ['', Validators.required],
      description: [''],
      city: ['', Validators.required],
    });
  }

  onFileSelected(event: any): void {
    const files = event.target.files;
    if (files) {
      for (let i = 0; i < files.length; i++) {
        const reader = new FileReader();
        reader.onload = (e: any) => {
          this.imageUrls.push(e.target.result);
        };
        reader.readAsDataURL(files[i]);
      }
    }
  }

  removeImage(index: number): void {
    this.imageUrls.splice(index, 1);
  }

  onSubmit(): void {
    if (this.petForm.invalid) {
      console.error('Formulário inválido');
      return;
    }

    if (this.imageUrls.length === 0) {
      console.error('É necessário adicionar pelo menos uma imagem.');
      return;
    }

    const petData = {
      ...this.petForm.value,
      imageUrls: this.imageUrls
    };

    this.petService.createPet(petData).subscribe({
      next: () => {
        console.log('Pet cadastrado com sucesso!');
        this.router.navigate(['/feed']);
      },
      error: (err) => {
        console.error('Erro ao cadastrar pet:', err);
      }
    });
  }
}
