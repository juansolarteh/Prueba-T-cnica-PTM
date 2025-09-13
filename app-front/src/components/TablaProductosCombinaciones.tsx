import React from "react";
import type { ColumnsType } from "antd/es/table";
import { Table } from "antd";
import { Combinacion, ProductoCombinacion } from "../models/Producto";

interface TablaProductosProps {
  combinaciones: Combinacion[];
}

const TablaProductosCombinaciones: React.FC<TablaProductosProps> = ({ combinaciones }) => {
  const mapProductosToString = (productos: ProductoCombinacion): string => {
    return productos.nombre + " (id: " + productos.id + " - precio: $ " + productos.precio + ")";
  }

  const columns: ColumnsType<Combinacion> = [
    {
      title: "Productos", dataIndex: "productos", key: "productos",
      render: (_, record) => (
        <>{record.productos.map((element) => (
          <div key={element.id}>
            {mapProductosToString(element)}
          </div>
        ))}</>
      )
    },
    { title: "Valor Sumatoria", dataIndex: "valorSumatoria", key: "valorSumatoria", render: (text) => `$ ${text}` },
  ];

  return (
    <>
      {combinaciones && combinaciones.length > 0 ? (
        <Table columns={columns} dataSource={combinaciones} rowKey="id" pagination={false} />
      ) : (
        <p>No hay combinaciones disponibles con el valor de comparación ingresado.</p>
      )}
    </>
  );
};

export default TablaProductosCombinaciones;