// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { CadastroComponent } from './pages/cadastro/cadastro.component';
import { CadastroPetComponent } from './pages/cadastro-pet/cadastro-pet.component';
import { FeedComponent } from './pages/feed/feed.component';

export const routes: Routes = [
    // Rota padrão agora é o feed
    {
        path: '',
        redirectTo: 'feed',
        pathMatch: 'full'
    },
    {
        path: "login",
        component: LoginComponent
    },
    {
        path: "cadastro",
        component: CadastroComponent
    },
    {
        path: "cadastro-pet",
        component: CadastroPetComponent
    },
    // Nova rota do feed
    {
        path: "feed",
        component: FeedComponent
    },
    // Redireciona qualquer rota não encontrada para o feed
    {
        path: '**',
        redirectTo: 'feed'
    }
];
