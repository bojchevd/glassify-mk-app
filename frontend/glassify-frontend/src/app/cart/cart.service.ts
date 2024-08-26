import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CartItem } from '../model/cart-item.model';
import { OrderDetails } from '../model/orderDetails.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiUrl = environment.springUrl; 

  constructor(private http: HttpClient) { }

  createCart(): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/cart/create`, {});
  }

  addItemToCart(cartId: string, item: CartItem): Observable<CartItem> {
    console.log(cartId)
    console.log(item)
    return this.http.post<CartItem>(`${this.apiUrl}/cart/${cartId}/add`, item);
  }

  removeItemFromCart(cartId: string, itemId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/cart/${cartId}/remove/${itemId}`);
  }

  clearCart(cartId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/cart/${cartId}/clear`);
  }

  getCartItems(cartId: string): Observable<CartItem[]> {
    return this.http.get<CartItem[]>(`${this.apiUrl}/cart/${cartId}/getAll`);
  }
}
