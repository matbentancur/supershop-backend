package grupo1.supershop.validadores;

import grupo1.supershop.beans.Producto;
import grupo1.supershop.beans.Promocion;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import java.util.ArrayList;
import java.util.List;

public class ValidadorPromocion {

    private static ValidadorPromocion instance = null;

    public static ValidadorPromocion getInstance() {
        if (instance == null) {
            instance = new ValidadorPromocion();
        }
        return instance;
    }

    private List<String> validarBean(Promocion promocion) {
        return ValidadorBase.getInstance().validarBean(promocion);
    }

    public List<String> estaBorrada(Promocion promocion) {
        List<String> listaErrores = new ArrayList<>();

        if (promocion.isBorrado()) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Promocion.deleted.error"));
        }

        return listaErrores;
    }

    public List<String> existeImagen(Promocion promocion) {
        List<String> listaErrores = new ArrayList<>();

        if (promocion.getImagen() != null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Promocion.image.exists.error"));
        }

        return listaErrores;
    }

    public List<String> noExisteImagen(Promocion promocion) {
        List<String> listaErrores = new ArrayList<>();

        if (promocion.getImagen() == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Promocion.image.notexists.error"));
        }

        return listaErrores;
    }

    public List<String> existeImagenRemota(Promocion promocion) {
        List<String> listaErrores = new ArrayList<>();

        if (promocion.getImagenRemota() != null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Promocion.image.exists.error"));
        }

        return listaErrores;
    }

    public List<String> noExisteImagenRemota(Promocion promocion) {
        List<String> listaErrores = new ArrayList<>();

        if (promocion.getImagenRemota() == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Promocion.image.notexists.error"));
        }

        return listaErrores;
    }

    public List<String> existe(Promocion promocion) {
        List<String> listaErrores = new ArrayList<>();

        if (promocion == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Promocion.notexists.error"));
        }

        return listaErrores;
    }

    public DtMensajeSistema validarCrear(Promocion promocion, Producto producto, Sucursal sucursal, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSucursal(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().perteneceSusursal(usuario, sucursal));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(sucursal));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(sucursal));
        listaErrores.addAll(ValidadorProducto.getInstance().existe(producto));
        listaErrores.addAll(ValidadorProducto.getInstance().estaBorrado(producto));
        listaErrores.addAll(this.validarBean(promocion));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarModificar(Promocion promocion, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSucursal(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().perteneceSusursal(usuario, promocion.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(promocion.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(promocion.getSucursal()));
        listaErrores.addAll(ValidadorProducto.getInstance().existe(promocion.getProducto()));
        listaErrores.addAll(ValidadorProducto.getInstance().estaBorrado(promocion.getProducto()));
        listaErrores.addAll(this.existe(promocion));
        listaErrores.addAll(this.estaBorrada(promocion));
        listaErrores.addAll(this.validarBean(promocion));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarBorrar(Promocion promocion, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSucursal(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().perteneceSusursal(usuario, promocion.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(promocion.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(promocion.getSucursal()));
        listaErrores.addAll(this.existe(promocion));
        listaErrores.addAll(this.estaBorrada(promocion));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarRestaurar(Promocion promocion, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSucursal(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().perteneceSusursal(usuario, promocion.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(promocion.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(promocion.getSucursal()));
        listaErrores.addAll(ValidadorProducto.getInstance().existe(promocion.getProducto()));
        listaErrores.addAll(ValidadorProducto.getInstance().estaBorrado(promocion.getProducto()));
        listaErrores.addAll(this.existe(promocion));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarAgregarImagen(Promocion promocion, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSucursal(usuario));
        listaErrores.addAll(this.existe(promocion));
        listaErrores.addAll(this.estaBorrada(promocion));
        listaErrores.addAll(this.existeImagen(promocion));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarQuitarImagen(Promocion promocion, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSucursal(usuario));
        listaErrores.addAll(this.existe(promocion));
        listaErrores.addAll(this.estaBorrada(promocion));
        listaErrores.addAll(this.noExisteImagen(promocion));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarAgregarImagenRemota(Promocion promocion, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSucursal(usuario));
        listaErrores.addAll(this.existe(promocion));
        listaErrores.addAll(this.estaBorrada(promocion));
        listaErrores.addAll(this.existeImagenRemota(promocion));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarQuitarImagenRemota(Promocion promocion, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSucursal(usuario));
        listaErrores.addAll(this.existe(promocion));
        listaErrores.addAll(this.estaBorrada(promocion));
        listaErrores.addAll(this.noExisteImagenRemota(promocion));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

}
