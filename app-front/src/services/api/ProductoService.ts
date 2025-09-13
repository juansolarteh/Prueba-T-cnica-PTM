import axios from "axios";
import { Combinacion, Producto } from "../../models/Producto";

const API_URL = process.env.REACT_APP_API_URL || "http://localhost:8080/productos";

export const ProductoService = {
  getAll: async (): Promise<Producto[]> => {
    const response = await axios.get(API_URL);
    return response.data;
  },
  create: async (producto: Producto): Promise<Producto> => {
    const response = await axios.post(API_URL, producto);
    return response.data;
  },
  delete: async (id: number): Promise<void> => {
    await axios.delete(`${API_URL}/${id}`);
  },
  update: async (id: number, producto: Producto): Promise<Producto> => {
    const response = await axios.put(`${API_URL}/${id}`, producto);
    return response.data;
  },
  getCombinaciones: async (valorComparacion: number): Promise<Combinacion[]> => {
    const response = await axios.get(`${API_URL}/combinaciones`, {
      params: { valorComparacion }
    });
    return response.data;
  }
};