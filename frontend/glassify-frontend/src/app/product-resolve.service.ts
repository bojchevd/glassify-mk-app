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

      if(id) {
        // fetch details from backend
        return this.productService.getProductDetailsById(id)
      } else {
        // return empty product Observable
        return of(this.getProductDetails());
      }
  }
  getProductDetails() {
    return {
      id: 0,
      name: "",
      description: "",
      imageUrl: "",
      salesPrice: 0,
      cost: 0,
      quantity: 0,
    }
  }
  

}

