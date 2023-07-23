package grupo1.supershop.validadores;

import grupo1.supershop.beans.Producto;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import java.util.ArrayList;
import java.util.List;

public class ValidadorProducto {

    private static ValidadorProducto instance = null;

    public static ValidadorProducto getInstance() {
        if (instance == null) {
            instance = new ValidadorProducto();
        }
        return instance;
    }

    private List<String> validarBean(Producto producto) {
        return ValidadorBase.getInstance().validarBean(producto);
    }

    public List<String> estaBorrado(Producto producto) {
        List<String> listaErrores = new ArrayList<>();

        if (producto.isBorrado()) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Producto.deleted.error"));
        }

        return listaErrores;
    }

    public List<String> existe(Producto producto) {
        List<String> listaErrores = new ArrayList<>();

        if (producto == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Producto.notexists.error"));
        }

        return listaErrores;
    }

    public DtMensajeSistema validarCrear(Producto producto, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.validarBean(producto));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarModificar(Producto producto, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(producto));
        listaErrores.addAll(this.estaBorrado(producto));
        listaErrores.addAll(this.validarBean(producto));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarBorrar(Producto producto, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(producto));
        listaErrores.addAll(this.estaBorrado(producto));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarRestaurar(Producto producto, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(producto));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarAgregarImagen(Producto producto, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(producto));
        listaErrores.addAll(this.estaBorrado(producto));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarQuitarImagen(Producto producto, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(producto));
        listaErrores.addAll(this.estaBorrado(producto));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

}
