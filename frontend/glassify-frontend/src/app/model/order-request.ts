export class OrderRequest {
  productType: string = "";
  songName: string = "";
  artistName: string = "";
  customDetails?: string;
  photoFile?: File | null = null;
  frameColor?: string;
  templateType?: string;
  quantity: number = 1;
}
