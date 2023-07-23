package grupo1.supershop.controladores;

import grupo1.supershop.config.properties.PropertyReader;
import grupo1.supershop.config.report.listenertest.ListenerTest;
import grupo1.supershop.config.servicios.ServicioIniciarSesion;
import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtCompra;
import grupo1.supershop.datatypes.DtCompraProducto;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtVale;
import grupo1.supershop.fabricas.FabricaCarrito;
import grupo1.supershop.fabricas.FabricaCompra;
import grupo1.supershop.fabricas.FabricaVale;
import grupo1.supershop.instalar.InstalarSistema;
import grupo1.supershop.interfaces.controladores.IControladorCarrito;
import grupo1.supershop.interfaces.controladores.IControladorCompra;
import grupo1.supershop.interfaces.controladores.IControladorVale;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ListenerTest.class)
class ControladorCompraTest {
    private static IControladorCompra iControladorCompra;
    private static Long idCompraConcluir;
    private static Long idCompraConfirmar;
    private static Long idCompraCancelar;
    private static  Long idCompraDevolver;
    private static  Long idCompraFinalizar;

    @BeforeAll
    static void configuracion() throws Exception {
        new InstalarSistema().instalar();
        iControladorCompra = FabricaCompra.getIControladorCompra();
        IControladorCarrito icCarrito = FabricaCarrito.getIControladorCarrito();

        String emailSucursal = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Password");
        String passwSucursal = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Password");
        String secretoSucursal = ServicioIniciarSesion.getInstance().iniciarSesion(emailSucursal, passwSucursal);

        //Listar Compra
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        icCarrito.crearCarrito(1L, 7L, 1, secreto);
        icCarrito.agregarProducto(8L, 2, secreto);
        icCarrito.agregarProducto(27L, 6, secreto);
        iControladorCompra.iniciarCompraSucursal(secreto);

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);

        //Iniciar compra sucursal
        email = PropertyReader.getInstance().getTestValue("ControladorCompra.iniciarSucursal.email");
        passw = PropertyReader.getInstance().getTestValue("ControladorCompra.iniciarSucursal.password");
        secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        icCarrito.crearCarrito(1L, 7L, 50, secreto);

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);

        //Iniciar compra domicilio
        email = PropertyReader.getInstance().getTestValue("ControladorCompra.iniciarDomicilio.email");
        passw = PropertyReader.getInstance().getTestValue("ControladorCompra.iniciarDomicilio.password");
        secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        icCarrito.crearCarrito(1L, 76L, 20, secreto);

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);

        //Concluir compra
        email = PropertyReader.getInstance().getTestValue("ControladorCompra.concluir.email");
        passw = PropertyReader.getInstance().getTestValue("ControladorCompra.concluir.password");
        secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        icCarrito.crearCarrito(1L, 7L, 1, secreto);
        icCarrito.agregarProducto(8L, 2, secreto);
        icCarrito.agregarProducto(27L, 6, secreto);
        iControladorCompra.iniciarCompraSucursal(secreto);
        idCompraConcluir = iControladorCompra.obtenerMiCompraProceso(secreto).getId();
        iControladorCompra.aplicarMetodoPagoEfectivo(idCompraConcluir, secreto);
        iControladorCompra.aplicarMetodoEnvio(idCompraConcluir, 1L, secreto);
        iControladorCompra.finalizarCompra(idCompraConcluir, secreto);
        iControladorCompra.confirmarCompra(idCompraConcluir,secretoSucursal);

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);

        //Confirmar compra
        email = PropertyReader.getInstance().getTestValue("ControladorCompra.confirmar.email");
        passw = PropertyReader.getInstance().getTestValue("ControladorCompra.confirmar.password");
        secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        icCarrito.crearCarrito(1L, 7L, 1, secreto);
        icCarrito.agregarProducto(8L, 2, secreto);
        icCarrito.agregarProducto(27L, 6, secreto);
        iControladorCompra.iniciarCompraSucursal(secreto);
        idCompraConfirmar = iControladorCompra.obtenerMiCompraProceso(secreto).getId();
        iControladorCompra.aplicarMetodoPagoEfectivo(idCompraConfirmar, secreto);
        iControladorCompra.aplicarMetodoEnvio(idCompraConfirmar, 1L, secreto);
        iControladorCompra.finalizarCompra(idCompraConfirmar,secreto);

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);

        //Cancelar compra
        email = PropertyReader.getInstance().getTestValue("ControladorCompra.cancelar.email");
        passw = PropertyReader.getInstance().getTestValue("ControladorCompra.cancelar.password");
        secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        icCarrito.crearCarrito(1L, 7L, 1, secreto);
        icCarrito.agregarProducto(8L, 2, secreto);
        icCarrito.agregarProducto(27L, 6, secreto);
        iControladorCompra.iniciarCompraSucursal(secreto);
        idCompraCancelar = iControladorCompra.obtenerMiCompraProceso(secreto).getId();

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);

        //Devolver compra
        email = PropertyReader.getInstance().getTestValue("ControladorCompra.devolver.email");
        passw = PropertyReader.getInstance().getTestValue("ControladorCompra.devolver.password");
        secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        icCarrito.crearCarrito(1L, 7L, 1, secreto);
        icCarrito.agregarProducto(8L, 2, secreto);
        icCarrito.agregarProducto(27L, 6, secreto);
        iControladorCompra.iniciarCompraSucursal(secreto);
        idCompraDevolver = iControladorCompra.obtenerMiCompraProceso(secreto).getId();
        iControladorCompra.aplicarMetodoPagoEfectivo(idCompraDevolver, secreto);
        iControladorCompra.aplicarMetodoEnvio(idCompraDevolver, 1L, secreto);
        iControladorCompra.finalizarCompra(idCompraDevolver, secreto);
        iControladorCompra.confirmarCompra(idCompraDevolver,secretoSucursal);
        iControladorCompra.concluirCompra(idCompraDevolver,secretoSucursal);

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);

        //Finalizar compra
        email = PropertyReader.getInstance().getTestValue("ControladorCompra.finalizar.email");
        passw = PropertyReader.getInstance().getTestValue("ControladorCompra.finalizar.password");
        secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        icCarrito.crearCarrito(1L, 7L, 1, secreto);
        icCarrito.agregarProducto(8L, 20, secreto);
        icCarrito.agregarProducto(27L, 10, secreto);
        iControladorCompra.iniciarCompraSucursal(secreto);
        idCompraFinalizar = iControladorCompra.obtenerMiCompraProceso(secreto).getId();
        iControladorCompra.aplicarMetodoPagoEfectivo(idCompraFinalizar, secreto);
        iControladorCompra.aplicarMetodoEnvio(idCompraFinalizar, 1L, secreto);

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Obtener compra")
    void obtenerCompra() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        DtCompra dtCompra = iControladorCompra.obtenerCompra(1L,secreto);

        ServicioVerificacion.getInstance().verificar(12L, dtCompra.getDtComprador().getId());
        ServicioVerificacion.getInstance().verificar(1L, dtCompra.getDtSucursal().getId());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Obtener mi compra productos")
    void listarMiCompraProductos() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        List<DtCompraProducto> dtCompraProductoList = iControladorCompra.listarMiCompraProductos(1L,secreto);

        ServicioVerificacion.getInstance().verificarTrue(0<dtCompraProductoList.size());
    }

    @Test
    @DisplayName("Iniciar compra domicilio")
    void iniciarCompraDomicilio() {
        String email = PropertyReader.getInstance().getTestValue("ControladorCompra.iniciarDomicilio.email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorCompra.iniciarDomicilio.password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        DtMensajeSistema dtMensajeSistema = iControladorCompra.iniciarCompraDomicilio(secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtMensajeSistema.isExitoso());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Iniciar compra sucursal")
    void iniciarCompraSucursal() {
        String email = PropertyReader.getInstance().getTestValue("ControladorCompra.iniciarSucursal.email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorCompra.iniciarSucursal.password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        DtMensajeSistema dtMensajeSistema = iControladorCompra.iniciarCompraSucursal(secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtMensajeSistema.isExitoso());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Cancelar compra")
    void cancelarCompra() {
        String email = PropertyReader.getInstance().getTestValue("ControladorCompra.cancelar.email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorCompra.cancelar.password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        DtMensajeSistema dtMensajeSistema = iControladorCompra.cancelarCompra(idCompraCancelar,secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtMensajeSistema.isExitoso());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Confirmar compra")
    void confirmarCompra() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Password");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        DtMensajeSistema dtMensajeSistema = iControladorCompra.confirmarCompra(idCompraConfirmar,secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtMensajeSistema.isExitoso());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Concluir compra")
    void concluirCompra() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        DtMensajeSistema dtMensajeSistema = iControladorCompra.concluirCompra(idCompraConcluir,secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtMensajeSistema.isExitoso());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Devolver compra")
    void devolverCompra() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        DtMensajeSistema dtMensajeSistema = iControladorCompra.devolverCompra(idCompraDevolver,secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtMensajeSistema.isExitoso());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Finalizar compra")
    void finalizarCompra() {
        String email = PropertyReader.getInstance().getTestValue("ControladorCompra.finalizar.email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorCompra.finalizar.password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        DtMensajeSistema dtMensajeSistema = iControladorCompra.finalizarCompra(idCompraFinalizar,secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtMensajeSistema.isExitoso());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }
}