import { Component } from '@angular/core';
import { DefaultLoginLayoutComponent } from '../../components/default-login-layout/default-login-layout.component';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PrimaryInputComponent } from '../../components/primary-input/primary-input.component';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CommonModule } from '@angular/common';

// Interface atualizada para suportar múltiplos arquivos
interface PetForm {
  nome: FormControl<string | null>;
  tipoAnimal: FormControl<string | null>;
  raca: FormControl<string | null>;
  idade: FormControl<number | null>;
  porte: FormControl<string | null>;
  cor: FormControl<string | null>;
  descricao: FormControl<string | null>;
  imagens: FormControl<File[] | null>; // Alterado para um array de arquivos
}

@Component({
  selector: 'app-cadastro-pet',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    DefaultLoginLayoutComponent,
    PrimaryInputComponent
  ],
  templateUrl: './cadastro-pet.component.html',
  styleUrls: ['./cadastro-pet.component.scss']
})
export class CadastroPetComponent {
  petForm: FormGroup<PetForm>;
  previewUrls: (string | ArrayBuffer)[] = [];

  constructor(
    private router: Router,
    private toastr: ToastrService
  ) {
    this.petForm = new FormGroup({
      nome: new FormControl('', [Validators.required]),
      tipoAnimal: new FormControl('', [Validators.required]),
      raca: new FormControl('', [Validators.required]),
      idade: new FormControl<number | null>(null, [Validators.required, Validators.min(0)]),
      porte: new FormControl('', [Validators.required]),
      cor: new FormControl('', [Validators.required]),
      descricao: new FormControl('', [Validators.required, Validators.maxLength(300)]),
      imagens: new FormControl<File[]>([]) // Inicializa com um array vazio
    });
  }

  // Método para lidar com a seleção de múltiplos arquivos
  onFileSelected(event: Event): void {
    const files = (event.target as HTMLInputElement).files;
    if (files) {
      const currentFiles = this.petForm.get('imagens')?.value || [];
      const newFiles = Array.from(files);

      // Atualiza o valor no formulário
      this.petForm.patchValue({ imagens: [...currentFiles, ...newFiles] });
      this.petForm.get('imagens')?.updateValueAndValidity();

      // Gera as URLs para pré-visualização
      newFiles.forEach(file => {
        const reader = new FileReader();
        reader.onload = () => {
          this.previewUrls.push(reader.result as string);
        };
        reader.readAsDataURL(file);
      });
    }
  }

  // Método para remover uma imagem da lista
  removeImage(index: number): void {
    // Remove a URL de pré-visualização
    this.previewUrls.splice(index, 1);

    // Remove o arquivo do FormControl
    const currentFiles = this.petForm.get('imagens')?.value || [];
    currentFiles.splice(index, 1);
    this.petForm.patchValue({ imagens: currentFiles });
    this.petForm.get('imagens')?.updateValueAndValidity();
  }


  submit() {
    if (this.petForm.valid) {
      // Lógica para enviar os dados, incluindo o array de imagens
      console.log(this.petForm.value);
      this.toastr.success('Pet cadastrado com sucesso!');
    } else {
      this.toastr.error('Por favor, preencha todos os campos corretamente.');
    }
  }

  navigate() {
    this.router.navigate(['/home']);
  }
}
