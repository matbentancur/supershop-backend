package grupo1.supershop.controladores;

import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.config.properties.PropertyReader;
import grupo1.supershop.config.report.listenertest.ListenerTest;
import grupo1.supershop.config.servicios.ServicioIniciarSesion;
import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtSucursal;
import grupo1.supershop.fabricas.FabricaSucursal;
import grupo1.supershop.instalar.InstalarSistema;
import grupo1.supershop.interfaces.controladores.IControladorSucursal;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith(ListenerTest.class)
class ControladorSucursalTest {

    private static IControladorSucursal iControladorSucursal;

    @BeforeAll
    static void configuracion() throws Exception {
        iControladorSucursal = FabricaSucursal.getIControladorSucursal();
        new InstalarSistema().instalar();
    }

    @Test
    @DisplayName("Obtener sucursal")
    void obtenerSucursal() {
        String sucursalId = PropertyReader.getInstance().getTestValue("ControladorSucursal.obtenerSucursal.sucursalId");
        String nombre = PropertyReader.getInstance().getTestValue("ControladorSucursal.obtenerSucursal.nombre");
        String borrado = PropertyReader.getInstance().getTestValue("ControladorSucursal.obtenerSucursal.borrado");
        String email = PropertyReader.getInstance().getTestValue("ControladorSucursal.obtenerSucursal.email");
        String horario = PropertyReader.getInstance().getTestValue("ControladorSucursal.obtenerSucursal.horario");
        String telefono = PropertyReader.getInstance().getTestValue("ControladorSucursal.obtenerSucursal.telefono");
        String imagenRemota = PropertyReader.getInstance().getTestValue("ControladorSucursal.obtenerSucursal.imagenRemota");

        DtSucursal dtSucursal = iControladorSucursal.obtenerSucursal(Long.parseLong(sucursalId));

        ServicioVerificacion.getInstance().verificar(nombre, dtSucursal.getNombre());
        ServicioVerificacion.getInstance().verificar(Boolean.valueOf(borrado), dtSucursal.isBorrado());
        ServicioVerificacion.getInstance().verificar(email, dtSucursal.getEmail());
        ServicioVerificacion.getInstance().verificar(horario, dtSucursal.getHorario());
        ServicioVerificacion.getInstance().verificar(telefono, dtSucursal.getTelefono());
        ServicioVerificacion.getInstance().verificar(imagenRemota, dtSucursal.getImagenRemota());
    }

    @Test
    @DisplayName("Obtener sucursal nombre")
    void obtenerSucursalNombre() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String id = PropertyReader.getInstance().getTestValue("ControladorSucursal.obtenerSucursalNombre.id");
        String nombre = PropertyReader.getInstance().getTestValue("ControladorSucursal.obtenerSucursalNombre.nombre");
        String borrado = PropertyReader.getInstance().getTestValue("ControladorSucursal.obtenerSucursalNombre.borrado");
        String emailSucursal = PropertyReader.getInstance().getTestValue("ControladorSucursal.obtenerSucursalNombre.sucursalEmail");
        String horario = PropertyReader.getInstance().getTestValue("ControladorSucursal.obtenerSucursalNombre.horario");
        String telefono = PropertyReader.getInstance().getTestValue("ControladorSucursal.obtenerSucursalNombre.telefono");
        String imagenRemota = PropertyReader.getInstance().getTestValue("ControladorSucursal.obtenerSucursalNombre.imagenRemota");

        DtSucursal dtSucursal = iControladorSucursal.obtenerSucursalNombre(nombre, secreto);

        ServicioVerificacion.getInstance().verificar(Long.parseLong(id), dtSucursal.getId());
        ServicioVerificacion.getInstance().verificar(nombre, dtSucursal.getNombre());
        ServicioVerificacion.getInstance().verificar(Boolean.valueOf(borrado), dtSucursal.isBorrado());
        ServicioVerificacion.getInstance().verificar(emailSucursal, dtSucursal.getEmail());
        ServicioVerificacion.getInstance().verificar(horario, dtSucursal.getHorario());
        ServicioVerificacion.getInstance().verificar(telefono, dtSucursal.getTelefono());
        ServicioVerificacion.getInstance().verificar(imagenRemota, dtSucursal.getImagenRemota());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Listar sucursales")
    void listarSucursales() {
        String[] nombreSucursales = PropertyReader.getInstance().getTestValue("ControladorSucursal.listarSucursales.nombreSucursales").split(",");
        List<DtSucursal> dtSucursales = iControladorSucursal.listarSucursales();

        ServicioVerificacion.getInstance().verificarTrue(dtSucursales.size() > 0);

        for (String nombreSucursal : nombreSucursales) {
            boolean existe = false;
            for (DtSucursal dtSucursal : dtSucursales) {
                if (dtSucursal.getNombre().equals(nombreSucursal)) {
                    existe = true;
                    break;
                }
            }
            ServicioVerificacion.getInstance().verificarTrue(existe);
        }
    }

    @Test
    @DisplayName("Listar sucursales borradas")
    void listarSucursalesBorradas() throws Exception {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);
        List<DtSucursal> dtSucursalesBorradas;
        String nombre = PropertyReader.getInstance().getTestValue("ControladorSucursal.listarSucursalesBorradas.nombre");
        String emailSucursal = PropertyReader.getInstance().getTestValue("ControladorSucursal.listarSucursalesBorradas.emailSucursal");
        String horario = PropertyReader.getInstance().getTestValue("ControladorSucursal.listarSucursalesBorradas.horario");
        String telefono = PropertyReader.getInstance().getTestValue("ControladorSucursal.listarSucursalesBorradas.telefono");
        String imagenRemota = PropertyReader.getInstance().getTestValue("ControladorSucursal.listarSucursalesBorradas.imagenRemota");

        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(nombre);
        sucursal.setBorrado(true);
        sucursal.setEmail(emailSucursal);
        sucursal.setHorario(horario);
        sucursal.setTelefono(telefono);
        sucursal.setImagenRemota(imagenRemota);

        ManejadorSucursal.getInstance().crearSucursal(sucursal);

        dtSucursalesBorradas = iControladorSucursal.listarSucursalesBorradas(secreto);
        boolean borrado = false;

        for (DtSucursal dtSucursal : dtSucursalesBorradas)
            if (dtSucursal.getNombre().equals(nombre)) {
                borrado = true;
                break;
            }

        ServicioVerificacion.getInstance().verificarTrue(borrado);

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Listar actividad sucursal")
    void listarActividadSucursal() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);
        String id = PropertyReader.getInstance().getTestValue("ControladorSucursal.listarActividadSucursal.id");
        String descripcion = PropertyReader.getInstance().getTestValue("ControladorSucursal.listarActividadSucursal.descripcion");
        List<DtActividad> listaDtActividad = iControladorSucursal.listarActividadSucursal(Long.parseLong(id), secreto);

        ServicioVerificacion.getInstance().verificar(descripcion, listaDtActividad.get(0).getDescripcion());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Crear sucursal")
    void crearSucursal() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);
        String nombre = PropertyReader.getInstance().getTestValue("ControladorSucursal.crearSucursal.nombre");
        String emailSucursal = PropertyReader.getInstance().getTestValue("ControladorSucursal.crearSucursal.emailSucursal");
        String horario = PropertyReader.getInstance().getTestValue("ControladorSucursal.crearSucursal.horario");
        String telefono = PropertyReader.getInstance().getTestValue("ControladorSucursal.crearSucursal.telefono");
        String imagenRemota = PropertyReader.getInstance().getTestValue("ControladorSucursal.crearSucursal.imagenRemota");

        DtSucursal dtSucursal = new DtSucursal();
        dtSucursal.setNombre(nombre);
        dtSucursal.setEmail(emailSucursal);
        dtSucursal.setHorario(horario);
        dtSucursal.setTelefono(telefono);
        dtSucursal.setImagenRemota(imagenRemota);

        iControladorSucursal.crearSucursal(dtSucursal, secreto);
        DtSucursal dtPosSucursal = iControladorSucursal.obtenerSucursalNombre(nombre, secreto);

        ServicioVerificacion.getInstance().verificar(nombre, dtPosSucursal.getNombre());
        ServicioVerificacion.getInstance().verificar(emailSucursal, dtPosSucursal.getEmail());
        ServicioVerificacion.getInstance().verificar(horario, dtPosSucursal.getHorario());
        ServicioVerificacion.getInstance().verificar(telefono, dtPosSucursal.getTelefono());
        ServicioVerificacion.getInstance().verificar(imagenRemota, dtSucursal.getImagenRemota());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Modificar sucursal")
    void modificarSucursal() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);
        String nombre = PropertyReader.getInstance().getTestValue("ControladorSucursal.modificarSucursal.nombre");
        String emailSucursal = PropertyReader.getInstance().getTestValue("ControladorSucursal.modificarSucursal.emailSucursal");
        String horario = PropertyReader.getInstance().getTestValue("ControladorSucursal.modificarSucursal.horario");
        String telefono = PropertyReader.getInstance().getTestValue("ControladorSucursal.modificarSucursal.telefono");
        String imagenRemota = PropertyReader.getInstance().getTestValue("ControladorSucursal.modificarSucursal.imagenRemota");
        String nombreNuevo = PropertyReader.getInstance().getTestValue("ControladorSucursal.modificarSucursal.nombreNuevo");
        String emailSucursalNuevo = PropertyReader.getInstance().getTestValue("ControladorSucursal.modificarSucursal.emailSucursalNuevo");
        String horarioNuevo = PropertyReader.getInstance().getTestValue("ControladorSucursal.modificarSucursal.horarioNuevo");
        String telefonoNuevo = PropertyReader.getInstance().getTestValue("ControladorSucursal.modificarSucursal.telefonoNuevo");
        String imagenRemotaNuevo = PropertyReader.getInstance().getTestValue("ControladorSucursal.modificarSucursal.imagenRemotaNuevo");

        DtSucursal dtSucursal = new DtSucursal();
        dtSucursal.setNombre(nombre);
        dtSucursal.setEmail(emailSucursal);
        dtSucursal.setHorario(horario);
        dtSucursal.setTelefono(telefono);
        dtSucursal.setImagenRemota(imagenRemota);

        iControladorSucursal.crearSucursal(dtSucursal, secreto);
        DtSucursal dtPosSucursal = iControladorSucursal.obtenerSucursalNombre(nombre, secreto);

        ServicioVerificacion.getInstance().verificar(nombre, dtPosSucursal.getNombre());
        ServicioVerificacion.getInstance().verificar(emailSucursal, dtPosSucursal.getEmail());
        ServicioVerificacion.getInstance().verificar(horario, dtPosSucursal.getHorario());
        ServicioVerificacion.getInstance().verificar(telefono, dtPosSucursal.getTelefono());
        ServicioVerificacion.getInstance().verificar(imagenRemota, dtSucursal.getImagenRemota());

        dtPosSucursal.setNombre(nombreNuevo);
        dtPosSucursal.setEmail(emailSucursalNuevo);
        dtPosSucursal.setHorario(horarioNuevo);
        dtPosSucursal.setTelefono(telefonoNuevo);
        dtPosSucursal.setImagenRemota(imagenRemotaNuevo);

        iControladorSucursal.modificarSucursal(dtPosSucursal, secreto);

        ServicioVerificacion.getInstance().verificar(nombreNuevo, dtPosSucursal.getNombre());
        ServicioVerificacion.getInstance().verificar(emailSucursalNuevo, dtPosSucursal.getEmail());
        ServicioVerificacion.getInstance().verificar(horarioNuevo, dtPosSucursal.getHorario());
        ServicioVerificacion.getInstance().verificar(telefonoNuevo, dtPosSucursal.getTelefono());
        ServicioVerificacion.getInstance().verificar(imagenRemotaNuevo, dtPosSucursal.getImagenRemota());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Borrar sucursal")
    void borrarSucursal() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);
        String nombre = PropertyReader.getInstance().getTestValue("ControladorSucursal.borrarSucursal.nombre");
        DtSucursal dtPosSucursal;

        DtSucursal dtSucursal = new DtSucursal();
        dtSucursal.setNombre(nombre);

        iControladorSucursal.crearSucursal(dtSucursal, secreto);
        dtPosSucursal = iControladorSucursal.obtenerSucursalNombre(nombre, secreto);
        iControladorSucursal.borrarSucursal(dtPosSucursal.getId(), secreto);
        dtPosSucursal = iControladorSucursal.obtenerSucursalNombre(nombre, secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtPosSucursal.isBorrado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Restaurar sucursal")
    void restaurarSucursal() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);
        String nombre = PropertyReader.getInstance().getTestValue("ControladorSucursal.restaurarSucursal.nombre");
        DtSucursal dtPosSucursal;

        DtSucursal dtSucursal = new DtSucursal();
        dtSucursal.setNombre(nombre);

        iControladorSucursal.crearSucursal(dtSucursal, secreto);
        dtPosSucursal = iControladorSucursal.obtenerSucursalNombre(nombre, secreto);

        iControladorSucursal.borrarSucursal(dtPosSucursal.getId(), secreto);
        dtPosSucursal = iControladorSucursal.obtenerSucursalNombre(nombre, secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtPosSucursal.isBorrado());

        iControladorSucursal.restaurarSucursal(dtPosSucursal.getId(), secreto);
        dtPosSucursal = iControladorSucursal.obtenerSucursalNombre(nombre, secreto);

        ServicioVerificacion.getInstance().verificarFalse(dtPosSucursal.isBorrado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }
}