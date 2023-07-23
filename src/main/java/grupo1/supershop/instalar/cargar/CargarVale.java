package grupo1.supershop.instalar.cargar;

import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.beans.Vale;
import grupo1.supershop.enums.EstadoVale;
import grupo1.supershop.fabricas.FabricaSesion;
import grupo1.supershop.interfaces.controladores.IControladorSesion;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import grupo1.supershop.persistencia.manejadores.ManejadorUsuario;
import grupo1.supershop.persistencia.manejadores.ManejadorVale;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioFechaHora;
import grupo1.supershop.servicios.ServicioSesion;

import java.math.BigDecimal;

public class CargarVale {
    private static final String EMAIL_ADMIN = "administrador@supershop.uy";
    private static final String PASSW_ADMIN = "administrador@supershop.uy";

    private final IControladorSesion icSesion;


    public CargarVale(){
        icSesion = FabricaSesion.getIControladorSesion();
    }
    
    public void cargarVales() throws Exception {
        String secreto = iniciarSesion(EMAIL_ADMIN,PASSW_ADMIN).obtenerSecreto(EMAIL_ADMIN,PASSW_ADMIN);
        Usuario usuarioAux = ServicioSesion.getInstance().obtenerUsuario(secreto);
        
        String[][] datos = {
                {"11", "Producto vencido.", "10/11/2023", "300", "1",},
                {"11", "Producto sin stock.", "10/11/2023", "140", "1",},
                {"12", "Producto vencido.", "10/11/2023", "140", "1",},
                {"12", "Producto sin stock.", "10/11/2023", "140", "1",},
                {"13", "Producto vencido.", "10/11/2023", "140", "1",},
                {"13", "Producto sin stock.", "10/11/2023", "140", "1",},
                {"14", "Producto vencido.", "10/11/2023", "140", "1",},
                {"14", "Producto sin stock.", "10/11/2023", "140", "1",},
                {"15", "Producto vencido.", "10/11/2023", "140", "1",},
                {"15", "Producto sin stock.", "10/11/2023", "140", "1",},
                {"16", "Producto vencido.", "10/11/2023", "140", "1",},
                {"16", "Producto sin stock.", "10/11/2023", "140", "1",}
        };

        for (String[] val : datos) {
            Vale vale = new Vale();
            Comprador usuario = (Comprador) ManejadorUsuario.getInstance().obtenerUsuario(Long.parseLong(val[0]));
            vale.setComprador(usuario);
            vale.setDescripcion(val[1]);
            vale.setEstado(EstadoVale.DISPONIBLE);
            vale.setExpira(ServicioFechaHora.getInstance().stringToDate(val[2]));
            vale.setMonto(BigDecimal.valueOf(Integer.parseInt(val[3])));
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(Long.parseLong(val[4]));
            vale.setSucursal(sucursal);

            ManejadorVale.getInstance().crearVale(vale);
            ServicioActividad.getInstance().registrarCreacion(vale, usuarioAux);
        }
        cerrarSesion(secreto);
    }
    private CargarVale iniciarSesion(String email, String passw) {
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
