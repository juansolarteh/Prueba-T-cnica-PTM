import React from "react";
import type { ColumnsType } from "antd/es/table";
import { Button, Space, Table } from "antd";
import { Producto } from "../models/Producto";

interface TablaProductosProps {
  productos: Producto[];
  onDeleteButton: (producto: Producto) => void;
  onUpdateButton: (producto: Producto) => void;
}

const TablaProductos: React.FC<TablaProductosProps> = ({ productos, onDeleteButton, onUpdateButton }) => {
  const columns: ColumnsType<Producto> = [
    { title: "ID", dataIndex: "id", key: "id", sorter: (a, b) => a.id - b.id },
    { title: "Nombre", dataIndex: "nombre", key: "nombre", sorter: (a, b) => a.nombre.localeCompare(b.nombre) },
    { title: "Descripción", dataIndex: "descripcion", key: "descripcion", sorter: (a, b) => a.descripcion.localeCompare(b.descripcion) },
    { title: "Precio", dataIndex: "precio", key: "precio", render: (text) => `$ ${text}`, sorter: (a, b) => a.precio - b.precio },
    { title: "Stock", dataIndex: "cantidadStock", key: "cantidadStock", sorter: (a, b) => a.cantidadStock - b.cantidadStock },
    { title: "Valor Inventario", dataIndex: "valorInventario", key: "valorInventario", render: (text) => `$ ${text}`, sorter: (a, b) => a.valorInventario - b.valorInventario },
    {
      title: "Acciones",
      key: "acciones",
      render: (_, record) => (
        <Space>
          <Button type="primary" onClick={() => onUpdateButton?.(record)}>Editar</Button>
          <Button danger onClick={() => onDeleteButton(record)}>Eliminar</Button>
        </Space>
      ),
    },
  ];

  return <Table columns={columns} dataSource={productos} rowKey="id" />;
};

export default TablaProductos;