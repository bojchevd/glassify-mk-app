export interface OrderItem {
    productName: string;
    songName: string;
    artistName: string;
    customDetails: string;
    albumName?: string; // Optional, if needed
    frameColor?: string; // Optional, if needed
    photoUrl?: string; // Optional, if needed
    quantity: number;
  }
  