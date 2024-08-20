import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CartItem } from '../model/cart-item.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiUrl = environment.mockApi; // todo : change

  constructor(private http: HttpClient) { }

  getCartId(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/cart/create`);
  }

  addItemToCart(cartId: number, item: CartItem): Observable<CartItem> {
    return this.http.post<CartItem>(`${this.apiUrl}/cart/${cartId}/add`, item);
  }

  removeItemFromCart(cartId: number, itemId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/cart/${cartId}/remove/${itemId}`);
  }

  clearCart(cartId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/cart/${cartId}/clear`);
  }

  getCartItems(cartId: number): Observable<CartItem[]> {
    return this.http.get<CartItem[]>(`${this.apiUrl}/cart/${cartId}/getAll`);
  }
}
