import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.prod';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../model/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private springUrl = environment.springUrl;
  private mockApi = environment.mockApi;

  constructor(private http: HttpClient) {}

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.mockApi + "/products")
  }
}
