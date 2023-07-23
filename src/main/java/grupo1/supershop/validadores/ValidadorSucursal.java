package grupo1.supershop.validadores;

import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import java.util.ArrayList;
import java.util.List;

public class ValidadorSucursal {

    private static ValidadorSucursal instance = null;

    public static ValidadorSucursal getInstance() {
        if (instance == null) {
            instance = new ValidadorSucursal();
        }
        return instance;
    }

    private List<String> validarBean(Sucursal sucursal) {
        return ValidadorBase.getInstance().validarBean(sucursal);
    }

    public List<String> estaBorrada(Sucursal sucursal) {
        List<String> listaErrores = new ArrayList<>();

        if (sucursal.isBorrado()) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Sucursal.deleted.error"));
        }

        return listaErrores;
    }

    public List<String> existeImagen(Sucursal sucursal) {
        List<String> listaErrores = new ArrayList<>();

        if (sucursal.getImagen() != null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Sucursal.image.exists.error"));
        }

        return listaErrores;
    }

    public List<String> noExisteImagen(Sucursal sucursal) {
        List<String> listaErrores = new ArrayList<>();

        if (sucursal.getImagen() == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Sucursal.image.notexists.error"));
        }

        return listaErrores;
    }

    public List<String> existeImagenRemota(Sucursal sucursal) {
        List<String> listaErrores = new ArrayList<>();

        if (sucursal.getImagenRemota()!= null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Sucursal.image.exists.error"));
        }

        return listaErrores;
    }

    public List<String> noExisteImagenRemota(Sucursal sucursal) {
        List<String> listaErrores = new ArrayList<>();

        if (sucursal.getImagenRemota()== null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Sucursal.image.notexists.error"));
        }

        return listaErrores;
    }

    public List<String> existe(Sucursal sucursal) {
        List<String> listaErrores = new ArrayList<>();

        if (sucursal == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Sucursal.notexists.error"));
        }

        return listaErrores;
    }

    public DtMensajeSistema validarCrear(Sucursal sucursal, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.validarBean(sucursal));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarModificar(Sucursal sucursal, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(sucursal));
        listaErrores.addAll(this.estaBorrada(sucursal));
        listaErrores.addAll(this.validarBean(sucursal));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarBorrar(Sucursal sucursal, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(sucursal));
        listaErrores.addAll(this.estaBorrada(sucursal));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarRestaurar(Sucursal sucursal, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(sucursal));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarAgregarImagen(Sucursal sucursal, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(sucursal));
        listaErrores.addAll(this.estaBorrada(sucursal));
        listaErrores.addAll(this.existeImagen(sucursal));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarQuitarImagen(Sucursal sucursal, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(sucursal));
        listaErrores.addAll(this.estaBorrada(sucursal));
        listaErrores.addAll(this.noExisteImagen(sucursal));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarAgregarImagenRemota(Sucursal sucursal, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(sucursal));
        listaErrores.addAll(this.estaBorrada(sucursal));
        listaErrores.addAll(this.existeImagenRemota(sucursal));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarQuitarImagenRemota(Sucursal sucursal, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(sucursal));
        listaErrores.addAll(this.estaBorrada(sucursal));
        listaErrores.addAll(this.noExisteImagenRemota(sucursal));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

}
