import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ProductListComponent } from './product/product-list/product-list.component';
import { ProductViewDetailsComponent } from './product-view-details/product-view-details.component';
import { ProductResolveService } from './product-resolve.service';


const routes: Routes = [
  // Define your routes here
  { path: 'products', component: ProductListComponent },
  { path: 'productViewDetails', component: ProductViewDetailsComponent, resolve: {product: ProductResolveService}}
];


@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes),
    CommonModule,
    RouterModule
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
