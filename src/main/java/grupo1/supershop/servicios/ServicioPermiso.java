package grupo1.supershop.servicios;

import grupo1.supershop.beans.AdministradorSistema;
import grupo1.supershop.beans.AdministradorSucursal;
import grupo1.supershop.beans.AtencionCliente;
import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.beans.UsuarioSucursal;

public class ServicioPermiso {

    private static ServicioPermiso instance = null;

    private ServicioPermiso() {
    }

    public static ServicioPermiso getInstance() {
        if (instance == null) {
            instance = new ServicioPermiso();
        }
        return instance;
    }

    private boolean estaBorradoBloqueado(Usuario usuario) {
        if (usuario.isBorrado()) {
            return true;
        }
        if (usuario.isBloqueado()) {
            return true;
        }
        return false;
    }

    public boolean permisoComprador(Usuario usuario) {
        if (this.estaBorradoBloqueado(usuario)) {
            return false;
        }

        if (usuario instanceof Comprador) {
            return true;
        } else {
            return false;
        }
    }

    public boolean permisoAdministradorSistema(Usuario usuario) {
        if (this.estaBorradoBloqueado(usuario)) {
            return false;
        }

        if (usuario instanceof AdministradorSistema) {
            return true;
        } else {
            return false;
        }
    }

    public boolean permisoAdministradorSucursal(Usuario usuario) {
        if (this.estaBorradoBloqueado(usuario)) {
            return false;
        }

        if (usuario instanceof AdministradorSucursal) {
            return true;
        } else {
            return false;
        }
    }

    public boolean permisoAtencionCliente(Usuario usuario) {
        if (this.estaBorradoBloqueado(usuario)) {
            return false;
        }

        if (usuario instanceof AtencionCliente) {
            return true;
        } else {
            return false;
        }
    }

    public boolean permisoSucursal(Usuario usuario) {
        if (this.estaBorradoBloqueado(usuario)) {
            return false;
        }

        if (usuario instanceof UsuarioSucursal) {
            return true;
        } else {
            return false;
        }
    }

    public boolean permisoSucursalComprador(Usuario usuario) {
        if (this.estaBorradoBloqueado(usuario)) {
            return false;
        }

        if (usuario instanceof UsuarioSucursal) {
            return true;
        } else if (usuario instanceof Comprador) {
            return true;
        } else {
            return false;
        }
    }

    public boolean permisoAdministradores(Usuario usuario) {
        if (this.estaBorradoBloqueado(usuario)) {
            return false;
        }

        if (usuario instanceof AdministradorSistema) {
            return true;
        } else if (usuario instanceof AdministradorSucursal) {
            return true;
        } else {
            return false;
        }
    }

    public boolean permisoEmpresa(Usuario usuario) {
        if (this.estaBorradoBloqueado(usuario)) {
            return false;
        }
        
        if (usuario instanceof AdministradorSistema) {
            return true;
        } else if (usuario instanceof UsuarioSucursal) {
            return true;
        } else {
            return false;
        }
    }

}
