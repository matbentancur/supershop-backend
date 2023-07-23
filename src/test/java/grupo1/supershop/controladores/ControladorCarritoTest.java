package grupo1.supershop.controladores;

import grupo1.supershop.config.properties.PropertyReader;
import grupo1.supershop.config.report.listenertest.ListenerTest;
import grupo1.supershop.config.servicios.ServicioIniciarSesion;
import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtCarrito;
import grupo1.supershop.datatypes.DtCarritoProducto;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtProducto;
import grupo1.supershop.fabricas.FabricaCarrito;
import grupo1.supershop.fabricas.FabricaConversacion;
import grupo1.supershop.fabricas.FabricaProducto;
import grupo1.supershop.instalar.InstalarSistema;
import grupo1.supershop.interfaces.controladores.IControladorCarrito;
import grupo1.supershop.interfaces.controladores.IControladorConversacion;
import grupo1.supershop.interfaces.controladores.IControladorProducto;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ListenerTest.class)
class ControladorCarritoTest {

    private static IControladorCarrito iControladorCarrito;

    private static IControladorProducto iControladorProducto;

    @BeforeAll
    static void configuracion() throws Exception {
        new InstalarSistema().instalar();
        iControladorCarrito = FabricaCarrito.getIControladorCarrito();
        //Para probar total
        iControladorProducto = FabricaProducto.getIControladorProducto();

        //PRUEBA OBTENER  LISTAR
        String email = PropertyReader.getInstance().getTestValue("ControladorCarrito.obtener.email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorCarrito.obtener.password");
        String productoId = PropertyReader.getInstance().getTestValue("ControladorCarrito.obtener.productoId");
        String sucursalId = PropertyReader.getInstance().getTestValue("ControladorCarrito.obtener.sucursalId");
        String cantidad = PropertyReader.getInstance().getTestValue("ControladorCarrito.obtener.cantidad");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        iControladorCarrito.crearCarrito(Long.parseLong(sucursalId),Long.parseLong(productoId),Integer.parseInt(cantidad),secreto);

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);


        //PRUEBA BORRAR
        email = PropertyReader.getInstance().getTestValue("ControladorCarrito.borrar.email");
        passw = PropertyReader.getInstance().getTestValue("ControladorCarrito.borrar.password");
        productoId = PropertyReader.getInstance().getTestValue("ControladorCarrito.borrar.productoId");
        sucursalId = PropertyReader.getInstance().getTestValue("ControladorCarrito.borrar.sucursalId");
        cantidad = PropertyReader.getInstance().getTestValue("ControladorCarrito.borrar.cantidad");
        secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        iControladorCarrito.crearCarrito(Long.parseLong(sucursalId),Long.parseLong(productoId),Integer.parseInt(cantidad),secreto);

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);

        //PRUEBA AGREGAR
        email = PropertyReader.getInstance().getTestValue("ControladorCarrito.agregar.email");
        passw = PropertyReader.getInstance().getTestValue("ControladorCarrito.agregar.password");
        productoId = PropertyReader.getInstance().getTestValue("ControladorCarrito.agregar.productoId");
        sucursalId = PropertyReader.getInstance().getTestValue("ControladorCarrito.agregar.sucursalId");
        cantidad = PropertyReader.getInstance().getTestValue("ControladorCarrito.agregar.cantidad");
        secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        iControladorCarrito.crearCarrito(Long.parseLong(sucursalId),Long.parseLong(productoId),Integer.parseInt(cantidad),secreto);

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);

        //QUITAR
        email = PropertyReader.getInstance().getTestValue("ControladorCarrito.quitar.email");
        passw = PropertyReader.getInstance().getTestValue("ControladorCarrito.quitar.password");
        productoId = PropertyReader.getInstance().getTestValue("ControladorCarrito.quitar.productoId");
        sucursalId = PropertyReader.getInstance().getTestValue("ControladorCarrito.quitar.sucursalId");
        cantidad = PropertyReader.getInstance().getTestValue("ControladorCarrito.quitar.cantidad");
        secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        iControladorCarrito.crearCarrito(Long.parseLong(sucursalId),Long.parseLong(productoId),Integer.parseInt(cantidad),secreto);

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);

        //MODIFICAR
        email = PropertyReader.getInstance().getTestValue("ControladorCarrito.modificar.email");
        passw = PropertyReader.getInstance().getTestValue("ControladorCarrito.modificar.password");
        productoId = PropertyReader.getInstance().getTestValue("ControladorCarrito.modificar.productoId");
        sucursalId = PropertyReader.getInstance().getTestValue("ControladorCarrito.modificar.sucursalId");
        cantidad = PropertyReader.getInstance().getTestValue("ControladorCarrito.modificar.cantidad");
        secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        iControladorCarrito.crearCarrito(Long.parseLong(sucursalId),Long.parseLong(productoId),Integer.parseInt(cantidad),secreto);

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);

        //BORRAR CARRITOPRODUCTO
        email = PropertyReader.getInstance().getTestValue("ControladorCarrito.carritoProducto.email");
        passw = PropertyReader.getInstance().getTestValue("ControladorCarrito.carritoProducto.password");
        productoId = PropertyReader.getInstance().getTestValue("ControladorCarrito.carritoProducto.productoId");
        sucursalId = PropertyReader.getInstance().getTestValue("ControladorCarrito.carritoProducto.sucursalId");
        cantidad = PropertyReader.getInstance().getTestValue("ControladorCarrito.carritoProducto.cantidad");
        String productoId2 = PropertyReader.getInstance().getTestValue("ControladorCarrito.carritoProducto.productoId2");
        secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        iControladorCarrito.crearCarrito(Long.parseLong(sucursalId),Long.parseLong(productoId),Integer.parseInt(cantidad),secreto);

        iControladorCarrito.agregarProducto(Long.parseLong(productoId2),2,secreto);

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);



    }

    @Before

    @Test
    @DisplayName("Obtener carrito")
    void obtenerCarrito() {

        String email = PropertyReader.getInstance().getTestValue("ControladorCarrito.obtener.email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorCarrito.obtener.password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);


        String productoId = PropertyReader.getInstance().getTestValue("ControladorCarrito.obtener.productoId");
        String sucursalId = PropertyReader.getInstance().getTestValue("ControladorCarrito.obtener.sucursalId");
        int cantidad = Integer.parseInt(PropertyReader.getInstance().getTestValue("ControladorCarrito.obtener.cantidad"));

        DtCarrito dtCarrito = iControladorCarrito.obtenerCarrito(secreto);


        DtProducto dtProducto = iControladorProducto.obtenerProducto(Long.parseLong(productoId));


        ServicioVerificacion.getInstance().verificar(Long.parseLong(sucursalId),dtCarrito.getDtSucursal().getId());
        ServicioVerificacion.getInstance().verificar(dtProducto.getPrecio().multiply(BigDecimal.valueOf(cantidad)),dtCarrito.getTotal());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);

    }

    @Test
    @DisplayName("Listar carrito producto")
    void listarCarritoProductos() {
        String email = PropertyReader.getInstance().getTestValue("ControladorCarrito.obtener.email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorCarrito.obtener.password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        List<DtCarritoProducto> dtCarritoProductoList = iControladorCarrito.listarCarritoProductos(secreto);

        ServicioVerificacion.getInstance().verificarTrue(0<dtCarritoProductoList.size());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);

    }

    @Test
    @DisplayName("Crear carrito")
    void crearCarrito() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String productoId = PropertyReader.getInstance().getTestValue("ControladorCarrito.obtener.productoId");
        String sucursalId = PropertyReader.getInstance().getTestValue("ControladorCarrito.obtener.sucursalId");
        int cantidad = Integer.parseInt(PropertyReader.getInstance().getTestValue("ControladorCarrito.obtener.cantidad"));

        DtMensajeSistema dtMensajeSistema = iControladorCarrito.crearCarrito(Long.parseLong(sucursalId),Long.parseLong(productoId),cantidad,secreto);

        DtCarrito dtCarrito = iControladorCarrito.obtenerCarrito(secreto);
        DtProducto dtProducto = iControladorProducto.obtenerProducto(Long.parseLong(productoId));


        ServicioVerificacion.getInstance().verificarTrue(dtMensajeSistema.isExitoso());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(sucursalId),dtCarrito.getDtSucursal().getId());
        ServicioVerificacion.getInstance().verificar(dtProducto.getPrecio().multiply(BigDecimal.valueOf(cantidad)),dtCarrito.getTotal());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);

    }

    @Test
    @DisplayName("Borrar carrito")
    void borrarCarrito() {
        String email = PropertyReader.getInstance().getTestValue("ControladorCarrito.borrar.email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorCarrito.borrar.password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        DtCarrito dtCarrito = iControladorCarrito.obtenerCarrito(secreto);

        DtMensajeSistema dtMensajeSistema = iControladorCarrito.borrarCarrito(secreto);

        DtCarrito dtCarritoBorrado = iControladorCarrito.obtenerCarrito(secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtMensajeSistema.isExitoso());
        ServicioVerificacion.getInstance().verificarTrue(dtCarrito != null);
        ServicioVerificacion.getInstance().verificarTrue(dtCarritoBorrado == null);

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Agregar producto")
    void agregarProducto() {
        String email = PropertyReader.getInstance().getTestValue("ControladorCarrito.agregar.email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorCarrito.agregar.password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        List<DtCarritoProducto> dtCarritoProductoList = iControladorCarrito.listarCarritoProductos(secreto);

        String productoIdNuevo = PropertyReader.getInstance().getTestValue("ControladorCarrito.agregar.nuevoProducto");

        DtMensajeSistema dtMensajeSistema = iControladorCarrito.agregarProducto(Long.parseLong(productoIdNuevo),1,secreto);

        List<DtCarritoProducto> dtCarritoProductoNuevo = iControladorCarrito.listarCarritoProductos(secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtMensajeSistema.isExitoso());
        ServicioVerificacion.getInstance().verificarTrue(dtCarritoProductoList.size()+1 == dtCarritoProductoNuevo.size());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Quitar producto")
    void quitarProducto() {
        String email = PropertyReader.getInstance().getTestValue("ControladorCarrito.quitar.email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorCarrito.quitar.password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);
        String productoId = PropertyReader.getInstance().getTestValue("ControladorCarrito.quitar.productoId");

        DtMensajeSistema dtMensajeSistema = iControladorCarrito.quitarProducto(Long.parseLong(productoId),1,secreto);

        DtCarrito dtCarrito = iControladorCarrito.obtenerCarrito(secreto);
        DtProducto dtProducto = iControladorProducto.obtenerProducto(Long.parseLong(productoId));

        ServicioVerificacion.getInstance().verificarTrue(dtMensajeSistema.isExitoso());
        ServicioVerificacion.getInstance().verificar(dtProducto.getPrecio(),dtCarrito.getTotal());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Modificar cantidad producto")
    void modificarCantidadProducto() {
        String email = PropertyReader.getInstance().getTestValue("ControladorCarrito.modificar.email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorCarrito.modificar.password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);
        String productoId = PropertyReader.getInstance().getTestValue("ControladorCarrito.modificar.productoId");
        int cantidad = 2;


        DtMensajeSistema dtMensajeSistema = iControladorCarrito.modificarCantidadProducto(Long.parseLong(productoId),cantidad,secreto);

        DtProducto dtProducto = iControladorProducto.obtenerProducto(Long.parseLong(productoId));
        DtCarrito dtCarritoModificado = iControladorCarrito.obtenerCarrito(secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtMensajeSistema.isExitoso());
        ServicioVerificacion.getInstance().verificar(dtProducto.getPrecio().multiply(BigDecimal.valueOf(cantidad)),dtCarritoModificado.getTotal());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);

    }

    @Test
    @DisplayName("Borrar carrito producto")
    void borrarCarritoProducto() {
        String email = PropertyReader.getInstance().getTestValue("ControladorCarrito.carritoProducto.email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorCarrito.carritoProducto.password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String productoId = PropertyReader.getInstance().getTestValue("ControladorCarrito.carritoProducto.productoId");

        List<DtCarritoProducto> dtCarritoProductoAntes = iControladorCarrito.listarCarritoProductos(secreto);

        DtMensajeSistema dtMensajeSistema = iControladorCarrito.borrarCarritoProducto(Long.parseLong(productoId),secreto);

        List<DtCarritoProducto> dtCarritoProductoDespues = iControladorCarrito.listarCarritoProductos(secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtMensajeSistema.isExitoso());
        ServicioVerificacion.getInstance().verificarTrue(dtCarritoProductoAntes.size() - 1 == dtCarritoProductoDespues.size());

    }
}