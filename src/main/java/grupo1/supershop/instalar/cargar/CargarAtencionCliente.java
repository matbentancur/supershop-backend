package grupo1.supershop.instalar.cargar;

import grupo1.supershop.beans.AtencionCliente;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.fabricas.FabricaSesion;
import grupo1.supershop.interfaces.controladores.IControladorSesion;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import grupo1.supershop.persistencia.manejadores.ManejadorUsuario;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioSesion;
import lombok.NoArgsConstructor;

import java.util.Date;

public class CargarAtencionCliente {
    private static final String EMAIL_ADMIN = "administrador@supershop.uy";
    private static final String PASSW_ADMIN = "administrador@supershop.uy";

    private final IControladorSesion icSesion;
    
    public CargarAtencionCliente(){
        icSesion = FabricaSesion.getIControladorSesion();
    }
    
    public void cargarUsuarios() throws Exception {
        String secreto = iniciarSesion(EMAIL_ADMIN,PASSW_ADMIN).obtenerSecreto(EMAIL_ADMIN,PASSW_ADMIN);
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        
        String[][] datos = {
                {"atencioncliente@supershop.uy", "atencioncliente@supershop.uy", "Atencion Cliente", "Atencion Cliente", "00000001"},
                {"atencioncliente2@supershop.uy", "atencioncliente2@supershop.uy", "Atencion Cliente 2", "Atencion Cliente 2", "00000002"},
                {"atencioncliente3@supershop.uy", "atencioncliente3@supershop.uy", "Atencion Cliente 3", "Atencion Cliente 3", "00000003"},
                {"atencioncliente4@supershop.uy", "atencioncliente4@supershop.uy", "Atencion Cliente 4", "Atencion Cliente 4", "00000004"},
        };
        long idSucursal = 1L;

        for (String[] val : datos) {
            AtencionCliente atencionCliente = new AtencionCliente();
            atencionCliente.setBloqueado(false);
            atencionCliente.setEmail(val[0]);
            atencionCliente.setPassword(val[1]);
            atencionCliente.cifrarPassword();
            atencionCliente.setNombres(val[2]);
            atencionCliente.setApellidos(val[3]);
            atencionCliente.setCelular(val[4]);
            atencionCliente.setNacimiento(new Date());

            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(idSucursal++);
            atencionCliente.setSucursal(sucursal);

            ManejadorUsuario.getInstance().crearUsuario(atencionCliente);
            ServicioActividad.getInstance().registrarCreacion(atencionCliente, usuario);
        }
        cerrarSesion(secreto);
    }
    
    private CargarAtencionCliente iniciarSesion(String email, String passw) {
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
