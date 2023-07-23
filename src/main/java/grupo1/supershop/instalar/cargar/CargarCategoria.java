package grupo1.supershop.instalar.cargar;

import grupo1.supershop.beans.Categoria;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.fabricas.FabricaSesion;
import grupo1.supershop.interfaces.controladores.IControladorSesion;
import grupo1.supershop.persistencia.manejadores.ManejadorCategoria;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioSesion;


public class CargarCategoria {
    private static final String EMAIL_ADMIN = "administrador@supershop.uy";
    private static final String PASSW_ADMIN = "administrador@supershop.uy";

    private final IControladorSesion icSesion;


    public CargarCategoria(){
        icSesion = FabricaSesion.getIControladorSesion();
    }
    
    public void cargarCategorias() throws Exception {
        String secreto = iniciarSesion(EMAIL_ADMIN,PASSW_ADMIN).obtenerSecreto(EMAIL_ADMIN,PASSW_ADMIN);
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        
        String[][] datos = {
                {"Bebidas", "bebidas","https://res.cloudinary.com/hnxrbcyvp/image/upload/v1688524430/99955_tsqjy5.png"},
                {"Limpieza", "limpieza", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1688524519/2737066_ef2uim.png"},
                {"Tecnologia", "tecnologia","https://res.cloudinary.com/hnxrbcyvp/image/upload/v1688562475/tecnologiaIcono_pt66yj.png"},
                {"Congelados", "congelados","https://res.cloudinary.com/hnxrbcyvp/image/upload/v1688562361/CongeladosIcono_gkgvrk.png"},
                {"Deportes", "deportes","https://res.cloudinary.com/hnxrbcyvp/image/upload/v1688562371/deporteIcono_norpg0.png"},
                {"Hogar", "hogar","https://res.cloudinary.com/hnxrbcyvp/image/upload/v1688562344/hogarIcono_afo5qp.png"}
        };

        for (String[] val : datos) {
            String nombre = val[0];
            String descripcion = val[1];
            String imagen = val[2];
            Categoria categoria = new Categoria();
            categoria.setBorrado(false);
            categoria.setNombre(nombre);
            categoria.setDescripcion(descripcion);
            categoria.setImagenRemota(imagen);

            ManejadorCategoria.getInstance().crearCategoria(categoria);
            ServicioActividad.getInstance().registrarCreacion(categoria, usuario);
        }
        cerrarSesion(secreto);
    }
    private CargarCategoria iniciarSesion(String email, String passw) {
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
