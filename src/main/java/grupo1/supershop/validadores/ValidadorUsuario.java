package grupo1.supershop.validadores;

import freemarker.template.utility.DateUtil;
import grupo1.supershop.beans.AdministradorSistema;
import grupo1.supershop.beans.AdministradorSucursal;
import grupo1.supershop.beans.AtencionCliente;
import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.beans.UsuarioSucursal;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.persistencia.manejadores.ManejadorUsuario;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import grupo1.supershop.servicios.ServicioPassword;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.time.DateUtils;

public class ValidadorUsuario {

    private static ValidadorUsuario instance = null;

    private final Pattern pattern;
    private Matcher matcher;
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%&*.,+-]).{8,128})";

    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();

    private ValidadorUsuario() {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    public static ValidadorUsuario getInstance() {
        if (instance == null) {
            instance = new ValidadorUsuario();
        }
        return instance;
    }

    //VALIDA QUE LA CONTRASEÑA CUMPLA CON EL PATRON
    private boolean validarPatron(final String password) {
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    //VALIDA QUE EL NOMBRE DE USUARIO NO SE IGUAL A LA CONTRASEÑA
    private boolean validarUsuarioPassword(final String email, final String password) {
        return !email.equalsIgnoreCase(password);
    }

    //VALIDA QUE LA CONTRAESÑA ANTERIOR NO SE IGUAL A LA NUEVA
    private boolean validarViejoNuevoPassword(final String passwordAnterior, final String passwordNuevo) {
        return !passwordAnterior.equalsIgnoreCase(passwordNuevo);
    }

    //VALIDA QUE LA CONTRASEÑA CUMPLA CON EL TAMAÑO
    private boolean validarLargoPassword(final String password) {
        if (password.length() < 8) {
            return false;
        }
        if (password.length() > 128) {
            return false;
        }
        return true;
    }

    //VALIDA QUE EL PASSWORD INDICADO SEA EL ACTUAL
    private List<String> validarPasswordCorrecto(Usuario usuario, final String password) {
        List<String> listaErrores = new ArrayList<>();

        ServicioPassword passwordUtils = new ServicioPassword();
        if (!passwordUtils.chequearPasswordHash(password, usuario.getPassword())) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Usuario.autentication.error"));
        }

        return listaErrores;
    }

    private List<String> validarNuevoEmail(String email) {
        List<String> listaErrores = new ArrayList<>();

        //VALIDA SI EXISTE YA EL CORREO ELECTRONICO
        Usuario usuarioExiste = ManejadorUsuario.getInstance().obtenerUsuario("email", email);
        if (usuarioExiste != null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Usuario.email.exists.error"));
        }

        return listaErrores;

    }

    private List<String> validarActualizarEmail(Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        //VALIDA SI EXISTE YA EL CORREO ELECTRONICO
        Usuario usuarioExiste = ManejadorUsuario.getInstance().obtenerUsuario("email", usuario.getEmail());
        if (usuarioExiste != null) {
            if (!usuario.getId().equals(usuarioExiste.getId())) {
                listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Usuario.email.exists.error"));
            }
        }

        return listaErrores;

    }

    private List<String> validarClave(Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        //TAMAÑO DE CONTRASEÑA
        if (!this.validarLargoPassword(usuario.getPassword())) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Password.length.error"));
        }
        //PATRON DE LA CONTRASEÑA
        if (!this.validarPatron(usuario.getPassword())) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Password.pattern.error"));
        }
        //EMAIL IGUAL A CONTRASEÑA
        if (!this.validarUsuarioPassword(usuario.getEmail(), usuario.getPassword())) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Password.userEqualPass.error"));
        }
        return listaErrores;
    }

    private List<String> validarBean(Usuario usuario) {
        return ValidadorBase.getInstance().validarBean(usuario);
    }

    public List<String> existe(Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        if (usuario == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Usuario.notexists.error"));
        }

        return listaErrores;
    }

    public List<String> estaBorrado(Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        if (usuario.isBorrado()) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Usuario.deleted.error"));
        }

        return listaErrores;
    }

    public List<String> estaBloqueado(Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        if (usuario.isBloqueado()) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Usuario.disabled.error"));
        }

        return listaErrores;
    }

    public List<String> estaBorradoBloqueado(Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        if (usuario.isBorrado()) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Usuario.deleted.error"));
        }
        if (usuario.isBloqueado()) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Usuario.disabled.error"));
        }

        return listaErrores;
    }

    public List<String> esComprador(Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        if (!(usuario instanceof Comprador)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Usuario.buyer.error"));
        }

        return listaErrores;
    }

    public List<String> esAdministradorSistema(Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        if (!(usuario instanceof AdministradorSistema)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Usuario.admin.error"));
        }

        return listaErrores;
    }

    public List<String> esAdministradorSucursal(Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        if (!(usuario instanceof AdministradorSucursal)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Usuario.branchAdmin.error"));
        }

        return listaErrores;
    }

    public List<String> esUsuarioSucursal(Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        if (!(usuario instanceof AtencionCliente) && !(usuario instanceof AdministradorSucursal)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Usuario.support.error"));
        }

        return listaErrores;
    }

    public List<String> perteneceSusursal(Usuario usuario, Sucursal sucursal) {
        List<String> listaErrores = new ArrayList<>();

        if (!(usuario instanceof UsuarioSucursal)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Usuario.branch.error"));
            return listaErrores;
        }

        UsuarioSucursal usuarioSucursal = (UsuarioSucursal) usuario;
        if (!sucursal.equals(usuarioSucursal.getSucursal())) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Usuario.branch.error"));
        }

        return listaErrores;
    }

    public List<String> esMayorEdad(Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        Date fechaActual = new Date();
        Date fechaMayoriaEdad = DateUtils.addYears(fechaActual, -18);
        if (!usuario.getNacimiento().before(fechaMayoriaEdad)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Usuario.age.error"));
        }

        return listaErrores;
    }

    public DtMensajeSistema autenticarUsuario(String email, String password) {
        List<String> listaErrores = new ArrayList<>();

        Usuario usuario = ManejadorUsuario.getInstance().obtenerUsuario("email", email);
        if (usuario != null) {
            listaErrores.addAll(this.validarPasswordCorrecto(usuario, password));
            listaErrores.addAll(this.estaBorradoBloqueado(usuario));
        } else {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Usuario.autentication.error"));
        }

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarCrear(Usuario usuario, Usuario administradorSistema) {
        List<String> listaErrores = new ArrayList<>();
        listaErrores.addAll(this.existe(administradorSistema));
        listaErrores.addAll(this.estaBorradoBloqueado(administradorSistema));
        listaErrores.addAll(this.esAdministradorSistema(administradorSistema));

        listaErrores.addAll(this.validarNuevoEmail(usuario.getEmail()));
        listaErrores.addAll(this.validarBean(usuario));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarCrearUsuarioSucursal(UsuarioSucursal usuarioSucursal, Usuario administradorSistema) {
        List<String> listaErrores = new ArrayList<>();
        listaErrores.addAll(this.existe(administradorSistema));
        listaErrores.addAll(this.estaBorradoBloqueado(administradorSistema));
        listaErrores.addAll(this.esAdministradorSistema(administradorSistema));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(usuarioSucursal.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(usuarioSucursal.getSucursal()));
        listaErrores.addAll(this.validarNuevoEmail(usuarioSucursal.getEmail()));
        listaErrores.addAll(this.validarBean(usuarioSucursal));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarModificar(Usuario usuario, AdministradorSistema administradorSistema) {
        List<String> listaErrores = new ArrayList<>();
        listaErrores.addAll(this.existe(administradorSistema));
        listaErrores.addAll(this.estaBorradoBloqueado(administradorSistema));
        listaErrores.addAll(this.esAdministradorSistema(administradorSistema));
        listaErrores.addAll(this.existe(usuario));
        listaErrores.addAll(this.estaBorradoBloqueado(usuario));
        listaErrores.addAll(this.validarActualizarEmail(usuario));
        listaErrores.addAll(this.validarBean(usuario));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarModificarUsuarioSucursal(UsuarioSucursal usuarioSucursal, AdministradorSistema administradorSistema) {
        List<String> listaErrores = new ArrayList<>();
        listaErrores.addAll(this.existe(administradorSistema));
        listaErrores.addAll(this.estaBorradoBloqueado(administradorSistema));
        listaErrores.addAll(this.esAdministradorSistema(administradorSistema));
        listaErrores.addAll(this.existe(usuarioSucursal));
        listaErrores.addAll(this.estaBorradoBloqueado(usuarioSucursal));
        listaErrores.addAll(this.validarActualizarEmail(usuarioSucursal));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(usuarioSucursal.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(usuarioSucursal.getSucursal()));
        listaErrores.addAll(this.validarBean(usuarioSucursal));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarBorrar(Usuario usuario, AdministradorSistema administradorSistema) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(this.existe(administradorSistema));
        listaErrores.addAll(this.estaBorradoBloqueado(administradorSistema));
        listaErrores.addAll(this.esAdministradorSistema(administradorSistema));
        listaErrores.addAll(this.existe(usuario));
        listaErrores.addAll(this.estaBorradoBloqueado(usuario));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarRestaurar(Usuario usuario, AdministradorSistema administradorSistema) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(this.existe(administradorSistema));
        listaErrores.addAll(this.estaBorradoBloqueado(administradorSistema));
        listaErrores.addAll(this.esAdministradorSistema(administradorSistema));
        listaErrores.addAll(this.existe(usuario));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarBloquear(Usuario usuario, AdministradorSistema administradorSistema) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(this.existe(administradorSistema));
        listaErrores.addAll(this.estaBorradoBloqueado(administradorSistema));
        listaErrores.addAll(this.esAdministradorSistema(administradorSistema));
        listaErrores.addAll(this.existe(usuario));
        listaErrores.addAll(this.estaBorradoBloqueado(usuario));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarDesbloquear(Usuario usuario, AdministradorSistema administradorSistema) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(this.existe(administradorSistema));
        listaErrores.addAll(this.estaBorradoBloqueado(administradorSistema));
        listaErrores.addAll(this.esAdministradorSistema(administradorSistema));
        listaErrores.addAll(this.existe(usuario));
        listaErrores.addAll(this.estaBorrado(usuario));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarRegistrarComprador(Comprador comprador) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(this.validarNuevoEmail(comprador.getEmail()));
        listaErrores.addAll(this.validarClave(comprador));
        listaErrores.addAll(this.esMayorEdad(comprador));
        listaErrores.addAll(this.validarBean(comprador));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarModificarMisDatos(Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(this.existe(usuario));
        listaErrores.addAll(this.estaBorradoBloqueado(usuario));
        listaErrores.addAll(this.validarActualizarEmail(usuario));
        listaErrores.addAll(this.validarBean(usuario));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarModificarMiClave(String passwordActual, String passwordNuevo, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(this.existe(usuario));
        listaErrores.addAll(this.estaBorradoBloqueado(usuario));
        listaErrores.addAll(this.validarPasswordCorrecto(usuario, passwordActual));
        usuario.setPassword(passwordNuevo);
        listaErrores.addAll(this.validarClave(usuario));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarBorrarMiUsuario(Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(this.existe(usuario));
        listaErrores.addAll(this.estaBorradoBloqueado(usuario));
        listaErrores.addAll(this.esComprador(usuario));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarModificarPushToken(Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(this.existe(usuario));
        listaErrores.addAll(this.estaBorradoBloqueado(usuario));
        listaErrores.addAll(this.esComprador(usuario));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

}
