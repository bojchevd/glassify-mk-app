import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from 'src/app/model/product';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[] = [];

  constructor(
    private productService: ProductService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.productService.getProducts().subscribe(data => {
      this.products = data;
    })
  }

  getImageUrl(imageUrl: string | null): string {
    return imageUrl ? `assets/${imageUrl}` : 'assets/default-image.jpg';
  }

  showProductDetails(id: number) {
    this.router.navigate(['/productViewDetails', id]);
  }
  

}
