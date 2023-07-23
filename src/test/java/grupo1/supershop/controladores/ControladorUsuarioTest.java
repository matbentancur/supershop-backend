package grupo1.supershop.controladores;

import grupo1.supershop.beans.Usuario;
import grupo1.supershop.config.properties.PropertyReader;
import grupo1.supershop.config.report.listenertest.ListenerTest;
import grupo1.supershop.config.servicios.ServicioIniciarSesion;
import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.*;
import grupo1.supershop.fabricas.FabricaUsuario;
import grupo1.supershop.instalar.InstalarSistema;
import grupo1.supershop.interfaces.controladores.IControladorUsuario;
import grupo1.supershop.persistencia.manejadores.ManejadorUsuario;
import grupo1.supershop.servicios.ServicioFechaHora;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ListenerTest.class)
class ControladorUsuarioTest {
    private static IControladorUsuario iControladorUsuario;

    @BeforeAll
    static void configuracion() throws Exception {
        iControladorUsuario = FabricaUsuario.getIControladorUsuario();
        new InstalarSistema().instalar();
    }

    @Test
    @DisplayName("Obtener usuario")
    void obtenerUsuario() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String id = PropertyReader.getInstance().getTestValue("ControladorUsuario.obtenerUsuario.id");
        String emailUsuario = PropertyReader.getInstance().getTestValue("ControladorUsuario.obtenerUsuario.emailUsuario");
        String nombres = PropertyReader.getInstance().getTestValue("ControladorUsuario.obtenerUsuario.nombres");
        String apellidos = PropertyReader.getInstance().getTestValue("ControladorUsuario.obtenerUsuario.apellidos");
        String celular = PropertyReader.getInstance().getTestValue("ControladorUsuario.obtenerUsuario.celular");

        DtUsuario dtUsuario = iControladorUsuario.obtenerUsuario(Long.parseLong(id), secreto);

        ServicioVerificacion.getInstance().verificar(Long.parseLong(id), dtUsuario.getId());
        ServicioVerificacion.getInstance().verificar(emailUsuario, dtUsuario.getEmail());
        ServicioVerificacion.getInstance().verificar(null, dtUsuario.getPassword());
        ServicioVerificacion.getInstance().verificar(nombres, dtUsuario.getNombres());
        ServicioVerificacion.getInstance().verificar(apellidos, dtUsuario.getApellidos());
        ServicioVerificacion.getInstance().verificar(celular, dtUsuario.getCelular());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Registrar comprador")
    void registrarComprador() {
        String emailUsuario = PropertyReader.getInstance().getTestValue("ControladorUsuario.registrarComprador.emailUsuario");
        String password = PropertyReader.getInstance().getTestValue("ControladorUsuario.registrarComprador.password");
        String nombres = PropertyReader.getInstance().getTestValue("ControladorUsuario.registrarComprador.nombres");
        String apellidos = PropertyReader.getInstance().getTestValue("ControladorUsuario.registrarComprador.apellidos");
        String celular = PropertyReader.getInstance().getTestValue("ControladorUsuario.registrarComprador.celular");
        String fecha = PropertyReader.getInstance().getTestValue("ControladorUsuario.registrarComprador.fecha");

        DtComprador dtComprador = new DtComprador();
        dtComprador.setEmail(emailUsuario);
        dtComprador.setPassword(password);
        dtComprador.setNombres(nombres);
        dtComprador.setApellidos(apellidos);
        dtComprador.setCelular(celular);
        dtComprador.setNacimiento(ServicioFechaHora.getInstance().stringToDate(fecha));

        iControladorUsuario.registrarComprador(dtComprador);

        Usuario usuario = ManejadorUsuario.getInstance().obtenerUsuario("email", emailUsuario);
        DtComprador dtPosComprador = (DtComprador) usuario.getDataType();

        ServicioVerificacion.getInstance().verificar(emailUsuario, dtPosComprador.getEmail());
        ServicioVerificacion.getInstance().verificar(null, dtPosComprador.getPassword());
        ServicioVerificacion.getInstance().verificar(nombres, dtPosComprador.getNombres());
        ServicioVerificacion.getInstance().verificar(apellidos, dtPosComprador.getApellidos());
        ServicioVerificacion.getInstance().verificar(celular, dtPosComprador.getCelular());
        ServicioVerificacion.getInstance().verificar(ServicioFechaHora.getInstance().stringToDate(fecha), dtPosComprador.getNacimiento());
    }

    @Test
    @DisplayName("Crear administrador sistema")
    void crearAdministradorSistema() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String emailUsuario = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAdministradorSistema.emailUsuario");
        String password = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAdministradorSistema.password");
        String nombres = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAdministradorSistema.nombres");
        String apellidos = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAdministradorSistema.apellidos");
        String celular = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAdministradorSistema.celular");
        String fecha = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAdministradorSistema.fecha");

        DtAdministradorSistema dtAdministradorSistema = new DtAdministradorSistema();
        dtAdministradorSistema.setEmail(emailUsuario);
        dtAdministradorSistema.setPassword(password);
        dtAdministradorSistema.setNombres(nombres);
        dtAdministradorSistema.setApellidos(apellidos);
        dtAdministradorSistema.setCelular(celular);
        dtAdministradorSistema.setNacimiento(ServicioFechaHora.getInstance().stringToDate(fecha));

        iControladorUsuario.crearAdministradorSistema(dtAdministradorSistema, secreto);

        Usuario usuario = ManejadorUsuario.getInstance().obtenerUsuario("email", emailUsuario);
        DtAdministradorSistema dtPosAdministradorSistema = (DtAdministradorSistema) usuario.getDataType();

        ServicioVerificacion.getInstance().verificar(emailUsuario, dtPosAdministradorSistema.getEmail());
        ServicioVerificacion.getInstance().verificar(null, dtPosAdministradorSistema.getPassword());
        ServicioVerificacion.getInstance().verificar(nombres, dtPosAdministradorSistema.getNombres());
        ServicioVerificacion.getInstance().verificar(apellidos, dtPosAdministradorSistema.getApellidos());
        ServicioVerificacion.getInstance().verificar(celular, dtPosAdministradorSistema.getCelular());
        ServicioVerificacion.getInstance().verificar(ServicioFechaHora.getInstance().stringToDate(fecha), dtPosAdministradorSistema.getNacimiento());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Crear administrador sucursal")
    void crearAdministradorSucursal() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String emailUsuario = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAdministradorSucursal.emailUsuario");
        String password = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAdministradorSucursal.password");
        String nombres = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAdministradorSucursal.nombres");
        String apellidos = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAdministradorSucursal.apellidos");
        String celular = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAdministradorSucursal.celular");
        String fecha = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAdministradorSucursal.fecha");
        String idSucursal = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAdministradorSucursal.idSucursal");

        DtAdministradorSucursal dtAdministradorSucursal = new DtAdministradorSucursal();
        dtAdministradorSucursal.setEmail(emailUsuario);
        dtAdministradorSucursal.setPassword(password);
        dtAdministradorSucursal.setNombres(nombres);
        dtAdministradorSucursal.setApellidos(apellidos);
        dtAdministradorSucursal.setCelular(celular);
        dtAdministradorSucursal.setNacimiento(ServicioFechaHora.getInstance().stringToDate(fecha));

        iControladorUsuario.crearAdministradorSucursal(dtAdministradorSucursal, Long.parseLong(idSucursal), secreto);

        Usuario usuario = ManejadorUsuario.getInstance().obtenerUsuario("email", emailUsuario);
        DtAdministradorSucursal dtPosAdministradorSucursal = (DtAdministradorSucursal) usuario.getDataType();

        ServicioVerificacion.getInstance().verificar(emailUsuario, dtPosAdministradorSucursal.getEmail());
        ServicioVerificacion.getInstance().verificar(null, dtPosAdministradorSucursal.getPassword());
        ServicioVerificacion.getInstance().verificar(nombres, dtPosAdministradorSucursal.getNombres());
        ServicioVerificacion.getInstance().verificar(apellidos, dtPosAdministradorSucursal.getApellidos());
        ServicioVerificacion.getInstance().verificar(celular, dtPosAdministradorSucursal.getCelular());
        ServicioVerificacion.getInstance().verificar(ServicioFechaHora.getInstance().stringToDate(fecha), dtPosAdministradorSucursal.getNacimiento());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(idSucursal), dtPosAdministradorSucursal.getDtSucursal().getId());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Crear atencion cliente")
    void crearAtencionCliente() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String emailUsuario = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAtencionCliente.emailUsuario");
        String password = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAtencionCliente.password");
        String nombres = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAtencionCliente.nombres");
        String apellidos = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAtencionCliente.apellidos");
        String celular = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAtencionCliente.celular");
        String fecha = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAtencionCliente.fecha");
        String idSucursal = PropertyReader.getInstance().getTestValue("ControladorUsuario.crearAtencionCliente.idSucursal");

        DtAtencionCliente dtAtencionCliente = new DtAtencionCliente();
        dtAtencionCliente.setEmail(emailUsuario);
        dtAtencionCliente.setPassword(password);
        dtAtencionCliente.setNombres(nombres);
        dtAtencionCliente.setApellidos(apellidos);
        dtAtencionCliente.setCelular(celular);
        dtAtencionCliente.setNacimiento(ServicioFechaHora.getInstance().stringToDate(fecha));

        iControladorUsuario.crearAtencionCliente(dtAtencionCliente, Long.parseLong(idSucursal), secreto);

        Usuario usuario = ManejadorUsuario.getInstance().obtenerUsuario("email", emailUsuario);
        DtAtencionCliente dtPosAtencionCliente = (DtAtencionCliente) usuario.getDataType();

        ServicioVerificacion.getInstance().verificar(emailUsuario, dtPosAtencionCliente.getEmail());
        ServicioVerificacion.getInstance().verificar(null, dtPosAtencionCliente.getPassword());
        ServicioVerificacion.getInstance().verificar(nombres, dtPosAtencionCliente.getNombres());
        ServicioVerificacion.getInstance().verificar(apellidos, dtPosAtencionCliente.getApellidos());
        ServicioVerificacion.getInstance().verificar(celular, dtPosAtencionCliente.getCelular());
        ServicioVerificacion.getInstance().verificar(ServicioFechaHora.getInstance().stringToDate(fecha), dtPosAtencionCliente.getNacimiento());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(idSucursal), dtPosAtencionCliente.getDtSucursal().getId());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Modificar usuario")
    void modificarUsuario() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String emailUsuario = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuario.emailUsuario");
        String password = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuario.password");
        String nombres = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuario.nombres");
        String apellidos = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuario.apellidos");
        String celular = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuario.celular");
        String fecha = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuario.fecha");
        String emailUsuarioNuevo = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuario.emailUsuarioNuevo");
        String passwordNuevo = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuario.passwordNuevo");
        String nombresNuevo = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuario.nombresNuevo");
        String apellidosNuevo = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuario.apellidosNuevo");
        String celularNuevo = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuario.celularNuevo");
        String fechaNuevo = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuario.fechaNuevo");

        DtAdministradorSistema dtAdministradorSistema = new DtAdministradorSistema();
        dtAdministradorSistema.setEmail(emailUsuario);
        dtAdministradorSistema.setPassword(password);
        dtAdministradorSistema.setNombres(nombres);
        dtAdministradorSistema.setApellidos(apellidos);
        dtAdministradorSistema.setCelular(celular);
        dtAdministradorSistema.setNacimiento(ServicioFechaHora.getInstance().stringToDate(fecha));

        iControladorUsuario.crearAdministradorSistema(dtAdministradorSistema, secreto);

        Usuario usuario = ManejadorUsuario.getInstance().obtenerUsuario("email", emailUsuario);
        DtUsuario dtPosUsuario = (DtUsuario) usuario.getDataType();

        dtPosUsuario.setEmail(emailUsuarioNuevo);
        dtPosUsuario.setPassword(passwordNuevo);
        dtPosUsuario.setNombres(nombresNuevo);
        dtPosUsuario.setApellidos(apellidosNuevo);
        dtPosUsuario.setCelular(celularNuevo);
        dtPosUsuario.setNacimiento(ServicioFechaHora.getInstance().stringToDate(fechaNuevo));

        iControladorUsuario.modificarUsuario(dtPosUsuario, secreto);

        usuario = ManejadorUsuario.getInstance().obtenerUsuario("email", emailUsuarioNuevo);
        dtPosUsuario = (DtUsuario) usuario.getDataType();

        ServicioVerificacion.getInstance().verificar(emailUsuarioNuevo, dtPosUsuario.getEmail());
        ServicioVerificacion.getInstance().verificar(null, dtPosUsuario.getPassword());
        ServicioVerificacion.getInstance().verificar(nombresNuevo, dtPosUsuario.getNombres());
        ServicioVerificacion.getInstance().verificar(apellidosNuevo, dtPosUsuario.getApellidos());
        ServicioVerificacion.getInstance().verificar(celularNuevo, dtPosUsuario.getCelular());
        ServicioVerificacion.getInstance().verificar(ServicioFechaHora.getInstance().stringToDate(fechaNuevo), dtPosUsuario.getNacimiento());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Modificar usuario sucursal")
    void modificarUsuarioSucursal() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String emailUsuario = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuarioSucursal.emailUsuario");
        String password = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuarioSucursal.password");
        String nombres = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuarioSucursal.nombres");
        String apellidos = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuarioSucursal.apellidos");
        String celular = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuarioSucursal.celular");
        String fecha = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuarioSucursal.fecha");
        String emailUsuarioNuevo = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuarioSucursal.emailUsuarioNuevo");
        String passwordNuevo = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuarioSucursal.passwordNuevo");
        String nombresNuevo = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuarioSucursal.nombresNuevo");
        String apellidosNuevo = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuarioSucursal.apellidosNuevo");
        String celularNuevo = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuarioSucursal.celularNuevo");
        String fechaNuevo = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuarioSucursal.fechaNuevo");
        String idSucursal = PropertyReader.getInstance().getTestValue("ControladorUsuario.modificarUsuarioSucursal.idSucursal");

        DtAdministradorSucursal dtAdministradorSucursal = new DtAdministradorSucursal();
        dtAdministradorSucursal.setEmail(emailUsuario);
        dtAdministradorSucursal.setPassword(password);
        dtAdministradorSucursal.setNombres(nombres);
        dtAdministradorSucursal.setApellidos(apellidos);
        dtAdministradorSucursal.setCelular(celular);
        dtAdministradorSucursal.setNacimiento(ServicioFechaHora.getInstance().stringToDate(fecha));

        iControladorUsuario.crearAdministradorSucursal(dtAdministradorSucursal, Long.parseLong(idSucursal), secreto);

        Usuario usuario = ManejadorUsuario.getInstance().obtenerUsuario("email", emailUsuario);
        DtUsuarioSucursal dtPosUsuario = (DtUsuarioSucursal) usuario.getDataType();

        dtPosUsuario.setEmail(emailUsuarioNuevo);
        dtPosUsuario.setPassword(passwordNuevo);
        dtPosUsuario.setNombres(nombresNuevo);
        dtPosUsuario.setApellidos(apellidosNuevo);
        dtPosUsuario.setCelular(celularNuevo);
        dtPosUsuario.setNacimiento(ServicioFechaHora.getInstance().stringToDate(fechaNuevo));

        iControladorUsuario.modificarUsuarioSucursal(dtPosUsuario, secreto);

        usuario = ManejadorUsuario.getInstance().obtenerUsuario("email", emailUsuarioNuevo);
        dtPosUsuario = (DtUsuarioSucursal) usuario.getDataType();

        ServicioVerificacion.getInstance().verificar(emailUsuarioNuevo, dtPosUsuario.getEmail());
        ServicioVerificacion.getInstance().verificar(null, dtPosUsuario.getPassword());
        ServicioVerificacion.getInstance().verificar(nombresNuevo, dtPosUsuario.getNombres());
        ServicioVerificacion.getInstance().verificar(apellidosNuevo, dtPosUsuario.getApellidos());
        ServicioVerificacion.getInstance().verificar(celularNuevo, dtPosUsuario.getCelular());
        ServicioVerificacion.getInstance().verificar(ServicioFechaHora.getInstance().stringToDate(fechaNuevo), dtPosUsuario.getNacimiento());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Borrar usuario")
    void borrarUsuario() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String emailUsuario = PropertyReader.getInstance().getTestValue("ControladorUsuario.borrarUsuario.emailUsuario");
        String password = PropertyReader.getInstance().getTestValue("ControladorUsuario.borrarUsuario.password");
        String nombres = PropertyReader.getInstance().getTestValue("ControladorUsuario.borrarUsuario.nombres");
        String apellidos = PropertyReader.getInstance().getTestValue("ControladorUsuario.borrarUsuario.apellidos");
        String celular = PropertyReader.getInstance().getTestValue("ControladorUsuario.borrarUsuario.celular");
        String fecha = PropertyReader.getInstance().getTestValue("ControladorUsuario.borrarUsuario.fecha");


        DtAdministradorSistema dtAdministradorSistema = new DtAdministradorSistema();
        dtAdministradorSistema.setEmail(emailUsuario);
        dtAdministradorSistema.setPassword(password);
        dtAdministradorSistema.setNombres(nombres);
        dtAdministradorSistema.setApellidos(apellidos);
        dtAdministradorSistema.setCelular(celular);
        dtAdministradorSistema.setNacimiento(ServicioFechaHora.getInstance().stringToDate(fecha));

        iControladorUsuario.crearAdministradorSistema(dtAdministradorSistema, secreto);

        Usuario usuario = ManejadorUsuario.getInstance().obtenerUsuario("email", emailUsuario);
        DtUsuario dtPosUsuario = (DtUsuario) usuario.getDataType();

        iControladorUsuario.borrarUsuario(dtPosUsuario.getId(), secreto);

        usuario = ManejadorUsuario.getInstance().obtenerUsuario("email", emailUsuario);
        dtPosUsuario = (DtUsuario) usuario.getDataType();

        ServicioVerificacion.getInstance().verificarTrue( dtPosUsuario.isBorrado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Bloquear usuario")
    void bloquearUsuario() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String emailUsuario = PropertyReader.getInstance().getTestValue("ControladorUsuario.bloquearUsuario.emailUsuario");
        String password = PropertyReader.getInstance().getTestValue("ControladorUsuario.bloquearUsuario.password");
        String nombres = PropertyReader.getInstance().getTestValue("ControladorUsuario.bloquearUsuario.nombres");
        String apellidos = PropertyReader.getInstance().getTestValue("ControladorUsuario.bloquearUsuario.apellidos");
        String celular = PropertyReader.getInstance().getTestValue("ControladorUsuario.bloquearUsuario.celular");
        String fecha = PropertyReader.getInstance().getTestValue("ControladorUsuario.bloquearUsuario.fecha");


        DtAdministradorSistema dtAdministradorSistema = new DtAdministradorSistema();
        dtAdministradorSistema.setEmail(emailUsuario);
        dtAdministradorSistema.setPassword(password);
        dtAdministradorSistema.setNombres(nombres);
        dtAdministradorSistema.setApellidos(apellidos);
        dtAdministradorSistema.setCelular(celular);
        dtAdministradorSistema.setNacimiento(ServicioFechaHora.getInstance().stringToDate(fecha));

        iControladorUsuario.crearAdministradorSistema(dtAdministradorSistema, secreto);

        Usuario usuario = ManejadorUsuario.getInstance().obtenerUsuario("email", emailUsuario);
        DtUsuario dtPosUsuario = (DtUsuario) usuario.getDataType();

        iControladorUsuario.bloquearUsuario(dtPosUsuario.getId(), secreto);

        usuario = ManejadorUsuario.getInstance().obtenerUsuario("email", emailUsuario);
        dtPosUsuario = (DtUsuario) usuario.getDataType();

        ServicioVerificacion.getInstance().verificarTrue( dtPosUsuario.isBloqueado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }
}