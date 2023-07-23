package grupo1.supershop.servicios;

import grupo1.supershop.beans.AdministradorSistema;
import grupo1.supershop.beans.AdministradorSucursal;
import grupo1.supershop.beans.AtencionCliente;
import grupo1.supershop.beans.Compra;
import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Conversacion;
import grupo1.supershop.beans.Domicilio;
import grupo1.supershop.beans.Reclamo;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.beans.UsuarioSucursal;
import grupo1.supershop.beans.Vale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioUsuario {

    private static ServicioUsuario instance = null;

    private ServicioUsuario() {
    }

    public static ServicioUsuario getInstance() {
        if (instance == null) {
            instance = new ServicioUsuario();
        }
        return instance;
    }

    public String obtenerPerfil(Usuario usuario) {
        try {
            if (usuario instanceof AdministradorSistema) {
                return "AdministradorSistema";
            } else if (usuario instanceof AdministradorSucursal) {
                return "AdministradorSucursal";
            } else if (usuario instanceof AtencionCliente) {
                return "AtencionCliente";
            } else if (usuario instanceof Comprador) {
                return "Comprador";
            } else {
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(ServicioUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean perteneceSusursal(UsuarioSucursal usuarioSucursal, Sucursal sucursal) {
        if (usuarioSucursal != null && sucursal != null) {
            if (sucursal.equals(usuarioSucursal.getSucursal())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean esPropietarioDomicilio(Comprador comprador, Domicilio domicilio) {
        if (comprador != null && domicilio != null) {
            if (domicilio.getComprador().equals(comprador)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean esPropietarioCompra(Comprador comprador, Compra compra) {
        if (comprador != null && compra != null) {
            if (compra.getComprador().equals(comprador)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean esPropietarioConversacion(Usuario usuario, Conversacion conversacion) {
        if (usuario != null && conversacion != null) {
            if (usuario instanceof Comprador) {
                if (conversacion.getComprador().equals(usuario)) {
                    return true;
                } else {
                    return false;
                }
            } else if (usuario instanceof UsuarioSucursal) {
                UsuarioSucursal usuarioSucursal = (UsuarioSucursal) usuario;
                if (conversacion.getSucursal().equals(usuarioSucursal.getSucursal())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean esPropietarioReclamo(Usuario usuario, Reclamo reclamo) {
        if (usuario != null && reclamo != null) {
            if (usuario instanceof Comprador) {
                if (reclamo.getComprador().equals(usuario)) {
                    return true;
                } else {
                    return false;
                }
            } else if (usuario instanceof UsuarioSucursal) {
                UsuarioSucursal usuarioSucursal = (UsuarioSucursal) usuario;
                if (reclamo.getSucursal().equals(usuarioSucursal.getSucursal())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean esPropietarioVale(Usuario usuario, Vale vale) {
        if (usuario != null && vale != null) {
            if (usuario instanceof Comprador) {
                if (vale.getComprador().equals(usuario)) {
                    return true;
                } else {
                    return false;
                }
            } else if (usuario instanceof UsuarioSucursal) {
                UsuarioSucursal usuarioSucursal = (UsuarioSucursal) usuario;
                if (vale.getSucursal().equals(usuarioSucursal.getSucursal())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
