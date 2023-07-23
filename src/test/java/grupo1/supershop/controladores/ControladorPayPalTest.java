package grupo1.supershop.controladores;

import grupo1.supershop.config.properties.PropertyReader;
import grupo1.supershop.config.report.listenertest.ListenerTest;
import grupo1.supershop.config.servicios.ServicioIniciarSesion;
import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.fabricas.FabricaCarrito;
import grupo1.supershop.fabricas.FabricaCompra;
import grupo1.supershop.fabricas.FabricaPayPal;
import grupo1.supershop.instalar.InstalarSistema;
import grupo1.supershop.interfaces.controladores.IControladorCarrito;
import grupo1.supershop.interfaces.controladores.IControladorCompra;
import grupo1.supershop.interfaces.controladores.IControladorPayPal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ExtendWith(ListenerTest.class)
class ControladorPayPalTest {
    private static IControladorPayPal iControladorPayPal;
    private static IControladorCompra iControladorCompra;
    private static IControladorCarrito iControladorCarrito;

    @BeforeAll
    static void configuracion() throws Exception {
        new InstalarSistema().instalar();
        iControladorPayPal = FabricaPayPal.getIControladorPayPal();
        iControladorCompra = FabricaCompra.getIControladorCompra();
        iControladorCarrito = FabricaCarrito.getIControladorCarrito();
    }

    @Test
    @DisplayName("Crear orden")
    void crearOrden() {
        String emailComprador = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Email");
        String passwComprador = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Password");
        String secretoComprador = ServicioIniciarSesion.getInstance().iniciarSesion(emailComprador, passwComprador);

        String idSucursal = PropertyReader.getInstance().getTestValue("ControladorPaypal.crearOrden.idSucursal");
        String idProducto = PropertyReader.getInstance().getTestValue("ControladorPaypal.crearOrden.idProducto");
        String cantidad = PropertyReader.getInstance().getTestValue("ControladorPaypal.crearOrden.cantidad");
        String idMetodoEnvio = PropertyReader.getInstance().getTestValue("ControladorPaypal.crearOrden.idMetodoEnvio");
        String status = PropertyReader.getInstance().getTestValue("ControladorPaypal.crearOrden.status");

        iControladorCarrito.crearCarrito(Long.parseLong(idSucursal), Long.parseLong(idProducto), Integer.parseInt(cantidad), secretoComprador);
        iControladorCompra.iniciarCompraDomicilio(secretoComprador);
        Long idCompra = iControladorCompra.obtenerMiCompraProceso(secretoComprador).getId();
        iControladorCompra.aplicarMetodoPagoPayPal(idCompra, secretoComprador);
        iControladorCompra.aplicarMetodoEnvio(idCompra, Long.parseLong(idMetodoEnvio), secretoComprador);
        iControladorCompra.modificarDomicilio(idCompra, 2L, secretoComprador);
        String orden = iControladorPayPal.crearOrden(idCompra, secretoComprador);

        ServicioVerificacion.getInstance().verificar(status, obtenerEstadoOrden(orden));

        ServicioIniciarSesion.getInstance().cerrarSesion(secretoComprador);
    }

    @Test
    @DisplayName("Capturar pago")
    void capturarPago() {
        String emailComprador = PropertyReader.getInstance().getTestValue("ControladorPaypal.capturarPago.email");
        String passwComprador = PropertyReader.getInstance().getTestValue("ControladorPaypal.capturarPago.passw");
        String secretoComprador = ServicioIniciarSesion.getInstance().iniciarSesion(emailComprador, passwComprador);

        String idSucursal = PropertyReader.getInstance().getTestValue("ControladorPaypal.capturarPago.idSucursal");
        String idProducto = PropertyReader.getInstance().getTestValue("ControladorPaypal.capturarPago.idProducto");
        String cantidad = PropertyReader.getInstance().getTestValue("ControladorPaypal.capturarPago.cantidad");
        String idMetodoEnvio = PropertyReader.getInstance().getTestValue("ControladorPaypal.capturarPago.idMetodoEnvio");
        String status = PropertyReader.getInstance().getTestValue("ControladorPaypal.capturarPago.status");

        iControladorCarrito.crearCarrito(Long.parseLong(idSucursal), Long.parseLong(idProducto), Integer.parseInt(cantidad), secretoComprador);
        iControladorCompra.iniciarCompraDomicilio(secretoComprador);
        Long idCompra = iControladorCompra.obtenerMiCompraProceso(secretoComprador).getId();
        iControladorCompra.aplicarMetodoPagoPayPal(idCompra, secretoComprador);
        iControladorCompra.aplicarMetodoEnvio(idCompra, Long.parseLong(idMetodoEnvio), secretoComprador);
        iControladorCompra.modificarDomicilio(idCompra, 2L, secretoComprador);
        iControladorPayPal.crearOrden(idCompra, secretoComprador);
        String orden = iControladorPayPal.capturarPago(idCompra, secretoComprador);

        ServicioVerificacion.getInstance().verificar(status, orden);
    }

    private String obtenerEstadoOrden(String orden) {
        Pattern pattern = Pattern.compile("\"status\":\"(\\w+)\"");
        Matcher matcher = pattern.matcher(orden);

        return matcher.find() ? matcher.group(1) : "NO HAY ESTADO";
    }
}