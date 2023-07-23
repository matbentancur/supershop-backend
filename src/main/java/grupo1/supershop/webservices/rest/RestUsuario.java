package grupo1.supershop.webservices.rest;

import grupo1.supershop.datatypes.*;
import grupo1.supershop.fabricas.FabricaUsuario;
import grupo1.supershop.interfaces.controladores.IControladorUsuario;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/rest/usuario")
public class RestUsuario {

    private IControladorUsuario iControladorUsuario;

    public RestUsuario() {
        this.iControladorUsuario = FabricaUsuario.getIControladorUsuario();
    }

    @GetMapping("/test")
    public String test() {
        return "TEST OK";
    }

    @GetMapping("/obtenerUsuario/{usuarioId}/{secreto}")
    public DtUsuario obtenerUsuario(@PathVariable("usuarioId") Long usuarioId, @PathVariable("secreto") String secreto) {
        return iControladorUsuario.obtenerUsuario(usuarioId, secreto);
    }

    @GetMapping("/obtenerPerfilUsuario/{usuarioId}/{secreto}")
    public String obtenerPerfilUsuario(@PathVariable("usuarioId") Long usuarioId, @PathVariable("secreto") String secreto) {
        return iControladorUsuario.obtenerPerfilUsuario(usuarioId, secreto);
    }

    @GetMapping("/obtenerUsuarioSucursal/{usuarioId}/{secreto}")
    public DtUsuario obtenerUsuarioSucursal(@PathVariable("usuarioId") Long usuarioId, @PathVariable("secreto") String secreto) {
        return iControladorUsuario.obtenerUsuarioSucursal(usuarioId, secreto);
    }

    @GetMapping("/obtenerMisDatos/{secreto}")
    public DtUsuario obtenerMisDatos(@PathVariable("secreto") String secreto) {
        return iControladorUsuario.obtenerMisDatos(secreto);
    }

    @GetMapping("/obtenerMiSucursal/{secreto}")
    public DtSucursal obtenerMiSucursal(@PathVariable("secreto") String secreto) {
        return iControladorUsuario.obtenerMiSucursal(secreto);
    }

    @GetMapping("/listarAdministradoresSistema/{secreto}")
    public List<DtAdministradorSistema> listarAdministradoresSistema(@PathVariable("secreto") String secreto) {
        return iControladorUsuario.listarAdministradoresSistema(secreto);
    }

    @GetMapping("/listarAdministradoresSistemaBorrados/{secreto}")
    public List<DtAdministradorSistema> listarAdministradoresSistemaBorrados(@PathVariable("secreto") String secreto) {
        return iControladorUsuario.listarAdministradoresSistemaBorrados(secreto);
    }

    @GetMapping("/listarAdministradoresSucursal/{secreto}")
    public List<DtAdministradorSucursal> listarAdministradoresSucursal(@PathVariable("secreto") String secreto) {
        return iControladorUsuario.listarAdministradoresSucursal(secreto);
    }

    @GetMapping("/listarAdministradoresSucursalBorrados/{secreto}")
    public List<DtAdministradorSucursal> listarAdministradoresSucursalBorrados(@PathVariable("secreto") String secreto) {
        return iControladorUsuario.listarAdministradoresSucursalBorrados(secreto);
    }

    @GetMapping("/listarAtencionCliente/{secreto}")
    public List<DtAtencionCliente> listarAtencionCliente(@PathVariable("secreto") String secreto) {
        return iControladorUsuario.listarAtencionCliente(secreto);
    }

    @GetMapping("/listarAtencionClienteBorrados/{secreto}")
    public List<DtAtencionCliente> listarAtencionClienteBorrados(@PathVariable("secreto") String secreto) {
        return iControladorUsuario.listarAtencionClienteBorrados(secreto);
    }

    @GetMapping("/listarCompradores/{secreto}")
    public List<DtComprador> listarCompradores(@PathVariable("secreto") String secreto) {
        return iControladorUsuario.listarCompradores(secreto);
    }

    @GetMapping("/listarCompradoresBorrados/{secreto}")
    public List<DtComprador> listarCompradoresBorrados(@PathVariable("secreto") String secreto) {
        return iControladorUsuario.listarCompradoresBorrados(secreto);
    }

    @GetMapping("/listarCompradoresBloqueados/{secreto}")
    public List<DtComprador> listarCompradoresBloqueados(@PathVariable("secreto") String secreto) {
        return iControladorUsuario.listarCompradoresBloqueados(secreto);
    }

    @GetMapping("/listarActividadUsuario/{usuarioId}/{secreto}")
    public List<DtActividad> listarActividadUsuario(@PathVariable("usuarioId") Long usuarioId, @PathVariable("secreto") String secreto) {
        return iControladorUsuario.listarActividadUsuario(usuarioId, secreto);
    }

    @PostMapping("/registrarComprador")
    public DtMensajeSistema registrarComprador(@RequestBody DtComprador dtComprador) {
        return iControladorUsuario.registrarComprador(dtComprador);
    }

    @PostMapping("/crearAdministradorSistema/{secreto}")
    public DtMensajeSistema crearAdministradorSistema(@RequestBody DtAdministradorSistema dtAdministradorSistema, @PathVariable("secreto") String secreto) {
        return iControladorUsuario.crearAdministradorSistema(dtAdministradorSistema, secreto);
    }

    @PostMapping("/crearAdministradorSucursal/{sucursalId}/{secreto}")
    public DtMensajeSistema crearAdministradorSucursal(@RequestBody DtAdministradorSucursal dtAdministradorSucursal, @PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorUsuario.crearAdministradorSucursal(dtAdministradorSucursal, sucursalId, secreto);
    }

    @PostMapping("/crearAtencionCliente/{sucursalId}/{secreto}")
    public DtMensajeSistema crearAtencionCliente(@RequestBody DtAtencionCliente dtAtencionCliente, @PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorUsuario.crearAtencionCliente(dtAtencionCliente, sucursalId, secreto);
    }

    @PostMapping("/modificarUsuario/{secreto}")
    public DtMensajeSistema modificarUsuario(@RequestBody DtUsuario dtUsuario, @PathVariable("secreto") String secreto) {
        return iControladorUsuario.modificarUsuario(dtUsuario, secreto);
    }

    @PostMapping("/modificarUsuarioSucursal/{secreto}")
    public DtMensajeSistema modificarUsuarioSucursal(@RequestBody DtUsuarioSucursal dtUsuarioSucursal, @PathVariable("secreto") String secreto) {
        return iControladorUsuario.modificarUsuarioSucursal(dtUsuarioSucursal, secreto);
    }

    @DeleteMapping("/borrarMiUsuario/{secreto}")
    public DtMensajeSistema borrarMiUsuario(@PathVariable("secreto") String secreto) {
        return iControladorUsuario.borrarMiUsuario(secreto);
    }

    @DeleteMapping("/borrar/{usuarioId}/{secreto}")
    public DtMensajeSistema borrarUsuario(@PathVariable("usuarioId") Long usuarioId, @PathVariable("secreto") String secreto) {
        return iControladorUsuario.borrarUsuario(usuarioId, secreto);
    }

    @PutMapping("/restaurar/{usuarioId}/{secreto}")
    public DtMensajeSistema restaurarUsuario(@PathVariable("usuarioId") Long usuarioId, @PathVariable("secreto") String secreto) {
        return iControladorUsuario.restaurarUsuario(usuarioId, secreto);
    }

    @PutMapping("/bloquear/{usuarioId}/{secreto}")
    public DtMensajeSistema bloquearUsuario(@PathVariable("usuarioId") Long usuarioId, @PathVariable("secreto") String secreto) {
        return iControladorUsuario.bloquearUsuario(usuarioId, secreto);
    }

    @PutMapping("/desbloquear/{usuarioId}/{secreto}")
    public DtMensajeSistema desbloquearUsuario(@PathVariable("usuarioId") Long usuarioId, @PathVariable("secreto") String secreto) {
        return iControladorUsuario.desbloquearUsuario(usuarioId, secreto);
    }

    @PostMapping("/modificarMisDatos/{secreto}")
    public DtMensajeSistema modificarMisDatos(@RequestBody DtUsuario dtUsuario, @PathVariable("secreto") String secreto) {
        return iControladorUsuario.modificarMisDatos(dtUsuario, secreto);
    }

    @PutMapping("/modificarMiClave/{passwordActual}/{passwordNuevo}/{secreto}")
    public DtMensajeSistema modificarMiClave(@PathVariable("passwordActual") String passwordActual, @PathVariable("passwordNuevo") String passwordNuevo, @PathVariable("secreto") String secreto) {
        return iControladorUsuario.modificarMiClave(passwordActual, passwordNuevo, secreto);
    }

    @PutMapping("/recuperarPassword/{email}")
    public DtMensajeSistema recuperarPassword(@PathVariable("email") String email) {
        return iControladorUsuario.recuperarPassword(email);
    }
    
    @PutMapping("/modificarPushToken/{token}/{secreto}")
    public DtMensajeSistema modificarPushToken(@PathVariable("token") String token, @PathVariable("secreto") String secreto) {
        return iControladorUsuario.modificarPushToken(token, secreto);
    }

    @PostMapping("/enviarNotificacionPush/{secreto}")
    public DtMensajeSistema enviarNotificacionPush(@RequestBody DtMensajePush dtMensajePush, @PathVariable("secreto") String secreto) {
        return iControladorUsuario.enviarNotificacionPush(dtMensajePush, secreto);
    }

}
