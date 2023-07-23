package grupo1.supershop.instalar.cargar;

import grupo1.supershop.beans.MetodoEnvio;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.fabricas.FabricaSesion;
import grupo1.supershop.interfaces.controladores.IControladorSesion;
import grupo1.supershop.persistencia.manejadores.ManejadorBase;
import grupo1.supershop.persistencia.manejadores.ManejadorMetodoEnvio;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioSesion;

import java.math.BigDecimal;

public class CargarMetodoEnvio {
    private static final String EMAIL_ADMIN = "administrador@supershop.uy";
    private static final String PASSW_ADMIN = "administrador@supershop.uy";

    private final IControladorSesion icSesion;


    public CargarMetodoEnvio(){
        icSesion = FabricaSesion.getIControladorSesion();
    }
    
    public void cargarMetodosDeEnvios() throws Exception {
        String secreto = iniciarSesion(EMAIL_ADMIN,PASSW_ADMIN).obtenerSecreto(EMAIL_ADMIN,PASSW_ADMIN);
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        
        String[][] datos = {
                {"Envío de la empresa", "Envío de la empresa.", "20000", "100", "100"},
                {"DAC", "Empresa Agencia Central.", "30000", "200","200"},
                {"UES", "Empresa UES", "30000", "200","200"},
                {"MIRTRANS", "Empresa MIRTRANS", "30000", "200","200"}
        };

        for (String[] val : datos) {
            MetodoEnvio metodoEnvio = new MetodoEnvio();
            metodoEnvio.setBorrado(false);
            metodoEnvio.setNombre(val[0]);
            metodoEnvio.setDescripcion(val[1]);
            metodoEnvio.setPesoMaximo(Integer.parseInt(val[2]));
            metodoEnvio.setCantidadProductosMaximo(Integer.parseInt(val[3]));
            metodoEnvio.setCosto(BigDecimal.valueOf(Long.parseLong(val[4])));

            ManejadorMetodoEnvio.getInstance().crearMetodoEnvio(metodoEnvio);
            ServicioActividad.getInstance().registrarCreacion(metodoEnvio, usuario);
        }
        cerrarSesion(secreto);
    }
    
    private CargarMetodoEnvio iniciarSesion(String email, String passw) {
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
