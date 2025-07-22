// src/app/shared/shared.module.ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';

// Seus componentes compartilhados
import { HeaderComponent } from '../components/header/header.component';
import { PrimaryInputComponent } from '../components/primary-input/primary-input.component';
import { DefaultLoginLayoutComponent } from '../components/default-login-layout/default-login-layout.component';
import { PetCardComponent } from '../components/pet-card/pet-card.component';

// Módulos do Angular Material
import { MatRadioModule } from '@angular/material/radio';


@NgModule({
  declarations: [
    HeaderComponent,
    PrimaryInputComponent,
    DefaultLoginLayoutComponent,
    PetCardComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule,
    MatRadioModule
  ],
  exports: [
    HeaderComponent,
    PrimaryInputComponent,
    DefaultLoginLayoutComponent,
    PetCardComponent,
    ReactiveFormsModule, // Exporte para usar [formGroup] nos templates
    MatRadioModule // Exporte para usar mat-radio-button
  ]
})
export class SharedModule { }
