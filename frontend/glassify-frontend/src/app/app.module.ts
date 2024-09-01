import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { OrderModule } from './order/order.module';
import { HttpClientModule } from '@angular/common/http'

import { AppComponent } from './app.component';
import { HomeModule } from './home/home.module';
import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { ProductModule } from './product/product.module';
import { ProductViewDetailsComponent } from './product-view-details/product-view-details.component';
import { SharedModule } from './shared/shared.module';
import { ContactComponent } from './contact/contact.component';
import { LayoutModule } from './layout/layout.module';
import { MatDialogModule } from '@angular/material/dialog'; // Import MatDialogModule
import { MatButtonModule } from '@angular/material/button'; // Import MatButtonModule
import { ConfirmationDialogComponent } from './shared/confirmation-dialog/confirmation-dialog.component';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { FileUploadComponent } from './order/file-upload/file-upload.component';
import { FileUploadService } from './order/file-upload.service';




@NgModule({
  declarations: [
    AppComponent,
    ProductViewDetailsComponent,
    ContactComponent,
    ConfirmationDialogComponent,
    FileUploadComponent
  ],
  imports: [
    BrowserModule,
    OrderModule,
    HttpClientModule,
    HomeModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    SharedModule,
    ProductModule,
    LayoutModule,
    MatDialogModule,
    MatButtonModule,
    MatProgressBarModule,
    MatSnackBarModule
  ],
  providers: [
    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
