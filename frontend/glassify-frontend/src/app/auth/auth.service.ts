import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
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

  constructor(private http: HttpClient) { }

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
  }
}
