import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideHttpClient } from '@angular/common/http';
import { provideToastr } from 'ngx-toastr';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideAnimationsAsync(),
    provideHttpClient(),
    provideToastr({
      timeOut: 10000, // Tempo de exibição padrão do toast
      positionClass: 'toast-bottom-right', // Posição padrão
      preventDuplicates: true, // Evita toasts duplicados
    }),
  ]
};
