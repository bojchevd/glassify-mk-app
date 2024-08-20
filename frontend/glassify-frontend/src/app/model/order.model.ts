import { OrderItem } from './order-item.model';

export interface Order {
    id?: number; // Optional, when retrieving an existing order
    fullName: string;
    phoneNumber: string;
    city: string;
    address: string;
    totalPrice: number;
    orderStatus?: string; // Optional, if applicable
    createdAt?: string; // Optional, ISO date string
    items: OrderItem[]; // List of order items
  }
  