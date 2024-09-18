import { Component, EventEmitter, Output } from '@angular/core';
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
  imageUrl: string | ArrayBuffer | null = null;

  @Output() uploadComplete = new EventEmitter<File>();

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

      // Emit the file object
      this.uploadComplete.emit(file);
    }
  }

  removeUploadedFile(): void {
    this.fileName = '';
    this.imageUrl = null;
    console.log('Uploaded file removed.');
    const fileInput = document.querySelector('.file-input') as HTMLInputElement;
    if (fileInput) {
      fileInput.value = '';
    }
  }
}