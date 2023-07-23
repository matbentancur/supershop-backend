package grupo1.supershop.validadores;

import grupo1.supershop.beans.MetodoEnvio;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import java.util.ArrayList;
import java.util.List;

public class ValidadorMetodoEnvio {

    private static ValidadorMetodoEnvio instance = null;

    public static ValidadorMetodoEnvio getInstance() {
        if (instance == null) {
            instance = new ValidadorMetodoEnvio();
        }
        return instance;
    }

    private List<String> validarBean(MetodoEnvio metodoEnvio) {
        return ValidadorBase.getInstance().validarBean(metodoEnvio);
    }

    public List<String> estaBorrado(MetodoEnvio metodoEnvio) {
        List<String> listaErrores = new ArrayList<>();

        if (metodoEnvio.isBorrado()) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("MetodoEnvio.deleted.error"));
        }

        return listaErrores;
    }

    public List<String> existe(MetodoEnvio metodoEnvio) {
        List<String> listaErrores = new ArrayList<>();

        if (metodoEnvio == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("MetodoEnvio.notexists.error"));
        }

        return listaErrores;
    }

    public DtMensajeSistema validarCrear(MetodoEnvio metodoEnvio, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.validarBean(metodoEnvio));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarModificar(MetodoEnvio metodoEnvio, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(metodoEnvio));
        listaErrores.addAll(this.estaBorrado(metodoEnvio));
        listaErrores.addAll(this.validarBean(metodoEnvio));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarBorrar(MetodoEnvio metodoEnvio, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(metodoEnvio));
        listaErrores.addAll(this.estaBorrado(metodoEnvio));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarRestaurar(MetodoEnvio metodoEnvio, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSistema(usuario));
        listaErrores.addAll(this.existe(metodoEnvio));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

}
