import { Injectable } from '@angular/core';
import { ProductService } from './product/product.service';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Product } from './model/product';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductResolveService implements Resolve<Product> {

  constructor(private productService: ProductService) { }

  resolve(
    route: ActivatedRouteSnapshot, 
    state: RouterStateSnapshot): Observable<Product> {
      const id = route.paramMap.get("id");

      if (id) {
        return this.productService.getProductDetailsById(Number(id));
      } else {
        return of(this.getDefaultProduct());
      }
  }

  getDefaultProduct(): Product {
    return {
      id: 0,
      name: "",
      description: "",
      imageUrl: "",
      basePrice: 0
    };
  }
}
