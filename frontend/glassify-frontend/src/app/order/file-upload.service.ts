import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

  private uploadUrl = environment.springUrl + '/order/upload';

  constructor(private http: HttpClient) {}

  upload(file: File, fileName: string): Observable<any> {
    console.log("Uploading " + fileName);
    const formData = new FormData();
    formData.append('file', file, fileName);
  
    return this.http.post<any>(this.uploadUrl, formData, {
      observe: 'response'
    });
  }
  
}
