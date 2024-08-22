import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  private apiUrl = 'http://localhost:8080/utility/sendMail'; 

  constructor(private http: HttpClient) { }

  sendEmail(name: string, email: string, message: string) {
    console.log(this.apiUrl + " and data " + name + email + message);
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    const body = { name, email, message };
    return this.http.post(this.apiUrl, body, { headers });
  }
}
