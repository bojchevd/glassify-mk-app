import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { ProductListComponent } from './product-list/product-list.component';
import { LayoutModule } from '../layout/layout.module';


@NgModule({
  declarations: [
    ProductListComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    LayoutModule
  ],
  exports: [
    ProductListComponent
  ]
})
export class ProductModule { }
