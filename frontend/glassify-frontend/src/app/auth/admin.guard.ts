import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from './auth.service'; // Assuming AuthService is already created

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    console.log('AdminGuard: Checking if user is logged in...');
    if (this.authService.isLoggedIn() && this.authService.isAdmin()) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
