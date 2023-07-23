package grupo1.supershop.servicios;

import java.util.ResourceBundle;

public class ServicioProperties {

    private static ServicioProperties instance = null;
    private ResourceBundle mensajesProperties = ResourceBundle.getBundle("mensajes");
    private ResourceBundle aplicacionProperties = ResourceBundle.getBundle("application");

    private ServicioProperties() {
    }

    public static ServicioProperties getInstance() {
        if (instance == null) {
            instance = new ServicioProperties();
        }
        return instance;
    }

    private ResourceBundle getMensajesProperties() {
        return mensajesProperties;
    }

    private ResourceBundle getAplicacionProperties() {
        return aplicacionProperties;
    }

    public String getMensajeProperties(String mensaje) {
        return this.getMensajesProperties().getString(mensaje);
    }

    public String getAppProperties(String mensaje) {
        return this.getAplicacionProperties().getString(mensaje);
    }

}
