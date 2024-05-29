import { ProductVariant } from "./product-variant";

export class Product {
    id: number = 0;
    name: string = "";
    description: string = "";
    variants: ProductVariant[] = [];
}
