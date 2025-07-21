import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { AuthService } from '../../services/auth.service';
@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterLink], 
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  isAuthenticated$: Observable<boolean>;

  constructor(private authService: AuthService) {
    this.isAuthenticated$ = this.authService.isAuthenticated$;
  }

  logout(): void {
    this.authService.logout();
  }
}
