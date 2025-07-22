// src/app/pages/profile/profile.component.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { UserProfile } from '../../types/user.type';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  profileForm: FormGroup;
  currentUser: UserProfile | null = null;
  // O ID pode ser number ou string, dependendo da API, mas o serviço espera string
  userId: string | number | null = null;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private authService: AuthService
  ) {
    this.profileForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      // Outros campos do formulário
    });
  }

  ngOnInit(): void {
    this.userService.currentUser$.subscribe(user => {
      this.currentUser = user;
      if (user) {
        this.userId = user.id;
        this.profileForm.patchValue(user);
      }
    });
  }

  onSave(): void {
    // CORREÇÃO AQUI
    if (this.profileForm.valid && this.userId !== null) {
      const updateData: Partial<UserProfile> = this.profileForm.value;
      // Converte o ID para string antes de enviar
      this.userService.updateUserProfile(String(this.userId), updateData).subscribe({
        next: (updatedUser: UserProfile) => {
          console.log('Perfil atualizado com sucesso!', updatedUser);
        },
        error: err => {
          console.error('Erro ao atualizar perfil', err);
        }
      });
    }
  }

  logout(): void {
    this.authService.logout();
  }
}
