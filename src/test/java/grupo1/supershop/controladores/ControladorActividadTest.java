package grupo1.supershop.controladores;

import grupo1.supershop.config.properties.PropertyReader;
import grupo1.supershop.config.report.listenertest.ListenerTest;
import grupo1.supershop.config.servicios.ServicioIniciarSesion;
import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.fabricas.FabricaActividad;
import grupo1.supershop.fabricas.FabricaDomicilio;
import grupo1.supershop.instalar.InstalarSistema;
import grupo1.supershop.interfaces.controladores.IControladorActividad;
import grupo1.supershop.servicios.ServicioFechaHora;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Date;

@ExtendWith(ListenerTest.class)
class ControladorActividadTest {
    private static IControladorActividad iControladorActividad;

    @BeforeAll
    static void configuracion() throws Exception {
        iControladorActividad = FabricaActividad.getIControladorActividad();
        new InstalarSistema().instalar();
    }

    @Test
    @DisplayName("Obtener actividad")
    void obtenerActividad() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);
        String id = PropertyReader.getInstance().getTestValue("ControladorActividad.obtenerActividad.id");
        String idUsuario = PropertyReader.getInstance().getTestValue("ControladorActividad.obtenerActividad.idUsuario");
        String descripcion = PropertyReader.getInstance().getTestValue("ControladorActividad.obtenerActividad.descripcion");

        DtActividad dtActividad = iControladorActividad.obtenerActividad(Long.parseLong(id), secreto);

        ServicioVerificacion.getInstance().verificar(Long.parseLong(id), dtActividad.getId());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(idUsuario), dtActividad.getDtUsuario().getId());
        ServicioVerificacion.getInstance().verificar(descripcion, dtActividad.getDescripcion());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Listar actividades temporal")
    void listarActividadesTemporal() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);
        String stringFechaInicio = PropertyReader.getInstance().getTestValue("ControladorActividad.listarActividadesTemporal.fechaInicio");
        String stringFechaFin = PropertyReader.getInstance().getTestValue("ControladorActividad.listarActividadesTemporal.fechaFin");
        Date fechaInicio = ServicioFechaHora.getInstance().stringToDate(stringFechaInicio);
        Date fechaFin = ServicioFechaHora.getInstance().stringToDate(stringFechaFin);
        String cantidad = PropertyReader.getInstance().getTestValue("ControladorActividad.listarActividadesTemporal.cantidad");

        int cantidadActividades = iControladorActividad.listarActividadesTemporal(fechaInicio, fechaFin, secreto).size();

        ServicioVerificacion.getInstance().verificar(Integer.parseInt(cantidad), cantidadActividades);

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Listar actividades usuario")
    void listarActividadesUsuario() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);
        String idUsuario = PropertyReader.getInstance().getTestValue("ControladorActividad.listarActividadesUsuario.idUsuario");
        String cantidad = PropertyReader.getInstance().getTestValue("ControladorActividad.listarActividadesUsuario.cantidad");

        int cantidadActividades = iControladorActividad.listarActividadesUsuario(Long.parseLong(idUsuario), secreto).size();

        ServicioVerificacion.getInstance().verificar(Integer.parseInt(cantidad), cantidadActividades);

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }
}