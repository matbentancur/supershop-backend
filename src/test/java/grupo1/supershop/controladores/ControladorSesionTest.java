package grupo1.supershop.controladores;

import grupo1.supershop.config.properties.PropertyReader;
import grupo1.supershop.config.report.listenertest.ListenerTest;
import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtSesion;
import grupo1.supershop.fabricas.FabricaSesion;
import grupo1.supershop.instalar.InstalarSistema;
import grupo1.supershop.interfaces.controladores.IControladorSesion;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ListenerTest.class)
class ControladorSesionTest {
    private static IControladorSesion iControladorSesion;

    @BeforeAll
    static void configuracion() throws Exception {
        iControladorSesion = FabricaSesion.getIControladorSesion();
        new InstalarSistema().instalar();
    }

    @Test
    @DisplayName("Obtener sesion")
    void obtenerSesion() {
        String email = PropertyReader.getInstance().getTestValue("ControladorSesion.obtenerSesion.Email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorSesion.obtenerSesion.Passw");
        String id = PropertyReader.getInstance().getTestValue("ControladorSesion.obtenerSesion.id");
        String nombres = PropertyReader.getInstance().getTestValue("ControladorSesion.obtenerSesion.nombres");
        String apellidos = PropertyReader.getInstance().getTestValue("ControladorSesion.obtenerSesion.apellidos");
        String celular = PropertyReader.getInstance().getTestValue("ControladorSesion.obtenerSesion.celular");
        String nacimiento = PropertyReader.getInstance().getTestValue("ControladorSesion.obtenerSesion.nacimiento");

        iControladorSesion.iniciarSesion(email, passw);
        DtSesion dtSesion = iControladorSesion.obtenerSesion(email, email);

        ServicioVerificacion.getInstance().verificar(Long.parseLong(id), dtSesion.getDtUsuario().getId());
        ServicioVerificacion.getInstance().verificar(nombres, dtSesion.getDtUsuario().getNombres());
        ServicioVerificacion.getInstance().verificar(apellidos, dtSesion.getDtUsuario().getApellidos());
        ServicioVerificacion.getInstance().verificar(email, dtSesion.getDtUsuario().getEmail());
        ServicioVerificacion.getInstance().verificar(null, dtSesion.getDtUsuario().getPassword());
        ServicioVerificacion.getInstance().verificar(celular, dtSesion.getDtUsuario().getCelular());
        ServicioVerificacion.getInstance().verificar(nacimiento, dtSesion.getDtUsuario().getNacimiento().toString());
    }

    @Test
    @DisplayName("Iniciar sesion")
    void iniciarSesion() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Email");
        String password = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Password");

        iControladorSesion.iniciarSesion(email, password);

        DtSesion dtSesion = iControladorSesion.obtenerSesion(email, password);

        ServicioVerificacion.getInstance().verificar(email, dtSesion.getDtUsuario().getEmail());
    }

    @Test
    @DisplayName("Cerrar sesion")
    void cerrarSesion() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Email");
        String password = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Password");

        iControladorSesion.iniciarSesion(email, password);
        String secreto = iControladorSesion.obtenerSesion(email, password).getSecreto();

        iControladorSesion.cerrarSesion(secreto);

        DtSesion dtSesion = iControladorSesion.obtenerSesion(email, password);

        ServicioVerificacion.getInstance().verificar(null, dtSesion);
    }
}