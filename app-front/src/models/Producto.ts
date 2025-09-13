export interface Producto {
  id: number;
  nombre: string;
  descripcion: string;
  precio: number;
  cantidadStock: number;
  valorInventario: number;
}

export interface ProductoCombinacion {
  id: number;
  nombre: string;
  precio: number;
}

export interface Combinacion {
  productos: ProductoCombinacion[];
  valorSumatoria: number;
}