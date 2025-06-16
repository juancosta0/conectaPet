import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { CadastroComponent } from './pages/cadastro/cadastro.component';
import { CadastroPetComponent } from './pages/cadastro-pet/cadastro-pet.component';


export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'home',
    component: HomeComponent,
    title: 'Página Inicial',
  },
  {
    path: 'login',
    component: LoginComponent,
    title: 'Login',
  },
  {
    path: "cadastro",
    component: CadastroComponent
  },
  {
    path: "cadastro-pet",
    component: CadastroPetComponent,
    title: 'Cadastro de Pet'
  }
];
