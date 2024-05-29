import { OrderRequest } from "./order-request";

export class CustomPhotoOrderRequest extends OrderRequest {
    photoFile: File | null = null;
}
