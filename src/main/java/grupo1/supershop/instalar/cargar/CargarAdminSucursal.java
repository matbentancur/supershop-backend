package grupo1.supershop.instalar.cargar;

import grupo1.supershop.beans.AdministradorSucursal;
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


public class CargarAdminSucursal {
    private static final String EMAIL_ADMIN = "administrador@supershop.uy";
    private static final String PASSW_ADMIN = "administrador@supershop.uy";
    
    private final IControladorSesion icSesion;
    
    public CargarAdminSucursal(){
        icSesion = FabricaSesion.getIControladorSesion();
    }
    
    public void cargarUsuarios() throws Exception {
        String secreto = iniciarSesion(EMAIL_ADMIN,PASSW_ADMIN).obtenerSecreto(EMAIL_ADMIN,PASSW_ADMIN);
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        
        String[][] datos = {
                {"adminsucursal@supershop.uy", "adminsucursal@supershop.uy", "Admin Sucursal", "Admin Sucursal", "00000001"},
                {"adminsucursal2@supershop.uy", "adminsucursal2@supershop.uy", "Admin Sucursal 2", "Admin Sucursal 2", "00000002"},
                {"adminsucursal3@supershop.uy", "adminsucursal3@supershop.uy", "Admin Sucursal 3", "Admin Sucursal 3", "00000003"},
                {"adminsucursal4@supershop.uy", "adminsucursal4@supershop.uy", "Admin Sucursal 4", "Admin Sucursal 4", "00000004"},
        };
        long idSucursal = 1L;

        for (String[] val : datos) {
            AdministradorSucursal administradorSucursal = new AdministradorSucursal();
            administradorSucursal.setBloqueado(false);
            administradorSucursal.setEmail(val[0]);
            administradorSucursal.setPassword(val[1]);
            administradorSucursal.cifrarPassword();
            administradorSucursal.setNombres(val[2]);
            administradorSucursal.setApellidos(val[3]);
            administradorSucursal.setCelular(val[4]);
            administradorSucursal.setNacimiento(new Date());

            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(idSucursal++);
            administradorSucursal.setSucursal(sucursal);

            ManejadorUsuario.getInstance().crearUsuario(administradorSucursal);
            ServicioActividad.getInstance().registrarCreacion(administradorSucursal, usuario);

            
        }
        
        cerrarSesion(secreto);
    }
    
    private CargarAdminSucursal iniciarSesion(String email, String passw) {
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
