package grupo1.supershop.instalar.cargar;

import grupo1.supershop.beans.ServidorCorreo;
import grupo1.supershop.persistencia.manejadores.ManejadorBase;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CargarServidorCorreo {
    public void crearServidorCorreo() throws Exception {
        ServidorCorreo servidorCorreo = (ServidorCorreo) ManejadorBase.getInstance().obtenerBean(new ServidorCorreo(), "nombre", "defecto");
        if (servidorCorreo == null) {
            servidorCorreo = new ServidorCorreo();
            servidorCorreo.setNombre("defecto");
            servidorCorreo.setServidor("smtp.gmail.com");
            servidorCorreo.setPuerto(587);
            servidorCorreo.setSeguridad("STARTTLS");
            servidorCorreo.setIdentificacion("PLAIN");
            servidorCorreo.setDesdeCorreo("supershop.uruguay@gmail.com");
            servidorCorreo.setDesdeNombre("SuperShop Uruguay");
            servidorCorreo.setUsuario("supershop.uruguay@gmail.com");
            servidorCorreo.setPassword("hkybdzdpezrvvazj");
            servidorCorreo.encriptarPassword();

            ManejadorBase.getInstance().crearBean(servidorCorreo);
        }
    }
}
