package grupo1.supershop.interfaces.controladores;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtAdministradorSistema;
import grupo1.supershop.datatypes.DtAdministradorSucursal;
import grupo1.supershop.datatypes.DtAtencionCliente;
import grupo1.supershop.datatypes.DtComprador;
import grupo1.supershop.datatypes.DtMensajePush;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtSucursal;
import grupo1.supershop.datatypes.DtUsuario;
import grupo1.supershop.datatypes.DtUsuarioSucursal;
import java.util.List;

public interface IControladorUsuario {

    public DtUsuario obtenerUsuario(Long usuarioId, String secreto);

    public String obtenerPerfilUsuario(Long usuarioId, String secreto);

    public DtUsuarioSucursal obtenerUsuarioSucursal(Long usuarioId, String secreto);

    public DtUsuario obtenerMisDatos(String secreto);

    public DtSucursal obtenerMiSucursal(String secreto);

    public List<DtAdministradorSistema> listarAdministradoresSistema(String secreto);

    public List<DtAdministradorSistema> listarAdministradoresSistemaBorrados(String secreto);

    public List<DtAdministradorSucursal> listarAdministradoresSucursal(String secreto);

    public List<DtAdministradorSucursal> listarAdministradoresSucursalBorrados(String secreto);

    public List<DtAtencionCliente> listarAtencionCliente(String secreto);

    public List<DtAtencionCliente> listarAtencionClienteBorrados(String secreto);

    public List<DtComprador> listarCompradores(String secreto);

    public List<DtComprador> listarCompradoresBorrados(String secreto);

    public List<DtComprador> listarCompradoresBloqueados(String secreto);
    
    public List<DtActividad> listarActividadUsuario(Long usuarioId, String secreto);

    public DtMensajeSistema registrarComprador(DtComprador dtComprador);

    public DtMensajeSistema crearAdministradorSistema(DtAdministradorSistema dtAdministradorSistema, String secreto);

    public DtMensajeSistema crearAdministradorSucursal(DtAdministradorSucursal dtAdministradorSucursal, Long sucursalId, String secreto);

    public DtMensajeSistema crearAtencionCliente(DtAtencionCliente dtAtencionCliente, Long sucursalId, String secreto);

    public DtMensajeSistema modificarUsuario(DtUsuario dtUsuario, String secreto);

    public DtMensajeSistema modificarUsuarioSucursal(DtUsuarioSucursal dtUsuarioSucursal, String secreto);

    public DtMensajeSistema borrarMiUsuario(String secreto);

    public DtMensajeSistema borrarUsuario(Long usuarioId, String secreto);

    public DtMensajeSistema restaurarUsuario(Long usuarioId, String secreto);

    public DtMensajeSistema bloquearUsuario(Long usuarioId, String secreto);

    public DtMensajeSistema desbloquearUsuario(Long usuarioId, String secreto);

    public DtMensajeSistema modificarMisDatos(DtUsuario dtUsuario, String secreto);

    public DtMensajeSistema modificarMiClave(String passwordActual, String passwordNuevo, String secreto);

    public DtMensajeSistema recuperarPassword(String email);
    
    public DtMensajeSistema modificarPushToken(String token, String secreto);
    
    public DtMensajeSistema enviarNotificacionPush(DtMensajePush dtMensajePush, String secreto);

}
