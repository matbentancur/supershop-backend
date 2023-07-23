package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtMensajeSistema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@DisplayName("DtMensajeSistemaTest")
class DtMensajeSistemaTest extends ConfiguracionTest {

    private boolean exitoso;
    private boolean erroneo;
    private boolean alerta;
    private boolean informacion;
    private String tipo;
    private String mensaje;
    private List<String> listaMensajes;

    @BeforeEach
    public void configurar() {
        exitoso = true;
        erroneo = false;
        alerta = true;
        informacion = false;
        tipo = "Tipo de mensaje";
        mensaje = "Mensaje";
        listaMensajes = new ArrayList<>();
        listaMensajes.add("Mensaje 1");
        listaMensajes.add("Mensaje 2");
    }

    @DisplayName("Crear DtMensajeSistema - Getter/Setter")
    @Test
    void crear1() {
        dtMensajeSistema.setExitoso(exitoso);
        dtMensajeSistema.setErroneo(erroneo);
        dtMensajeSistema.setAlerta(alerta);
        dtMensajeSistema.setInformacion(informacion);
        dtMensajeSistema.setTipo(tipo);
        dtMensajeSistema.setMensajes(listaMensajes);

        ServicioVerificacion.getInstance().verificar(exitoso, dtMensajeSistema.isExitoso());
        ServicioVerificacion.getInstance().verificar(erroneo, dtMensajeSistema.isErroneo());
        ServicioVerificacion.getInstance().verificar(alerta, dtMensajeSistema.isAlerta());
        ServicioVerificacion.getInstance().verificar(informacion, dtMensajeSistema.isInformacion());
        ServicioVerificacion.getInstance().verificar(tipo, dtMensajeSistema.getTipo());
        ServicioVerificacion.getInstance().verificar(listaMensajes, dtMensajeSistema.getMensajes());

    }

    @DisplayName("Crear DtMensajeSistema(exitoso, erroneo, alerta, informacion, tipo)")
    @Test
    void crear2() {
        dtMensajeSistema = new DtMensajeSistema(!exitoso, !erroneo, !alerta, !informacion, tipo);

        ServicioVerificacion.getInstance().verificar(!exitoso, dtMensajeSistema.isExitoso());
        ServicioVerificacion.getInstance().verificar(!erroneo, dtMensajeSistema.isErroneo());
        ServicioVerificacion.getInstance().verificar(!alerta, dtMensajeSistema.isAlerta());
        ServicioVerificacion.getInstance().verificar(!informacion, dtMensajeSistema.isInformacion());
        ServicioVerificacion.getInstance().verificar(tipo, dtMensajeSistema.getTipo());
    }

    @DisplayName("Crear DtMensajeSistema(exitoso, erroneo, alerta, informacion, tipo, mensaje)")
    @Test
    void crear3() {
        dtMensajeSistema = new DtMensajeSistema(!exitoso, erroneo, alerta, !informacion, tipo, mensaje);

        ServicioVerificacion.getInstance().verificar(!exitoso, dtMensajeSistema.isExitoso());
        ServicioVerificacion.getInstance().verificar(erroneo, dtMensajeSistema.isErroneo());
        ServicioVerificacion.getInstance().verificar(alerta, dtMensajeSistema.isAlerta());
        ServicioVerificacion.getInstance().verificar(!informacion, dtMensajeSistema.isInformacion());
        ServicioVerificacion.getInstance().verificar(tipo, dtMensajeSistema.getTipo());
        listaMensajes.clear();
        listaMensajes.add(mensaje);
        ServicioVerificacion.getInstance().verificar(listaMensajes, dtMensajeSistema.getMensajes());
    }

    @DisplayName("Crear DtMensajeSistema(exitoso, erroneo, alerta, informacion, tipo, listaMensaje)")
    @Test
    void crear4() {
        dtMensajeSistema = new DtMensajeSistema(!exitoso, erroneo, alerta, informacion, tipo, listaMensajes);

        ServicioVerificacion.getInstance().verificar(!exitoso, dtMensajeSistema.isExitoso());
        ServicioVerificacion.getInstance().verificar(erroneo, dtMensajeSistema.isErroneo());
        ServicioVerificacion.getInstance().verificar(alerta, dtMensajeSistema.isAlerta());
        ServicioVerificacion.getInstance().verificar(informacion, dtMensajeSistema.isInformacion());
        ServicioVerificacion.getInstance().verificar(tipo, dtMensajeSistema.getTipo());
        ServicioVerificacion.getInstance().verificar(listaMensajes, dtMensajeSistema.getMensajes());
    }
}