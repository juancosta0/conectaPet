// src/app/pages/cadastro/cadastro.component.ts
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { User } from '../../types/user.type'; // Importar tipo User

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.scss']
})
export class CadastroComponent {
  registerForm: FormGroup;
  userType: 'tutor' | 'ong' = 'tutor';

  constructor(
    private fb: FormBuilder,
    private loginService: LoginService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      userType: ['tutor', Validators.required],
      cnpj: [''],
      description: [''],
      city: ['']
    });

    this.registerForm.get('userType')?.valueChanges.subscribe(value => {
      this.userType = value;
      const cnpjControl = this.registerForm.get('cnpj');
      if (value === 'ong') {
        cnpjControl?.setValidators([Validators.required]);
      } else {
        cnpjControl?.clearValidators();
      }
      cnpjControl?.updateValueAndValidity();
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      // Garantir que o tipo está correto
      const registerData: Omit<User, 'id' | 'createdAt'> = this.registerForm.value;

      this.loginService.register(registerData).subscribe({
        next: () => {
          console.log('Usuário registrado com sucesso!');
          this.router.navigate(['/login']);
        },
        error: (err) => {
          console.error('Erro no registro', err);
        }
      });
    }
  }
}
