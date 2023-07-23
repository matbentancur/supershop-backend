package grupo1.supershop.controladores;

import grupo1.supershop.beans.Categoria;
import grupo1.supershop.config.properties.PropertyReader;
import grupo1.supershop.config.report.listenertest.ListenerTest;
import grupo1.supershop.config.servicios.ServicioIniciarSesion;
import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtCategoria;
import grupo1.supershop.datatypes.DtProducto;
import grupo1.supershop.fabricas.FabricaProducto;
import grupo1.supershop.instalar.InstalarSistema;
import grupo1.supershop.interfaces.controladores.IControladorProducto;
import grupo1.supershop.persistencia.manejadores.ManejadorCategoria;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

@ExtendWith(ListenerTest.class)
class ControladorProductoTest {
    private static IControladorProducto iControladorProducto;

    @BeforeAll
    static void configuracion() throws Exception {
        iControladorProducto = FabricaProducto.getIControladorProducto();
        new InstalarSistema().instalar();
    }

    @Test
    @DisplayName("Obtener producto nombre")
    void obtenerProductoNombre() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String nombre = PropertyReader.getInstance().getTestValue("ControladorProducto.obtenerProductoNombre.nombre");
        String borrado = PropertyReader.getInstance().getTestValue("ControladorProducto.obtenerProductoNombre.borrado");
        String peso = PropertyReader.getInstance().getTestValue("ControladorProducto.obtenerProductoNombre.peso");
        String precio = PropertyReader.getInstance().getTestValue("ControladorProducto.obtenerProductoNombre.precio");
        String categoria = PropertyReader.getInstance().getTestValue("ControladorProducto.obtenerProductoNombre.categoria");
        String id = PropertyReader.getInstance().getTestValue("ControladorProducto.obtenerProductoNombre.id");
        String barcode = PropertyReader.getInstance().getTestValue("ControladorProducto.obtenerProductoNombre.barcode");
        String descripcion = PropertyReader.getInstance().getTestValue("ControladorProducto.obtenerProductoNombre.descripcion");

        DtProducto dtProducto = iControladorProducto.obtenerProductoNombre(nombre, secreto);

        ServicioVerificacion.getInstance().verificar(nombre, dtProducto.getNombre());
        ServicioVerificacion.getInstance().verificar(Boolean.valueOf(borrado), dtProducto.isBorrado());
        ServicioVerificacion.getInstance().verificar(Integer.parseInt(peso), dtProducto.getPeso());
        ServicioVerificacion.getInstance().verificar(new BigDecimal(precio), dtProducto.getPrecio());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(categoria), dtProducto.getDtCategoria().getId());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(id), dtProducto.getId());
        ServicioVerificacion.getInstance().verificar(barcode, dtProducto.getBarcode());
        ServicioVerificacion.getInstance().verificar(descripcion, dtProducto.getDescripcion());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Obtener producto barcode")
    void obtenerProductoBarcode() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String nombre = PropertyReader.getInstance().getTestValue("ControladorProducto.obtenerProductoBarcode.nombre");
        String borrado = PropertyReader.getInstance().getTestValue("ControladorProducto.obtenerProductoBarcode.borrado");
        String peso = PropertyReader.getInstance().getTestValue("ControladorProducto.obtenerProductoBarcode.peso");
        String precio = PropertyReader.getInstance().getTestValue("ControladorProducto.obtenerProductoBarcode.precio");
        String categoria = PropertyReader.getInstance().getTestValue("ControladorProducto.obtenerProductoBarcode.categoria");
        String id = PropertyReader.getInstance().getTestValue("ControladorProducto.obtenerProductoBarcode.id");
        String barcode = PropertyReader.getInstance().getTestValue("ControladorProducto.obtenerProductoBarcode.barcode");
        String descripcion = PropertyReader.getInstance().getTestValue("ControladorProducto.obtenerProductoBarcode.descripcion");

        DtProducto dtProducto = iControladorProducto.obtenerProductoBarcode(barcode);

        ServicioVerificacion.getInstance().verificar(nombre, dtProducto.getNombre());
        ServicioVerificacion.getInstance().verificar(Boolean.valueOf(borrado), dtProducto.isBorrado());
        ServicioVerificacion.getInstance().verificar(Integer.parseInt(peso), dtProducto.getPeso());
        ServicioVerificacion.getInstance().verificar(new BigDecimal(precio), dtProducto.getPrecio());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(categoria), dtProducto.getDtCategoria().getId());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(id), dtProducto.getId());
        ServicioVerificacion.getInstance().verificar(barcode, dtProducto.getBarcode());
        ServicioVerificacion.getInstance().verificar(descripcion, dtProducto.getDescripcion());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Listar productos")
    void listarProductos() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String cantidad = PropertyReader.getInstance().getTestValue("ControladorProducto.listarProductos.cantidad");
        int cantidadProductos = iControladorProducto.listarProductos().size();

        ServicioVerificacion.getInstance().verificarTrue((Integer.parseInt(cantidad) >= cantidadProductos));

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Crear producto")
    void crearProducto() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String nombre = PropertyReader.getInstance().getTestValue("ControladorProducto.crearProducto.nombre");
        String peso = PropertyReader.getInstance().getTestValue("ControladorProducto.crearProducto.peso");
        String precio = PropertyReader.getInstance().getTestValue("ControladorProducto.crearProducto.precio");
        String barcode = PropertyReader.getInstance().getTestValue("ControladorProducto.crearProducto.barcode");
        String descripcion = PropertyReader.getInstance().getTestValue("ControladorProducto.crearProducto.descripcion");
        String categoriaId = PropertyReader.getInstance().getTestValue("ControladorProducto.crearProducto.categoriaId");

        DtProducto dtProducto = new DtProducto();
        dtProducto.setNombre(nombre);
        dtProducto.setPeso(Integer.parseInt(peso));
        dtProducto.setPrecio(new BigDecimal(precio));
        dtProducto.setBarcode(barcode);
        dtProducto.setDescripcion(descripcion);
        Categoria categoria = ManejadorCategoria.getInstance().obtenerCategoria(Long.parseLong(categoriaId));
        dtProducto.setDtCategoria((DtCategoria) categoria.getDataType());

        iControladorProducto.crearProducto(dtProducto, secreto);
        DtProducto dtPosProducto = iControladorProducto.obtenerProductoNombre(nombre,secreto);

        ServicioVerificacion.getInstance().verificar(nombre, dtPosProducto.getNombre());
        ServicioVerificacion.getInstance().verificar(Integer.parseInt(peso), dtPosProducto.getPeso());
        ServicioVerificacion.getInstance().verificar(new BigDecimal(precio), dtPosProducto.getPrecio());
        ServicioVerificacion.getInstance().verificar(barcode, dtPosProducto.getBarcode());
        ServicioVerificacion.getInstance().verificar(descripcion, dtPosProducto.getDescripcion());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(categoriaId), dtPosProducto.getDtCategoria().getId());
        ServicioVerificacion.getInstance().verificarFalse(dtPosProducto.isBorrado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Modificar producto")
    void modificarProducto() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String nombre = PropertyReader.getInstance().getTestValue("ControladorProducto.modificarProducto.nombre");
        String peso = PropertyReader.getInstance().getTestValue("ControladorProducto.modificarProducto.peso");
        String precio = PropertyReader.getInstance().getTestValue("ControladorProducto.modificarProducto.precio");
        String barcode = PropertyReader.getInstance().getTestValue("ControladorProducto.modificarProducto.barcode");
        String descripcion = PropertyReader.getInstance().getTestValue("ControladorProducto.modificarProducto.descripcion");
        String categoriaId = PropertyReader.getInstance().getTestValue("ControladorProducto.modificarProducto.categoriaId");
        String nombreNuevo = PropertyReader.getInstance().getTestValue("ControladorProducto.modificarProducto.nombreNuevo");
        String pesoNuevo = PropertyReader.getInstance().getTestValue("ControladorProducto.modificarProducto.pesoNuevo");
        String precioNuevo = PropertyReader.getInstance().getTestValue("ControladorProducto.modificarProducto.precioNuevo");
        String barcodeNuevo = PropertyReader.getInstance().getTestValue("ControladorProducto.modificarProducto.barcodeNuevo");
        String descripcionNuevo = PropertyReader.getInstance().getTestValue("ControladorProducto.modificarProducto.descripcionNuevo");
        String categoriaIdNuevo = PropertyReader.getInstance().getTestValue("ControladorProducto.modificarProducto.categoriaIdNuevo");

        DtProducto dtProducto = new DtProducto();
        dtProducto.setNombre(nombre);
        dtProducto.setPeso(Integer.parseInt(peso));
        dtProducto.setPrecio(new BigDecimal(precio));
        dtProducto.setBarcode(barcode);
        dtProducto.setDescripcion(descripcion);
        Categoria categoria = ManejadorCategoria.getInstance().obtenerCategoria(Long.parseLong(categoriaId));
        dtProducto.setDtCategoria((DtCategoria) categoria.getDataType());

        iControladorProducto.crearProducto(dtProducto, secreto);
        DtProducto dtPosProducto = iControladorProducto.obtenerProductoNombre(nombre,secreto);

        ServicioVerificacion.getInstance().verificar(nombre, dtPosProducto.getNombre());
        ServicioVerificacion.getInstance().verificar(Integer.parseInt(peso), dtPosProducto.getPeso());
        ServicioVerificacion.getInstance().verificar(new BigDecimal(precio), dtPosProducto.getPrecio());
        ServicioVerificacion.getInstance().verificar(barcode, dtPosProducto.getBarcode());
        ServicioVerificacion.getInstance().verificar(descripcion, dtPosProducto.getDescripcion());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(categoriaId), dtPosProducto.getDtCategoria().getId());
        ServicioVerificacion.getInstance().verificarFalse(dtPosProducto.isBorrado());

        dtPosProducto.setNombre(nombreNuevo);
        dtPosProducto.setPeso(Integer.parseInt(pesoNuevo));
        dtPosProducto.setPrecio(new BigDecimal(precioNuevo));
        dtPosProducto.setBarcode(barcodeNuevo);
        dtPosProducto.setDescripcion(descripcionNuevo);
        categoria = ManejadorCategoria.getInstance().obtenerCategoria(Long.parseLong(categoriaIdNuevo));
        dtPosProducto.setDtCategoria((DtCategoria) categoria.getDataType());

        iControladorProducto.modificarProducto(dtPosProducto, secreto);

        dtPosProducto = iControladorProducto.obtenerProductoNombre(nombreNuevo, secreto);

        ServicioVerificacion.getInstance().verificar(nombreNuevo, dtPosProducto.getNombre());
        ServicioVerificacion.getInstance().verificar(Integer.parseInt(pesoNuevo), dtPosProducto.getPeso());
        ServicioVerificacion.getInstance().verificar(new BigDecimal(precioNuevo), dtPosProducto.getPrecio());
        ServicioVerificacion.getInstance().verificar(barcodeNuevo, dtPosProducto.getBarcode());
        ServicioVerificacion.getInstance().verificar(descripcionNuevo, dtPosProducto.getDescripcion());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(categoriaIdNuevo), dtPosProducto.getDtCategoria().getId());
        ServicioVerificacion.getInstance().verificarFalse(dtPosProducto.isBorrado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Borrar producto")
    void borrarProducto() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String nombre = PropertyReader.getInstance().getTestValue("ControladorProducto.borrarProducto.nombre");
        String peso = PropertyReader.getInstance().getTestValue("ControladorProducto.borrarProducto.peso");
        String precio = PropertyReader.getInstance().getTestValue("ControladorProducto.borrarProducto.precio");
        String barcode = PropertyReader.getInstance().getTestValue("ControladorProducto.borrarProducto.barcode");
        String descripcion = PropertyReader.getInstance().getTestValue("ControladorProducto.borrarProducto.descripcion");
        String categoriaId = PropertyReader.getInstance().getTestValue("ControladorProducto.borrarProducto.categoriaId");

        DtProducto dtProducto = new DtProducto();
        dtProducto.setNombre(nombre);
        dtProducto.setPeso(Integer.parseInt(peso));
        dtProducto.setPrecio(new BigDecimal(precio));
        dtProducto.setBarcode(barcode);
        dtProducto.setDescripcion(descripcion);
        Categoria categoria = ManejadorCategoria.getInstance().obtenerCategoria(Long.parseLong(categoriaId));
        dtProducto.setDtCategoria((DtCategoria) categoria.getDataType());

        iControladorProducto.crearProducto(dtProducto, secreto);
        DtProducto dtPosProducto = iControladorProducto.obtenerProductoNombre(nombre, secreto);

        ServicioVerificacion.getInstance().verificar(nombre, dtPosProducto.getNombre());
        ServicioVerificacion.getInstance().verificar(Integer.parseInt(peso), dtPosProducto.getPeso());
        ServicioVerificacion.getInstance().verificar(new BigDecimal(precio), dtPosProducto.getPrecio());
        ServicioVerificacion.getInstance().verificar(barcode, dtPosProducto.getBarcode());
        ServicioVerificacion.getInstance().verificar(descripcion, dtPosProducto.getDescripcion());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(categoriaId), dtPosProducto.getDtCategoria().getId());
        ServicioVerificacion.getInstance().verificarFalse(dtPosProducto.isBorrado());

        iControladorProducto.borrarProducto(dtPosProducto.getId(), secreto);
        dtPosProducto = iControladorProducto.obtenerProductoNombre(nombre, secreto);

        ServicioVerificacion.getInstance().verificar(nombre, dtPosProducto.getNombre());
        ServicioVerificacion.getInstance().verificar(Integer.parseInt(peso), dtPosProducto.getPeso());
        ServicioVerificacion.getInstance().verificar(new BigDecimal(precio), dtPosProducto.getPrecio());
        ServicioVerificacion.getInstance().verificar(barcode, dtPosProducto.getBarcode());
        ServicioVerificacion.getInstance().verificar(descripcion, dtPosProducto.getDescripcion());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(categoriaId), dtPosProducto.getDtCategoria().getId());
        ServicioVerificacion.getInstance().verificarTrue(dtPosProducto.isBorrado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }
}