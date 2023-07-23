package grupo1.supershop.instalar.cargar;

import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.fabricas.FabricaSesion;
import grupo1.supershop.interfaces.controladores.IControladorSesion;
import grupo1.supershop.persistencia.manejadores.ManejadorUsuario;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioSesion;

import java.util.Date;

public class CargarComprador {
    private static final String EMAIL_ADMIN = "administrador@supershop.uy";
    private static final String PASSW_ADMIN = "administrador@supershop.uy";

    private final IControladorSesion icSesion;


    public CargarComprador() {
        icSesion = FabricaSesion.getIControladorSesion();
    }

    public void cargarUsuarios() throws Exception {
        String secreto = iniciarSesion(EMAIL_ADMIN, PASSW_ADMIN).obtenerSecreto(EMAIL_ADMIN, PASSW_ADMIN);
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);

        String[][] datos = {
                {"supershop.uruguay.github@gmail.com", "supershop.uruguay.github@gmail.com", "Supershop", "Uruguay", "00000000"},
                {"leonardogabrielperez1993@gmail.com", "leonardogabrielperez1993@gmail.com", "Leonardo Gabriel", "Perez Larrosa", "00000001"},
                {"comprador@supershop.uy", "comprador@supershop.uy", "Jeniffer", "Mello", "00000002"},
                {"comprador2@supershop.uy", "comprador2@supershop.uy", "Lucia", "Da Silva", "00000003"},
                {"comprador3@supershop.uy", "comprador3@supershop.uy", "Miguel", "Perez", "00000004"},
                {"comprador4@supershop.uy", "comprador4@supershop.uy", "Maximiliano", "Perez", "00000005"},
                {"comprador5@supershop.uy", "comprador5@supershop.uy", "Miriam", "Larrosa", "00000006"},
                {"comprador6@supershop.uy", "comprador6@supershop.uy", "Juan", "Ortigoza", "00000007"},
                {"comprador7@supershop.uy", "comprador7@supershop.uy", "Fernando", "Lopez", "00000008"},
                {"comprador8@supershop.uy", "comprador8@supershop.uy", "Jesus", "Calzada", "00000009"},
                {"comprador9@supershop.uy", "comprador9@supershop.uy", "Hector", "Mendez", "00000010"},
                {"comprador10@supershop.uy", "comprador10@supershop.uy", "Agustina", "Fernandez", "00000011"},
                {"comprador11@supershop.uy", "comprador11@supershop.uy", "Natalia", "Martinez", "00000012"},
                {"comprador12@supershop.uy", "comprador12@supershop.uy", "Maria", "Acosta", "00000013"},
        };

        for (String[] val : datos) {
            Comprador comprador = new Comprador();
            comprador.setBloqueado(false);
            comprador.setEmail(val[0]);
            comprador.setPassword(val[1]);
            comprador.cifrarPassword();
            comprador.setNombres(val[2]);
            comprador.setApellidos(val[3]);
            comprador.setCelular(val[4]);
            comprador.setNacimiento(new Date());

            ManejadorUsuario.getInstance().crearUsuario(comprador);
            ServicioActividad.getInstance().registrarCreacion(comprador, usuario);
        }
        cerrarSesion(secreto);
    }

    private CargarComprador iniciarSesion(String email, String passw) {
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
