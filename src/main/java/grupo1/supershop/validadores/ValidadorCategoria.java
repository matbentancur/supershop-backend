package grupo1.supershop.validadores;

import grupo1.supershop.beans.Categoria;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import java.util.ArrayList;
import java.util.List;

public class ValidadorCategoria {

    private static ValidadorCategoria instance = null;

    public static ValidadorCategoria getInstance() {
        if (instance == null) {
            instance = new ValidadorCategoria();
        }
        return instance;
    }

    private List<String> validarBean(Categoria categoria) {
        return ValidadorBase.getInstance().validarBean(categoria);
    }

    public List<String> estaBorrada(Categoria categoria) {
        List<String> listaErrores = new ArrayList<>();

        if (categoria.isBorrado()) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Categoria.deleted.error"));
        }

        return listaErrores;
    }

    public List<String> existeImagen(Categoria categoria) {
        List<String> listaErrores = new ArrayList<>();

        if (categoria.getImagen() != null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Categoria.image.exists.error"));
        }

        return listaErrores;
    }

    public List<String> noExisteImagen(Categoria categoria) {
        List<String> listaErrores = new ArrayList<>();

        if (categoria.getImagen() == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Categoria.image.notexists.error"));
        }

        return listaErrores;
    }

    public List<String> existeImagenRemota(Categoria categoria) {
        List<String> listaErrores = new ArrayList<>();

        if (categoria.getImagenRemota() != null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Categoria.image.exists.error"));
        }

        return listaErrores;
    }

    public List<String> noExisteImagenRemota(Categoria categoria) {
        List<String> listaErrores = new ArrayList<>();

        if (categoria.getImagenRemota() == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Categoria.image.notexists.error"));
        }

        return listaErrores;
    }

    public List<String> existe(Categoria categoria) {
        List<String> listaErrores = new ArrayList<>();

        if (categoria == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Categoria.notexists.error"));
        }

        return listaErrores;
    }

    public DtMensajeSistema validarCrear(Categoria categoria, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.validarBean(categoria));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarModificar(Categoria categoria, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(categoria));
        listaErrores.addAll(this.estaBorrada(categoria));
        listaErrores.addAll(this.validarBean(categoria));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarBorrar(Categoria categoria, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(categoria));
        listaErrores.addAll(this.estaBorrada(categoria));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarRestaurar(Categoria categoria, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(categoria));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarAgregarImagen(Categoria categoria, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(categoria));
        listaErrores.addAll(this.estaBorrada(categoria));
        listaErrores.addAll(this.existeImagen(categoria));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarQuitarImagen(Categoria categoria, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(categoria));
        listaErrores.addAll(this.estaBorrada(categoria));
        listaErrores.addAll(this.noExisteImagen(categoria));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarAgregarImagenRemota(Categoria categoria, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(categoria));
        listaErrores.addAll(this.estaBorrada(categoria));
        listaErrores.addAll(this.existeImagenRemota(categoria));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarQuitarImagenRemota(Categoria categoria, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(categoria));
        listaErrores.addAll(this.estaBorrada(categoria));
        listaErrores.addAll(this.noExisteImagenRemota(categoria));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

}
