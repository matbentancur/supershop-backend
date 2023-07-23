package grupo1.supershop.controladores;

import grupo1.supershop.config.properties.PropertyReader;
import grupo1.supershop.config.report.listenertest.ListenerTest;
import grupo1.supershop.config.servicios.ServicioIniciarSesion;
import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtConversacion;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtVale;
import grupo1.supershop.fabricas.FabricaConversacion;
import grupo1.supershop.fabricas.FabricaPromocion;
import grupo1.supershop.instalar.InstalarSistema;
import grupo1.supershop.interfaces.controladores.IControladorConversacion;
import grupo1.supershop.interfaces.controladores.IControladorPromocion;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ListenerTest.class)
class ControladorConversacionTest {

    private static IControladorConversacion iControladorConversacion;

    @BeforeAll
    static void configuracion() throws Exception {
        new InstalarSistema().instalar();
        iControladorConversacion = FabricaConversacion.getIControladorConversacion();
    }

    @Test
    @DisplayName("Obtener conversacion")
    void obtenerConversacion() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String conversacionId = PropertyReader.getInstance().getTestValue("ControladorConversacion.obtener.id");
        String compradorId = PropertyReader.getInstance().getTestValue("ControladorConversacion.obtener.compradorId");
        String sucursalId = PropertyReader.getInstance().getTestValue("ControladorConversacion.obtener.sucursalId");
        String mensaje = PropertyReader.getInstance().getTestValue("ControladorConversacion.obtener.mensaje");

        DtConversacion dtConversacion = iControladorConversacion.obtenerConversacion(Long.parseLong(conversacionId),secreto);

        ServicioVerificacion.getInstance().verificar(Long.parseLong(conversacionId), dtConversacion.getId());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(sucursalId), dtConversacion.getDtSucursal().getId());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(compradorId), dtConversacion.getDtComprador().getId());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Listar conversaciones comprador")
    void listarConversacionesComprador() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        List<DtConversacion> dtConversacionList = iControladorConversacion.listarConversacionesComprador(secreto);

        ServicioVerificacion.getInstance().verificarTrue(0< dtConversacionList.size());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Crear conversacion")
    void crearConversacion() {
        String email = PropertyReader.getInstance().getTestValue("ControladorConversacion.crear.Email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorConversacion.crear.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String sucursalId = PropertyReader.getInstance().getTestValue("ControladorConversacion.obtener.sucursalId");

        DtMensajeSistema dtMensajeSistema = iControladorConversacion.crearConversacion(Long.parseLong(sucursalId),secreto);

        List<DtConversacion> dtConversacionList = iControladorConversacion.listarConversacionesComprador(secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtMensajeSistema.isExitoso());
        ServicioVerificacion.getInstance().verificarTrue(0< dtConversacionList.size());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);

    }

    @Test
    @DisplayName("Finalizar conversacion")
    void finalizarConversacion() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String conversacionId = PropertyReader.getInstance().getTestValue("ControladorConversacion.finalizar.id");

        DtMensajeSistema dtMensajeSistema = iControladorConversacion.finalizarConversacion(Long.parseLong(conversacionId),secreto);

        DtConversacion dtConversacion = iControladorConversacion.obtenerConversacion(Long.parseLong(conversacionId),secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtMensajeSistema.isExitoso());
        ServicioVerificacion.getInstance().verificar(true,dtConversacion.isFinalizada());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }
}