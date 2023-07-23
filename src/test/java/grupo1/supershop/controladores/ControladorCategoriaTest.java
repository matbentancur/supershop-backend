package grupo1.supershop.controladores;

import grupo1.supershop.config.properties.PropertyReader;
import grupo1.supershop.config.report.listenertest.ListenerTest;
import grupo1.supershop.config.servicios.ServicioIniciarSesion;
import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtCategoria;
import grupo1.supershop.fabricas.FabricaCategoria;
import grupo1.supershop.instalar.InstalarSistema;
import grupo1.supershop.interfaces.controladores.IControladorCategoria;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith(ListenerTest.class)
class ControladorCategoriaTest {

    private static IControladorCategoria iControladorCategoria;

    @BeforeAll
    static void configuracion() throws Exception {
        iControladorCategoria = FabricaCategoria.getIControladorCategoria();
        new InstalarSistema().instalar();
    }

    @Test
    @DisplayName("Obtener categoria nombre")
    void obtenerCategoriaNombre() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);
        String nombre = PropertyReader.getInstance().getTestValue("ControladorCategoria.obtenerCategoriaNombre.nombre");
        String descripcion = PropertyReader.getInstance().getTestValue("ControladorCategoria.obtenerCategoriaNombre.descripcion");
        String imagenRemota = PropertyReader.getInstance().getTestValue("ControladorCategoria.obtenerCategoriaNombre.imagenRemota");

        DtCategoria dtCategoria = iControladorCategoria.obtenerCategoriaNombre(nombre, secreto);

        ServicioVerificacion.getInstance().verificar(nombre, dtCategoria.getNombre());
        ServicioVerificacion.getInstance().verificar(descripcion, dtCategoria.getDescripcion());
        ServicioVerificacion.getInstance().verificar(imagenRemota, dtCategoria.getImagenRemota());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Listar categorias")
    void listarCategorias() {
        String[] nombreCategorias = PropertyReader.getInstance().getTestValue("ControladorCategoria.listarCategorias.nombreCategorias").split(",");
        List<DtCategoria> listaCategorias = iControladorCategoria.listarCategorias();

        for (String nombreCategoria : nombreCategorias) {
            boolean existe = false;
            for (DtCategoria dtCategoria : listaCategorias) {
                if (dtCategoria.getNombre().equals(nombreCategoria)) {
                    existe = true;
                    break;
                }
            }
            ServicioVerificacion.getInstance().verificarTrue(existe);
        }
    }

    @Test
    @DisplayName("Crear categoria")
    void crearCategoria() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);
        String nombre = PropertyReader.getInstance().getTestValue("ControladorCategoria.crearCategoria.nombre");
        String descripcion = PropertyReader.getInstance().getTestValue("ControladorCategoria.crearCategoria.descripcion");
        String imagenRemota = PropertyReader.getInstance().getTestValue("ControladorCategoria.crearCategoria.imagenRemota");

        DtCategoria dtCategoria = new DtCategoria();
        dtCategoria.setNombre(nombre);
        dtCategoria.setDescripcion(descripcion);
        dtCategoria.setImagenRemota(imagenRemota);

        iControladorCategoria.crearCategoria(dtCategoria, secreto);

        DtCategoria dtPosCategoria = iControladorCategoria.obtenerCategoriaNombre(nombre, secreto);

        ServicioVerificacion.getInstance().verificar(nombre, dtPosCategoria.getNombre());
        ServicioVerificacion.getInstance().verificar(descripcion, dtPosCategoria.getDescripcion());
        ServicioVerificacion.getInstance().verificar(imagenRemota, dtPosCategoria.getImagenRemota());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Modificar categoria")
    void modificarCategoria() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);
        String nombre = PropertyReader.getInstance().getTestValue("ControladorCategoria.modificarCategoria.nombre");
        String descripcion = PropertyReader.getInstance().getTestValue("ControladorCategoria.modificarCategoria.descripcion");
        String imagenRemota = PropertyReader.getInstance().getTestValue("ControladorCategoria.modificarCategoria.imagenRemota");
        String nombreNuevo = PropertyReader.getInstance().getTestValue("ControladorCategoria.modificarCategoria.nombreNuevo");
        String descripcionNuevo = PropertyReader.getInstance().getTestValue("ControladorCategoria.modificarCategoria.descripcionNuevo");
        String imagenRemotaNuevo = PropertyReader.getInstance().getTestValue("ControladorCategoria.modificarCategoria.imagenRemotaNuevo");

        DtCategoria dtCategoria = new DtCategoria();
        dtCategoria.setNombre(nombre);
        dtCategoria.setDescripcion(descripcion);
        dtCategoria.setImagenRemota(imagenRemota);

        iControladorCategoria.crearCategoria(dtCategoria, secreto);

        DtCategoria dtPosCategoria = iControladorCategoria.obtenerCategoriaNombre(nombre, secreto);

        ServicioVerificacion.getInstance().verificar(nombre, dtPosCategoria.getNombre());
        ServicioVerificacion.getInstance().verificar(descripcion, dtPosCategoria.getDescripcion());
        ServicioVerificacion.getInstance().verificar(imagenRemota, dtPosCategoria.getImagenRemota());

        dtPosCategoria.setNombre(nombreNuevo);
        dtPosCategoria.setDescripcion(descripcionNuevo);
        dtPosCategoria.setImagenRemota(imagenRemotaNuevo);

        iControladorCategoria.modificarCategoria(dtPosCategoria, secreto);

        dtPosCategoria = iControladorCategoria.obtenerCategoriaNombre(nombreNuevo, secreto);

        ServicioVerificacion.getInstance().verificar(nombreNuevo, dtPosCategoria.getNombre());
        ServicioVerificacion.getInstance().verificar(descripcionNuevo, dtPosCategoria.getDescripcion());
        ServicioVerificacion.getInstance().verificar(imagenRemotaNuevo, dtPosCategoria.getImagenRemota());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Borrar categoria")
    void borrarCategoria() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);
        String nombre = PropertyReader.getInstance().getTestValue("ControladorCategoria.borrarCategoria.nombre");
        String descripcion = PropertyReader.getInstance().getTestValue("ControladorCategoria.borrarCategoria.descripcion");
        String imagenRemota = PropertyReader.getInstance().getTestValue("ControladorCategoria.borrarCategoria.imagenRemota");

        DtCategoria dtCategoria = new DtCategoria();
        dtCategoria.setNombre(nombre);
        dtCategoria.setDescripcion(descripcion);
        dtCategoria.setImagenRemota(imagenRemota);

        iControladorCategoria.crearCategoria(dtCategoria, secreto);

        DtCategoria dtPosCategoria = iControladorCategoria.obtenerCategoriaNombre(nombre, secreto);

        ServicioVerificacion.getInstance().verificar(nombre, dtPosCategoria.getNombre());
        ServicioVerificacion.getInstance().verificar(descripcion, dtPosCategoria.getDescripcion());
        ServicioVerificacion.getInstance().verificar(imagenRemota, dtPosCategoria.getImagenRemota());

        iControladorCategoria.borrarCategoria(dtPosCategoria.getId(), secreto);

        dtPosCategoria = iControladorCategoria.obtenerCategoriaNombre(nombre, secreto);

        ServicioVerificacion.getInstance().verificar(nombre, dtPosCategoria.getNombre());
        ServicioVerificacion.getInstance().verificar(descripcion, dtPosCategoria.getDescripcion());
        ServicioVerificacion.getInstance().verificar(imagenRemota, dtPosCategoria.getImagenRemota());
        ServicioVerificacion.getInstance().verificarTrue(dtPosCategoria.isBorrado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }
}