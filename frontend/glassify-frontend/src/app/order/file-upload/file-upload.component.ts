import { Component } from '@angular/core';
import { HttpClient, HttpEventType, HttpHeaders, HttpRequest } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subscription } from 'rxjs';
import { FileUploadService } from '../file-upload.service';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent {
  fileName = '';
  uploadProgress = 0;
  uploadInProgress = false;
  imageUrl: string | ArrayBuffer | null = null;
  uploadSubscription?: Subscription;

  constructor(private fileUploadService: FileUploadService) {}

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    if (file) {
      this.fileName = file.name;

      // Show the image preview
      const reader = new FileReader();
      reader.onload = () => {
        this.imageUrl = reader.result;
      };
      reader.readAsDataURL(file);

      this.uploadInProgress = true;
      this.uploadSubscription = this.fileUploadService.upload(file).subscribe(event => {
        if (event.type === HttpEventType.UploadProgress) {
          // Calculate and update upload progress
          const total = event.total || 1;
          this.uploadProgress = Math.round((event.loaded / total) * 100);
        } else if (event.type === HttpEventType.Response) {
          // Upload complete, reset upload progress
          this.uploadInProgress = false;
          this.uploadProgress = 0;
          console.log('File upload complete:', event.body);
        }
      }, error => {
        console.error('File upload error:', error);
        this.uploadInProgress = false;
        this.uploadProgress = 0;
      });
    }
  }

  removeUploadedFile(): void {
    this.fileName = '';
    this.uploadProgress = 0;
    this.uploadInProgress = false;
    this.imageUrl = null;
    console.log('Uploaded file removed.');
    const fileInput = document.querySelector('.file-input') as HTMLInputElement;
    if (fileInput) {
      fileInput.value = '';
    }
  }
}