package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.AdministradorSistema;
import grupo1.supershop.beans.AdministradorSucursal;
import grupo1.supershop.beans.AtencionCliente;
import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.beans.UsuarioSucursal;
import java.util.List;

public class ManejadorUsuario {

    private static ManejadorUsuario instance = null;

    private ManejadorUsuario() {
    }

    public static ManejadorUsuario getInstance() {
        if (instance == null) {
            instance = new ManejadorUsuario();
        }
        return instance;
    }

    public Usuario obtenerUsuario(Long id) {
        return (Usuario) ManejadorBase.getInstance().obtenerBean(new Usuario(), "id", id);
    }

    public <T> Usuario obtenerUsuario(String parametro, T valor) {
        return (Usuario) ManejadorBase.getInstance().obtenerBean(new Usuario(), parametro, valor);
    }

    public UsuarioSucursal obtenerUsuarioSucursal(Long id) {
        return (UsuarioSucursal) ManejadorBase.getInstance().obtenerBean(new UsuarioSucursal(), "id", id);
    }

    public AdministradorSistema obtenerAdministradorSistema(Long id) {
        return (AdministradorSistema) ManejadorBase.getInstance().obtenerBean(new AdministradorSistema(), "id", id);
    }

    public AdministradorSucursal obtenerAdministradorSucursal(Long id) {
        return (AdministradorSucursal) ManejadorBase.getInstance().obtenerBean(new AdministradorSucursal(), "id", id);
    }

    public AtencionCliente obtenerAtencionCliente(Long id) {
        return (AtencionCliente) ManejadorBase.getInstance().obtenerBean(new AtencionCliente(), "id", id);
    }

    public Comprador obtenerComprador(Long id) {
        return (Comprador) ManejadorBase.getInstance().obtenerBean(new Comprador(), "id", id);
    }

    public <T> List<Usuario> listarUsuarios(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBean(new Usuario(), parametro, valor, parametro, true);
    }

    public <T> List<AdministradorSistema> listarAdministradoresSistema(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBean(new AdministradorSistema(), parametro, valor, parametro, true);
    }

    public <T> List<AdministradorSucursal> listarAdministradoresSucursal(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBean(new AdministradorSucursal(), parametro, valor, parametro, true);
    }

    public <T> List<AtencionCliente> listarAtencionCliente(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBean(new AtencionCliente(), parametro, valor, parametro, true);
    }

    public <T> List<Comprador> listarCompradores(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBean(new Comprador(), parametro, valor, parametro, true);
    }

    public void crearUsuario(Usuario usuario) throws Exception {
        ManejadorBase.getInstance().crearBean(usuario);
    }

    public void modificarUsuario(Usuario usuario) throws Exception {
        ManejadorBase.getInstance().modificarBean(usuario);
    }

    public void borrarUsuario(Usuario usuario) throws Exception {
        ManejadorBase.getInstance().borradoLogicoBean(usuario);
    }

    public void restaurarUsuario(Usuario usuario) throws Exception {
        ManejadorBase.getInstance().restaurarBean(usuario);
    }

}
