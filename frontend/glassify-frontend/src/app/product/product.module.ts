import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService } from './product.service';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { ProductListComponent } from './product-list/product-list.component';





@NgModule({
  declarations: [
  
  
    ProductListComponent
  ],
  imports: [
    CommonModule,
    MatCardModule,
    MatDividerModule
  ],
  providers: [ProductService]
})
export class ProductModule { }
