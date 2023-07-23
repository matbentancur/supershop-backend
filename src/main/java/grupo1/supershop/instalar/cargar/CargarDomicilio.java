package grupo1.supershop.instalar.cargar;

import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Domicilio;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.fabricas.FabricaSesion;
import grupo1.supershop.interfaces.controladores.IControladorSesion;
import grupo1.supershop.persistencia.manejadores.ManejadorDomicilio;
import grupo1.supershop.persistencia.manejadores.ManejadorUsuario;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioSesion;

import java.math.BigDecimal;

public class CargarDomicilio {
    private static final String EMAIL_ADMIN = "administrador@supershop.uy";
    private static final String PASSW_ADMIN = "administrador@supershop.uy";

    private final IControladorSesion icSesion;


    public CargarDomicilio(){
        icSesion = FabricaSesion.getIControladorSesion();
    }
    
    public void cargarDomicilios() throws Exception {
        String secreto = iniciarSesion(EMAIL_ADMIN,PASSW_ADMIN).obtenerSecreto(EMAIL_ADMIN,PASSW_ADMIN);
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        
        long idComprador = 11;
        String[][] datos = {
                {"001", "Brazo Oriental", "Casa", "Dirección de casa.", "Homero de Gregorio", "3527", "11700", "1", "Andres lamas", "Montevdieo", "Referencia"},
                {"002", "Manga", "Casa", "Dirección para casa.", "Av. Jose Belloni", "2356", "12345", "2", "C. Prevision", "Montevideo", "Referencia"},
                {"003", "Centro", "Casa", "Es la dirección de casa.", "Av. 18 de Julio", "7543", "54321", "3", "Arenal Grande", "Montevideo", "Referencia"},
                {"004", "Buceo", "Casa", "Es la dirección para casa.", "Av. Gral. Rivera", "1289", "67890", "4", "Pedro Bustamante", "Montevideo", "Referencia"},
                {"005", "Piedras Blancas", "Casa", "Mi dirección de casa.", "Rafael", "4542", "09876", "5", "San Cono", "Montevideo", "Referencia"},
                {"006", "Palermo", "Casa", "Mi dirección.", "Durazno", "3861", "10293", "6", "Paraguay", "Montevideo", "Referencia"}
        };

        for (String[] val : datos) {
            Domicilio domicilio = new Domicilio();
            domicilio.setApartamento(val[0]);
            domicilio.setBarrio(val[1]);
            domicilio.setBorrado(false);
            domicilio.setNombre(val[2]);
            domicilio.setDescripcion(val[3]);
            domicilio.setLongitud(BigDecimal.valueOf(100));
            domicilio.setLatitud(BigDecimal.valueOf(50));
            domicilio.setCalle(val[4]);
            domicilio.setNumeracion(val[5]);
            domicilio.setCodigoPostal(Integer.parseInt(val[6]));
            domicilio.setBlock(val[7]);
            domicilio.setEsquina(val[8]);
            domicilio.setDepartamento(val[9]);
            domicilio.setReferencias(val[10]);

            Comprador comprador = ManejadorUsuario.getInstance().obtenerComprador(idComprador++);
            domicilio.setComprador(comprador);

            ManejadorDomicilio.getInstance().crearDomicilio(domicilio);
            ServicioActividad.getInstance().registrarCreacion(domicilio, usuario);
        }
        cerrarSesion(secreto);
    }
    private CargarDomicilio iniciarSesion(String email, String passw) {
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