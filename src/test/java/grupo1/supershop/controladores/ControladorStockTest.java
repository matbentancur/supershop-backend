package grupo1.supershop.controladores;

import grupo1.supershop.config.properties.PropertyReader;
import grupo1.supershop.config.report.listenertest.ListenerTest;
import grupo1.supershop.config.servicios.ServicioIniciarSesion;
import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtStock;
import grupo1.supershop.fabricas.FabricaStock;
import grupo1.supershop.instalar.InstalarSistema;
import grupo1.supershop.interfaces.controladores.IControladorStock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith(ListenerTest.class)
class ControladorStockTest {
    private static IControladorStock iControladorStock;

    @BeforeAll
    static void configuracion() throws Exception {
        iControladorStock = FabricaStock.getIControladorStock();
        new InstalarSistema().instalar();
    }

    @Test
    @DisplayName("Obtener stock")
    void obtenerStock() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String sucursalId = PropertyReader.getInstance().getTestValue("ControladorStock.obtenerStock.sucursalId");
        String productoId = PropertyReader.getInstance().getTestValue("ControladorStock.obtenerStock.productoId");
        String cantidad = PropertyReader.getInstance().getTestValue("ControladorStock.obtenerStock.cantidad");

        DtStock dtStock = iControladorStock.obtenerStock(Long.parseLong(sucursalId), Long.parseLong(productoId), secreto);

        ServicioVerificacion.getInstance().verificar(Long.parseLong(productoId), dtStock.getDtProducto().getId());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(sucursalId), dtStock.getDtSucursal().getId());
        ServicioVerificacion.getInstance().verificar(Integer.parseInt(cantidad), dtStock.getCantidad());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Listar stock sucursal")
    void listarStockSucursal() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String sucursalId = PropertyReader.getInstance().getTestValue("ControladorStock.listarStockSucursal.sucursalId");
        String[] productos = PropertyReader.getInstance().getTestValue("ControladorStock.listarStockSucursal.productos").split(";");
        String cantidad = PropertyReader.getInstance().getTestValue("ControladorStock.listarStockSucursal.cantidad");

        List<DtStock> dtlista = iControladorStock.listarStockSucursal(Long.parseLong(sucursalId), secreto);

        for (int i = 0; i < productos.length; i++) {
            ServicioVerificacion.getInstance().verificar(Long.parseLong(sucursalId), dtlista.get(i).getDtSucursal().getId());
            ServicioVerificacion.getInstance().verificar(productos[i], dtlista.get(i).getDtProducto().getNombre());
            ServicioVerificacion.getInstance().verificar(Integer.parseInt(cantidad), dtlista.get(i).getCantidad());
        }

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Agregar stock")
    void agregarStock() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String sucursalId = PropertyReader.getInstance().getTestValue("ControladorStock.agregarStock.sucursalId");
        String productoId = PropertyReader.getInstance().getTestValue("ControladorStock.agregarStock.productoId");
        String cantidad = PropertyReader.getInstance().getTestValue("ControladorStock.agregarStock.cantidad");
        String cantidadSumar = PropertyReader.getInstance().getTestValue("ControladorStock.agregarStock.cantidadSumar");

        DtStock dtStockAntes = iControladorStock.obtenerStock(Long.parseLong(sucursalId), Long.parseLong(productoId), secreto);

        iControladorStock.agregarStock(Long.parseLong(sucursalId), Long.parseLong(productoId), Integer.parseInt(cantidadSumar), secreto);

        DtStock dtStockDespues = iControladorStock.obtenerStock(Long.parseLong(sucursalId), Long.parseLong(productoId), secreto);

        ServicioVerificacion.getInstance().verificar(Integer.parseInt(cantidad), dtStockAntes.getCantidad());
        ServicioVerificacion.getInstance().verificar(Integer.parseInt(cantidad) + Integer.parseInt(cantidadSumar), dtStockDespues.getCantidad());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Quitar stock")
    void quitarStock() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String sucursalId = PropertyReader.getInstance().getTestValue("ControladorStock.quitarStock.sucursalId");
        String productoId = PropertyReader.getInstance().getTestValue("ControladorStock.quitarStock.productoId");
        String cantidad = PropertyReader.getInstance().getTestValue("ControladorStock.quitarStock.cantidad");
        String cantidadRestar = PropertyReader.getInstance().getTestValue("ControladorStock.quitarStock.cantidadRestar");

        DtStock dtStockAntes = iControladorStock.obtenerStock(Long.parseLong(sucursalId), Long.parseLong(productoId), secreto);

        ServicioVerificacion.getInstance().verificar(Integer.parseInt(cantidad), dtStockAntes.getCantidad());

        iControladorStock.quitarStock(Long.parseLong(sucursalId), Long.parseLong(productoId), Integer.parseInt(cantidadRestar), secreto);

        DtStock dtStockDespues = iControladorStock.obtenerStock(Long.parseLong(sucursalId), Long.parseLong(productoId), secreto);

        ServicioVerificacion.getInstance().verificar(Integer.parseInt(cantidad) - Integer.parseInt(cantidadRestar), dtStockDespues.getCantidad());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);

    }

    @Test
    @DisplayName("Modificar stock")
    void modificarStock() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String sucursalId = PropertyReader.getInstance().getTestValue("ControladorStock.modificarStock.sucursalId");
        String productoId = PropertyReader.getInstance().getTestValue("ControladorStock.modificarStock.productoId");
        String cantidad = PropertyReader.getInstance().getTestValue("ControladorStock.modificarStock.cantidad");
        String cantidadModificar = PropertyReader.getInstance().getTestValue("ControladorStock.modificarStock.cantidadModificar");

        DtStock dtStockAntes = iControladorStock.obtenerStock(Long.parseLong(sucursalId), Long.parseLong(productoId), secreto);

        ServicioVerificacion.getInstance().verificar(Integer.parseInt(cantidad), dtStockAntes.getCantidad());

        iControladorStock.modificarStock(Long.parseLong(sucursalId), Long.parseLong(productoId), Integer.parseInt(cantidadModificar), secreto);

        DtStock dtStockDespues = iControladorStock.obtenerStock(Long.parseLong(sucursalId), Long.parseLong(productoId), secreto);

        ServicioVerificacion.getInstance().verificar(Integer.parseInt(cantidadModificar), dtStockDespues.getCantidad());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }
}