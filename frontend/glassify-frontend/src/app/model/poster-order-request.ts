import { OrderRequest } from "./order-request";

export class PosterOrderRequest extends OrderRequest {
    framed: boolean = false;
    frameColor: string = "";
}
