// src/app/pages/login/login.component.ts
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { LoginRequest } from '../../types/login-request.type'; // Importar tipo

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private loginService: LoginService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      // CORREÇÃO AQUI
      const loginRequest: LoginRequest = this.loginForm.value;
      this.loginService.login(loginRequest).subscribe({
        next: () => {
          this.router.navigate(['/feed']);
        },
        error: (err) => {
          console.error('Login falhou', err);
          // Adicionar feedback para o usuário aqui
        }
      });
    }
  }
}
