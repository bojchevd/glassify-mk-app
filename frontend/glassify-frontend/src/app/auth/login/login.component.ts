import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {

  email: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onLogin() {
    this.authService.checkCredentials(this.email, this.password).subscribe({
      next: (response) => {
        const { token, role } = response;
        console.log('Successful login' + role)
        localStorage.setItem('userToken', token); 
        localStorage.setItem('userRole', role);  
        if (role === "ROLE_ADMIN") {
          this.router.navigate(['/admin']);
        }
      }, error: (err) => {
        console.log("Login failed", err)
        this.router.navigate(['/login']);
      }
    })
  }

  onLogout() {
    // todo : impl component html
    localStorage.removeItem('userToken');
    localStorage.removeItem('userRole');
    this.router.navigate(['/login']);
  }


}
