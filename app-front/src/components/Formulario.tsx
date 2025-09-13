import { Producto } from "../models/Producto";
import { Form, Input, InputNumber } from "antd";

interface FormularioProps {
    producto: Producto | null;
    onSave: (producto: Producto) => void;
}

const Formulario: React.FC<FormularioProps> = ({ producto, onSave }) => {
    return (
        <Form
            id="formulario-producto"
            name="basic"
            labelCol={{ span: 8 }}
            wrapperCol={{ span: 16 }}
            style={{ maxWidth: 600 }}
            initialValues={{ remember: true }}
            onFinish={onSave}
            autoComplete="off"
        >
            <Form.Item<Producto>
                label="Nombre"
                name="nombre"
                rules={[
                    { required: true, message: 'Por favor ingrese el nombre!' },
                    { whitespace: true, message: 'El nombre no puede estar en blanco!' }
                ]}
                initialValue={producto ? producto.nombre : undefined}
            >
                <Input />
            </Form.Item>

            <Form.Item<Producto>
                label="Descripción"
                name="descripcion"
                rules={[
                    { required: true, message: 'Por favor ingrese la descripción!' },
                    { whitespace: true, message: 'La descripción no puede estar en blanco!' }
                ]}
                initialValue={producto ? producto.descripcion : undefined}
            >
                <Input />
            </Form.Item>

            <Form.Item<Producto>
                label="Precio"
                name="precio"
                rules={[
                    { required: true, message: 'Por favor ingrese el precio!' }, 
                    { type: 'number', min: 0.01, message: 'El precio debe ser mayor que 0.01!' }
                ]}
                initialValue={producto ? producto.precio : undefined}
            >
                <InputNumber precision={2}/>
            </Form.Item>

            <Form.Item<Producto>
                label="Cantidad en Stock"
                name="cantidadStock"
                rules={[
                    { required: true, message: 'Por favor ingrese la cantidad en stock!' },
                    { type: 'number', min: 0, message: 'La cantidad en stock no puede ser negativa!' }
                ]}
                initialValue={producto ? producto.cantidadStock : undefined}
            >
                <InputNumber precision={0} />
            </Form.Item>
        </Form>
    );
};

export default Formulario;