package grupo1.supershop.servicios;

import grupo1.supershop.datatypes.DtMensajeSistema;
import java.util.List;

public class ServicioMensajeSistema {

    private static ServicioMensajeSistema instance = null;

    private final String MENSAJE_TIPO_OK = "OK";
    private final String MENSAJE_TIPO_ERROR = "ERROR";
    private final String MENSAJE_TIPO_ALERT = "ALERT";
    private final String MENSAJE_TIPO_INFO = "INFO";

    private ServicioMensajeSistema() {
    }

    public static ServicioMensajeSistema getInstance() {
        if (instance == null) {
            instance = new ServicioMensajeSistema();
        }
        return instance;
    }

    public String getMensajeProperties(String mensaje) {
        return ServicioProperties.getInstance().getMensajeProperties(mensaje);
    }

    public DtMensajeSistema getPropertiesMensajeSistemaOK(String texto) {
        String mensaje = this.getMensajeProperties(texto);
        if (mensaje != null) {
            return this.getMensajeSistemaOK(mensaje);
        } else {
            return this.getMensajeSistemaOK("No se pudo recuperar el mensaje");
        }
    }

    public DtMensajeSistema getMensajeSistemaOK() {
        return new DtMensajeSistema(true, false, false, false, MENSAJE_TIPO_OK);
    }

    public DtMensajeSistema getMensajeSistemaOK(String mensaje) {
        return new DtMensajeSistema(true, false, false, false, MENSAJE_TIPO_OK, mensaje);
    }

    public DtMensajeSistema getMensajeSistemaOK(List<String> mensajes) {
        return new DtMensajeSistema(true, false, false, false, MENSAJE_TIPO_OK, mensajes);
    }

    public DtMensajeSistema getPropertiesMensajeSistemaERROR(String texto) {
        String mensaje = this.getMensajeProperties(texto);
        if (mensaje != null) {
            return this.getMensajeSistemaERROR(mensaje);
        } else {
            return this.getMensajeSistemaERROR("No se pudo recuperar el mensaje");
        }
    }

    public DtMensajeSistema getMensajeSistemaERROR() {
        return new DtMensajeSistema(false, true, false, false, MENSAJE_TIPO_ERROR);
    }

    public DtMensajeSistema getMensajeSistemaERROR(String mensaje) {
        return new DtMensajeSistema(false, true, false, false, MENSAJE_TIPO_ERROR, mensaje);
    }

    public DtMensajeSistema getMensajeSistemaERROR(List<String> mensajes) {
        return new DtMensajeSistema(false, true, false, false, MENSAJE_TIPO_ERROR, mensajes);
    }

    public DtMensajeSistema getPropertiesMensajeSistemaALERT(String texto) {
        String mensaje = this.getMensajeProperties(texto);
        if (mensaje != null) {
            return this.getMensajeSistemaALERT(mensaje);
        } else {
            return this.getMensajeSistemaALERT("No se pudo recuperar el mensaje");
        }
    }

    public DtMensajeSistema getMensajeSistemaALERT() {
        return new DtMensajeSistema(false, false, true, false, MENSAJE_TIPO_ALERT);
    }

    public DtMensajeSistema getMensajeSistemaALERT(String mensaje) {
        return new DtMensajeSistema(false, false, true, false, MENSAJE_TIPO_ALERT, mensaje);
    }

    public DtMensajeSistema getMensajeSistemaALERT(List<String> mensajes) {
        return new DtMensajeSistema(false, false, true, false, MENSAJE_TIPO_ALERT, mensajes);
    }

    public DtMensajeSistema getPropertiesMensajeSistemaINFO(String texto) {
        String mensaje = this.getMensajeProperties(texto);
        if (mensaje != null) {
            return this.getMensajeSistemaINFO(mensaje);
        } else {
            return this.getMensajeSistemaINFO("No se pudo recuperar el mensaje");
        }
    }

    public DtMensajeSistema getMensajeSistemaINFO() {
        return new DtMensajeSistema(false, false, false, true, MENSAJE_TIPO_INFO);
    }

    public DtMensajeSistema getMensajeSistemaINFO(String mensaje) {
        return new DtMensajeSistema(false, false, false, true, MENSAJE_TIPO_INFO, mensaje);
    }

    public DtMensajeSistema getMensajeSistemaINFO(List<String> mensajes) {
        return new DtMensajeSistema(false, false, false, true, MENSAJE_TIPO_INFO, mensajes);
    }

}
