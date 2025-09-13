import { useEffect, useState } from 'react';
import './App.css';
import TablaProductos from './components/TablaProductos';
import { Combinacion, Producto } from './models/Producto';
import { Alert, Button, InputNumber, Modal, notification } from 'antd';
import Formulario from './components/Formulario';
import { ProductoService } from './services/api/ProductoService';
import TablaProductosCombinaciones from './components/TablaProductosCombinaciones';
import { GatosService } from './services/api/GatosService';
import { DatoDiaService } from './services/api/DatoDIaService';
import { PlusOutlined, SearchOutlined } from '@ant-design/icons';

type NotificationType = 'success' | 'info' | 'warning' | 'error';

function App() {

  const [api, contextHolder] = notification.useNotification();
  const [confirmLoading, setConfirmLoading] = useState(false);

  const [productos, setProductos] = useState<Producto[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [productoAEliminar, setProductoAEliminar] = useState<Producto | null>(null);
  const [productoAEditar, setProductoAEditar] = useState<Producto | null>(null);
  const [valorComparacion, setValorComparacion] = useState<number | null>(null);
  const [combinaciones, setCombinaciones] = useState<Combinacion[] | null>(null);
  const [infoGatos, setInfoGatos] = useState<string[] | null>(null);
  const [datoInutil, setDatoInutil] = useState<string>("");


  useEffect(() => {
    ProductoService.getAll()
      .then(setProductos)
      .catch(handleError);

    GatosService.getInformacionGatos()
      .then((info) => setInfoGatos(info))
      .catch(() => openNotification("error", "Error al obtener información de gatos", "No se pudo obtener la información de gatos."));

    DatoDiaService.getDatoDia()
      .then((dato) => setDatoInutil(dato))
      .catch(() => openNotification("error", "Error al obtener dato del día", "No se pudo obtener el dato del día."));
  }, []);

  const openNotification = (type: NotificationType, message: string, description: string) => {
    api[type]({
      message: message,
      description: description,
    });
  };

  const showModal = () => {
    setIsModalOpen(true);
  };

  const showCombinacionesModal = () => {
    if (valorComparacion !== null && valorComparacion > 0){
      ProductoService.getCombinaciones(valorComparacion).then((combs) => {
        setCombinaciones(combs);
      });
    }
  };

  const showDeleteConfirmation = (producto: Producto) => {
    setProductoAEliminar(producto);
  };

  const showUpdateModal = (producto: Producto) => {
    setProductoAEditar(producto);
    setIsModalOpen(true);
  }

  const handleModalCancel = () => {
    setIsModalOpen(false);
    setProductoAEditar(null);
  };

  const handleCombinacionesModalClose = () => {
    setCombinaciones(null);
  };

  const handleDeleteConfirmationCancel = () => {
    setProductoAEliminar(null);
  };

  const handleInfoGatosModalClose = () => {
    setInfoGatos(null);
  };

  const handleOnSave = (producto: Producto) => {
    setConfirmLoading(true);
    ProductoService.create(producto).then((nuevoProducto) => {
      setProductos((prev) => [...prev, nuevoProducto]);
      setIsModalOpen(false);
      openNotification("success", "Producto creado", `El producto '${nuevoProducto.nombre}' ha sido creado con éxito.`);
    }).catch(handleError).finally(() => {
      setConfirmLoading(false);
    });
  };

  const handleOnUpdate = (producto: Producto) => {
    if (productoAEditar) {
      setConfirmLoading(true);
      ProductoService.update(productoAEditar.id, producto).then((productoActualizado) => {
        setProductos((prev) => prev.map((p) => p.id === productoActualizado.id ? productoActualizado : p));
        setProductoAEditar(null);
        setIsModalOpen(false);
        openNotification("success", "Producto actualizado", `El producto '${productoActualizado.nombre}' ha sido actualizado con éxito.`);
      }).catch(handleError).finally(() => {
        setConfirmLoading(false);
      });
    }
  }

  const handleOnDelete = () => {
    if (productoAEliminar) {
      setConfirmLoading(true);
      ProductoService.delete(productoAEliminar.id).then(() => {
        setProductos((prev) => prev.filter((p) => p.id !== productoAEliminar.id));
        setProductoAEliminar(null);
        openNotification("success", "Producto eliminado", `El producto '${productoAEliminar.nombre}' ha sido eliminado con éxito.`);
      }).catch(handleError).finally(() => {
        setConfirmLoading(false);
      });
    }
  };

  const handleError = (error: any) => {
    if (error.message === "Network Error") {
      openNotification("error", "Error de conexión", "No se pudo conectar con el servidor de productos. Por favor, intente más tarde.");
    } else {
      openNotification("error", "Error con la API", "Ocurrió un error al obtener los productos. Por favor, intente más tarde.");
    }
  };

  const getProductoMayorValorInventario = (): string => {
    if (productos.length === 0) return "No hay productos por el momento.";
    const producto = productos.reduce((prev, current) => (prev.valorInventario > current.valorInventario) ? prev : current);
    return `El producto con mayor valor de inventario es '${producto.nombre}' con id ${producto.id} y un valor de inventario de $${producto.valorInventario}.`;
  }

  return (
    <div className='app-container'>
      {contextHolder}

      <Alert
        className='custom-alert'
        message="Producto con mayor valor de inventario"
        description={getProductoMayorValorInventario()}
        type={productos.length === 0 ? "warning" : "info"}
        showIcon
      />

      <div className='actions-row'>
        <Button type="primary" onClick={showModal} icon={<PlusOutlined />}>Agregar</Button>
        <div className='right-actions'>
          <InputNumber addonBefore="Valor de comparación de combinaciones" precision={2} min={0.01} value={valorComparacion} onChange={setValorComparacion} />
          <Button type="primary" onClick={showCombinacionesModal} icon={<SearchOutlined />}>Buscar</Button>
        </div>
      </div>

      <div className='custom-table'>
        <TablaProductos
          productos={productos}
          onDeleteButton={showDeleteConfirmation}
          onUpdateButton={showUpdateModal} />
      </div>

      <footer className='app-footer'>
        <p><strong>Dato del día:</strong> {datoInutil || "Cargando..."}</p>
      </footer>

      <Modal
        title={productoAEditar ? "Editar Producto" : "Agregar Producto"}
        open={isModalOpen}
        destroyOnHidden
        onCancel={handleModalCancel}
        maskClosable={!confirmLoading}
        closable={!confirmLoading}
        keyboard={!confirmLoading}
        footer={[
          <Button key="back" onClick={handleModalCancel} loading={confirmLoading}>Cancelar</Button>,
          <Button key="submit" type="primary" htmlType='submit' form='formulario-producto' loading={confirmLoading}>{productoAEditar ? "Editar" : "Guardar"}</Button>
        ]}
      >
        <Formulario producto={productoAEditar} onSave={productoAEditar ? handleOnUpdate : handleOnSave} />
      </Modal>

      <Modal
        title="Eliminar Producto"
        open={productoAEliminar !== null}
        onCancel={handleDeleteConfirmationCancel}
        maskClosable={!confirmLoading}
        closable={!confirmLoading}
        keyboard={!confirmLoading}
        footer={[
          <Button key="back" onClick={handleDeleteConfirmationCancel} loading={confirmLoading}>Cancelar</Button>,
          <Button key="submit" type="primary" danger onClick={handleOnDelete} loading={confirmLoading}>Eliminar</Button>
        ]}
      >
        <p>¿Está seguro que desea eliminar el producto '{productoAEliminar?.nombre}' con ID {productoAEliminar?.id}?</p>
      </Modal>

      <Modal
        title="Combinaciones de productos"
        open={combinaciones !== null}
        onCancel={handleCombinacionesModalClose}
        footer={[
          <Button type='primary' onClick={handleCombinacionesModalClose}>Ok</Button>
        ]}
      >
        <TablaProductosCombinaciones combinaciones={combinaciones ? combinaciones : []} />
      </Modal>

      <Modal
        title="Sabías que..."
        open={infoGatos !== null}
        onCancel={handleInfoGatosModalClose}
        footer={[
          <Button type='primary' onClick={handleInfoGatosModalClose}>Ok</Button>
        ]}
      >
        <ul>
          {infoGatos?.map((dato, index) => (
            <li key={index}>{dato}</li>
          ))}
        </ul>
      </Modal>
    </div>
  );
}

export default App;
