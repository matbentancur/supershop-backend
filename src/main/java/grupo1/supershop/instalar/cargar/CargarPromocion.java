package grupo1.supershop.instalar.cargar;

import grupo1.supershop.beans.Producto;
import grupo1.supershop.beans.Promocion;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.fabricas.FabricaSesion;
import grupo1.supershop.interfaces.controladores.IControladorSesion;
import grupo1.supershop.persistencia.manejadores.ManejadorProducto;
import grupo1.supershop.persistencia.manejadores.ManejadorPromocion;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioFechaHora;
import grupo1.supershop.servicios.ServicioSesion;



public class CargarPromocion {
    private static final String EMAIL_ADMIN = "administrador@supershop.uy";
    private static final String PASSW_ADMIN = "administrador@supershop.uy";

    private final IControladorSesion icSesion;


    public CargarPromocion(){
        icSesion = FabricaSesion.getIControladorSesion();
    }
    
    public void cargarPromociones() throws Exception {
        String secreto = iniciarSesion(EMAIL_ADMIN,PASSW_ADMIN).obtenerSecreto(EMAIL_ADMIN,PASSW_ADMIN);
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        
        String[][] datos = {
                {"COCA-COLA 3L Promo 10%", "COCA-COLA 3L con 10%", "10", "01/07/2023", "12/11/2023", "4", "1", "1","https://res.cloudinary.com/hnxrbcyvp/image/upload/v1688577878/promo1_stgvhy.jpg"},
                {"Jabon PROTEX Promo", "Jabon PROTEX 2 con 20%", "20", "01/07/2023", "12/11/2023", "3", "103", "1","https://res.cloudinary.com/hnxrbcyvp/image/upload/v1688578841/promo2_vkusik.png"},
                {"Helado CRUFI 5L Promo", "Helado CRUFI 5L con 30%", "30", "01/07/2023", "12/11/2023", "2", "32", "1","https://res.cloudinary.com/hnxrbcyvp/image/upload/v1688579847/promocion_pwx5vn.png"},
        };

        for (String[] val : datos) {
            String imagen = val[8];
            Promocion promocion = new Promocion();
            promocion.setBorrado(false);
            promocion.setNombre(val[0]);
            promocion.setDescripcion(val[1]);
            promocion.setDescuentoPorcentual(Integer.parseInt(val[2]));
            promocion.setInicia(ServicioFechaHora.getInstance().stringToDate(val[3]));
            promocion.setFinaliza(ServicioFechaHora.getInstance().stringToDate(val[4]));
            promocion.setCantidad(Integer.parseInt(val[5]));
            Producto producto = ManejadorProducto.getInstance().obtenerProducto(Long.parseLong(val[6]));
            promocion.setProducto(producto);
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(Long.parseLong(val[7]));
            promocion.setSucursal(sucursal);
            promocion.setImagenRemota(imagen);

            ManejadorPromocion.getInstance().crearPromocion(promocion);
            ServicioActividad.getInstance().registrarCreacion(promocion, usuario);
        }
        cerrarSesion(secreto);
    }
    private CargarPromocion iniciarSesion(String email, String passw) {
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
