import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

interface AuthResponse {
  token: string;
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl : string = environment.springUrl + '/auth';

  constructor(private http: HttpClient,
    private router: Router
  ) { }

  checkCredentials(email: string, password: string): Observable<AuthResponse> {
    const payload = { email, password };
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, payload);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('userToken'); 
  }

  isAdmin(): boolean {
    const userRole = localStorage.getItem('userRole'); 
    return userRole === 'ROLE_ADMIN'; 
  }

  logout(): void {
    localStorage.removeItem('userToken');
    localStorage.removeItem('userRole'); 
    this.router.navigate(['/login']); // Optional: Redirect to login page after logout
  }
}
