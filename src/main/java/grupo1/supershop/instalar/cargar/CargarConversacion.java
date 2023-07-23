package grupo1.supershop.instalar.cargar;

import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Conversacion;
import grupo1.supershop.beans.Mensaje;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.fabricas.FabricaSesion;
import grupo1.supershop.interfaces.controladores.IControladorSesion;
import grupo1.supershop.persistencia.manejadores.ManejadorConversacion;
import grupo1.supershop.persistencia.manejadores.ManejadorMensaje;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import grupo1.supershop.persistencia.manejadores.ManejadorUsuario;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioSesion;

public class CargarConversacion {
    private static final String EMAIL_ADMIN = "administrador@supershop.uy";
    private static final String PASSW_ADMIN = "administrador@supershop.uy";

    private final IControladorSesion icSesion;


    public CargarConversacion(){
        icSesion = FabricaSesion.getIControladorSesion();
    }

    public void cargarConversaciones() throws Exception {
        String secreto = iniciarSesion(EMAIL_ADMIN,PASSW_ADMIN).obtenerSecreto(EMAIL_ADMIN,PASSW_ADMIN);
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        
        String[][] datos = {
                {"11", "1", "Mensaje inicial"},
                {"12", "1", "Mensaje inicial"},
                {"13", "1", "Mensaje inicial"}
        };

        for (String[] val : datos) {
            Comprador comprador = ManejadorUsuario.getInstance().obtenerComprador(Long.parseLong(val[0]));
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(Long.parseLong(val[1]));
            String mensajeComprador = val[2];
            Conversacion conversacion = new Conversacion();
            conversacion.setFinalizada(false);
            conversacion.setComprador(comprador);
            conversacion.setSucursal(sucursal);

            ManejadorConversacion.getInstance().crearConversacion(conversacion);
            ServicioActividad.getInstance().registrarCreacion(conversacion, comprador);

            Mensaje mensaje = new Mensaje();
            mensaje.setTexto(mensajeComprador);
            mensaje.setConversacion(conversacion);
            mensaje.setUsuario(comprador);

            ManejadorMensaje.getInstance().crearMensaje(mensaje);
            ServicioActividad.getInstance().registrarCreacion(mensaje, comprador);
        }
        cerrarSesion(secreto);
    }
    private CargarConversacion iniciarSesion(String email, String passw) {
        icSesion.iniciarSesion(email, passw);
        return this;
    }

    private String obtenerSecreto(String email, String passw) {
        return icSesion.obtenerSesion(email, passw).getSecreto();
    }

    private void cerrarSesion(String secreto) {
        icSesion.cerrarSesion(secreto);
    }

}
