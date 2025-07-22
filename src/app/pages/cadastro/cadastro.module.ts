// Exemplo: src/app/pages/cadastro/cadastro.module.ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CadastroComponent } from './cadastro.component';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  declarations: [
    CadastroComponent
  ],
  imports: [
    CommonModule,
    SharedModule // <-- ADICIONE AQUI
  ]
})
export class CadastroModule { }
