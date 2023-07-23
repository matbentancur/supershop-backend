package grupo1.supershop.instalar.cargar;

import grupo1.supershop.beans.AdministradorSistema;
import grupo1.supershop.persistencia.manejadores.ManejadorUsuario;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class CargarAdminSistema {

    public void cargarUsuarios() throws Exception {
        String[][] datos = {
                {"administrador@supershop.uy", "administrador@supershop.uy", "Administrador", "Administrador", "00000001"},
                {"administrador2@supershop.uy", "administrador2@supershop.uy", "Administrador 2", "Administrador 2", "00000002"}
        };

        for (String[] val : datos) {
            AdministradorSistema administradorSistema = new AdministradorSistema();
            administradorSistema.setBloqueado(false);
            administradorSistema.setEmail(val[0]);
            administradorSistema.setPassword(val[1]);
            administradorSistema.cifrarPassword();
            administradorSistema.setNombres(val[2]);
            administradorSistema.setApellidos(val[3]);
            administradorSistema.setCelular(val[4]);
            administradorSistema.setNacimiento(new Date());

            ManejadorUsuario.getInstance().crearUsuario(administradorSistema);

        }
    }
}
