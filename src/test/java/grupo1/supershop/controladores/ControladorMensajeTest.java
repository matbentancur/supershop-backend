package grupo1.supershop.controladores;

import grupo1.supershop.config.properties.PropertyReader;
import grupo1.supershop.config.report.listenertest.ListenerTest;
import grupo1.supershop.config.servicios.ServicioIniciarSesion;
import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtMensaje;
import grupo1.supershop.fabricas.FabricaConversacion;
import grupo1.supershop.fabricas.FabricaMensaje;
import grupo1.supershop.instalar.InstalarSistema;
import grupo1.supershop.interfaces.controladores.IControladorConversacion;
import grupo1.supershop.interfaces.controladores.IControladorMensaje;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith(ListenerTest.class)
class ControladorMensajeTest {
    private static IControladorMensaje iControladorMensaje;

    @BeforeAll
    static void configuracion() throws Exception {
        iControladorMensaje = FabricaMensaje.getIControladorMensaje();
        new InstalarSistema().instalar();
    }

    @Test
    @DisplayName("Obtener mensaje")
    void obtenerMensaje() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String mensajeId = PropertyReader.getInstance().getTestValue("ControladorMensaje.ObtenerMensaje.mensajeId");
        String mensajeTexto = PropertyReader.getInstance().getTestValue("ControladorMensaje.ObtenerMensaje.mensaje");
        String conversacionId = PropertyReader.getInstance().getTestValue("ControladorMensaje.ObtenerMensaje.ConversacionId");

        DtMensaje dtMensaje = iControladorMensaje.obtenerMensaje(Long.parseLong(mensajeId), secreto);

        ServicioVerificacion.getInstance().verificarFalse(dtMensaje.isBorrado());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(conversacionId), dtMensaje.getDtConversacion().getId());
        ServicioVerificacion.getInstance().verificar(mensajeTexto, dtMensaje.getTexto());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Listar mensaje")
    void listarMensajes() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String conversacionId = PropertyReader.getInstance().getTestValue("ControladorMensaje.listarMensajes.ConversacionId");
        String cantidad = PropertyReader.getInstance().getTestValue("ControladorMensaje.listarMensajes.cantidad");

        List<DtMensaje> dtLista = iControladorMensaje.listarMensajes(Long.parseLong(conversacionId), secreto);
        int dtListaCantidad = dtLista.size();

        ServicioVerificacion.getInstance().verificar(Integer.parseInt(cantidad), dtListaCantidad);

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Enviar mensaje")
    void enviarMensaje() {
        String email = PropertyReader.getInstance().getTestValue("ControladorMensaje.enviarMensaje.Email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorMensaje.enviarMensaje.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String conversacionId = PropertyReader.getInstance().getTestValue("ControladorMensaje.enviarMensaje.ConversacionId");
        String mensajeTexto = PropertyReader.getInstance().getTestValue("ControladorMensaje.enviarMensaje.mensaje");

        DtMensaje dtMensaje = new DtMensaje();
        dtMensaje.setTexto(mensajeTexto);

        iControladorMensaje.enviarMensaje(dtMensaje, Long.parseLong(conversacionId), secreto);
        List<DtMensaje> dtMensajeList = iControladorMensaje.listarMensajes(Long.parseLong(conversacionId), secreto);

        ServicioVerificacion.getInstance().verificar(mensajeTexto, dtMensajeList.get(1).getTexto());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Borrar mensaje")
    void borrarMensaje() {
        String email = PropertyReader.getInstance().getTestValue("ControladorMensaje.borrarMensaje.Email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorMensaje.borrarMensaje.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String mensajeId = PropertyReader.getInstance().getTestValue("ControladorMensaje.borrarMensaje.mensajeId");

        iControladorMensaje.borrarMensaje(Long.parseLong(mensajeId), secreto);

        DtMensaje dtMensaje = iControladorMensaje.obtenerMensaje(Long.parseLong(mensajeId), secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtMensaje.isBorrado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }
}