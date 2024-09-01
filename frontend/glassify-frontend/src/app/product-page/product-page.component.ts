import { Component } from '@angular/core';
import { ProductModule } from '../product/product.module';
import { LayoutModule } from '../layout/layout.module';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  standalone: true,
  imports: [ProductModule, 
  LayoutModule]  
})
export class ProductPageComponent { }
