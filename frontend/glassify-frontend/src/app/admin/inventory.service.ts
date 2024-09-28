import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface InventoryItem {
  id: number;
  name: string;
  stockQuantity: number;
  vendor: string;
  contactPhoneNumber: string;
  contactEmail: string;
  updatedAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class InventoryService {

  private baseUrl = environment.springUrl + "/inventory"

  constructor(private http: HttpClient) {}

  getInventory(): Observable<InventoryItem[]> {
    return this.http.get<InventoryItem[]>(`${this.baseUrl}/get`);
  }

  deleteInventoryItem(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
  }

  updateStockQuantity(id: number, newQuantity: number): Observable<any> {
    const params = new HttpParams().set('stockQuantity', newQuantity.toString());
    return this.http.put<InventoryItem>(`${this.baseUrl}/update-stock/${id}`, {}, { params });
  }
}
