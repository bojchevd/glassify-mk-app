import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { OrderModule } from './order/order.module';
import { HttpClientModule } from '@angular/common/http'

import { AppComponent } from './app.component';
import { HomeModule } from './home/home.module';
import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { ProductModule } from './product/product.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    OrderModule,
    HttpClientModule,
    HomeModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatCardModule,
    MatDividerModule,
    ProductModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
