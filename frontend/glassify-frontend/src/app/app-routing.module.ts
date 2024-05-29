import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ProductListComponent } from './product/product-list/product-list.component';


const routes: Routes = [
  // Define your routes here
  { path: 'products', component: ProductListComponent }
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
