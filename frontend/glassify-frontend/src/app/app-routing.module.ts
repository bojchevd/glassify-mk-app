import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ProductViewDetailsComponent } from './product-view-details/product-view-details.component';
import { ProductResolveService } from './product-resolve.service';
import { LandingPageComponent } from './home/landing-page/landing-page.component';
import { ContactComponent } from './contact/contact.component';
import { ProductPageComponent } from './product-page/product-page.component';

const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'products', component: ProductPageComponent },
  { path: 'productViewDetails/:id', component: ProductViewDetailsComponent, resolve: { product: ProductResolveService } },
  { path: 'contact', component: ContactComponent }
];


@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes),
    CommonModule,
    RouterModule,
    LandingPageComponent
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
