import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Order } from '../model/order.model';
import { OrderDetails } from '../model/orderDetails.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private apiUrl = environment.springUrl + "/order"; // todo : change

  constructor(private http: HttpClient) { }

  getOrders(offset: number, limit: number): Observable<{ totalCount: number, orders: any[] }> {
    let params = new HttpParams()
      .set('offset', offset)
      .set('limit', limit);
  
    return this.http.get<{ totalCount: number, orders: any[] }>(`${this.apiUrl}/get`, { params });
  }

  updateOrderStatus(orderId: number, newStatus: string): Observable<any> {
    return this.http.put(`${this.apiUrl}/${orderId}/status`, { status: newStatus });
}

  deleteOrder(orderId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${orderId}/delete`);
  }

  saveOrderDetails(orderDetails: OrderDetails): Observable<any> {
    return this.http.post(`${this.apiUrl}/orders`, orderDetails);
  }

  submitOrder(cartId: number): Observable<Order> {
    return this.http.post<Order>(`${this.apiUrl}/order/submit/${cartId}`, {});
  }


}
