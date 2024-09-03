import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { CartItem } from '../model/cart-item.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiUrl = environment.springUrl;
  private cartId: string | null = null;

  constructor(private http: HttpClient) { }

  createCart(): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/cart/create`, {});
  }

  addItemToCart(cartId: string, item: CartItem): Observable<CartItem> {
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

  getCartId(): string | null {
    return this.cartId;
  }

  setCartId(id: string): void {
    this.cartId = id;
  }

  createAndStoreCart(): Observable<string> {
    return this.createCart().pipe(
      tap(cartId => this.setCartId(cartId))
    );
  }
}
