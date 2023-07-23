package grupo1.supershop.instalar.cargar;

import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.fabricas.FabricaSesion;
import grupo1.supershop.interfaces.controladores.IControladorSesion;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioSesion;


public class CargarSucursal {
    private static final String EMAIL_ADMIN = "administrador@supershop.uy";
    private static final String PASSW_ADMIN = "administrador@supershop.uy";

    private final IControladorSesion icSesion;


    public CargarSucursal() {
        icSesion = FabricaSesion.getIControladorSesion();
    }

    public void cargarSucursales() throws Exception {
        String secreto = iniciarSesion(EMAIL_ADMIN, PASSW_ADMIN).obtenerSecreto(EMAIL_ADMIN, PASSW_ADMIN);
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);

        String[][] datos = {
                {"Casa Central", "casacentral@supershop.uy", "L a V de 10:00 a 22:00", "0011 1524", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1688564321/sucursal1_fa7p22.jpg"},
                {"Ciudad de la costa", "ciudaddelacosta@supershop.uy", "L a V de 08:00 a 20:00", "0101 1524", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1688564148/sucursal3_vif95f.jpg"},
                {"Piriapolis", "piriapolis@supershop.uy", "L a V de 08:00 a 20:00", "0202 1524", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1688564545/suscursalpiria_ud5ysk.jpg"},
                {"Colonia", "colonia@supershop.uy", "L a V de 10:00 a 20:00", "0303 1524", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1688564789/sucursal4_g4nyyg.jpg"},
        };

        for (String[] val : datos) {
            String nombre = val[0];
            String email = val[1];
            String horario = val[2];
            String telefono = val[3];
            String imagen = val[4];
            Sucursal sucursal = new Sucursal();
            sucursal.setNombre(nombre);
            sucursal.setBorrado(false);
            sucursal.setEmail(email);
            sucursal.setHorario(horario);
            sucursal.setTelefono(telefono);
            sucursal.setImagenRemota(imagen);

            ManejadorSucursal.getInstance().crearSucursal(sucursal);
            ServicioActividad.getInstance().registrarCreacion(sucursal, usuario);
        }

        cerrarSesion(secreto);
    }

    private CargarSucursal iniciarSesion(String email, String passw) {
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
