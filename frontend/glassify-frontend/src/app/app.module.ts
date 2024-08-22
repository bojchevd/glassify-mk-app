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




@NgModule({
  declarations: [
    AppComponent,
    ProductViewDetailsComponent,
    ContactComponent
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
    LayoutModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
