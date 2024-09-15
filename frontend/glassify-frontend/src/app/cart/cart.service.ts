import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable,  of} from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { CartItem } from '../model/cart-item.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private apiUrl = environment.springUrl;
  private cartIdKey = 'cartId';

  constructor(private http: HttpClient) { }

  getCartId(): string | null {
    return sessionStorage.getItem(this.cartIdKey);
  }

  createCart(): Observable<string> {
    return this.http.post<string>(`${this.apiUrl}/cart/create`, {}).pipe(
      switchMap(cartId => {
        sessionStorage.setItem(this.cartIdKey, cartId);
        return of(cartId);
      })
    );
  }

  addItemToCart(item: CartItem): Observable<CartItem> {
    let cartId = this.getCartId();
    if (!cartId) {
      return this.createCart().pipe(
        switchMap(newCartId => {
          return this.http.post<CartItem>(`${this.apiUrl}/cart/${newCartId}/add`, item);
        })
      );
    } else {
      return this.http.post<CartItem>(`${this.apiUrl}/cart/${cartId}/add`, item);
    }
  }

  removeItemFromCart(itemId: string): Observable<void> {
    let cartId = this.getCartId();
    if (cartId) {
      return this.http.delete<void>(`${this.apiUrl}/cart/${cartId}/items/${itemId}`);
    } else {
      return of(undefined);
    }
  }

  clearCart(): Observable<void> {
    let cartId = this.getCartId();
    if (cartId) {
    return this.http.delete<void>(`${this.apiUrl}/cart/${cartId}/clear`);
    } else throw new Error("no cart created");
  }

  getCartItems(): Observable<CartItem[]> {
    let cartId = this.getCartId();
    if (cartId) {
      return this.http.get<CartItem[]>(`${this.apiUrl}/cart/${cartId}/getAll`);
    } else throw new Error("no cart created");
  }

}
