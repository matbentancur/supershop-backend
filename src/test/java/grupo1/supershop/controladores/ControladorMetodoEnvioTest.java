package grupo1.supershop.controladores;

import grupo1.supershop.beans.MetodoEnvio;
import grupo1.supershop.config.properties.PropertyReader;
import grupo1.supershop.config.report.listenertest.ListenerTest;
import grupo1.supershop.config.servicios.ServicioIniciarSesion;
import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtMetodoEnvio;
import grupo1.supershop.fabricas.FabricaMetodoEnvio;
import grupo1.supershop.instalar.InstalarSistema;
import grupo1.supershop.interfaces.controladores.IControladorMetodoEnvio;
import grupo1.supershop.persistencia.manejadores.ManejadorMetodoEnvio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(ListenerTest.class)
class ControladorMetodoEnvioTest {
    private static IControladorMetodoEnvio iControladorMetodoEnvio;

    @BeforeAll
    static void configuracion() throws Exception {
        new InstalarSistema().instalar();
        iControladorMetodoEnvio = FabricaMetodoEnvio.getIControladorMetodoEnvio();
    }

    @Test
    @DisplayName("Obtener metodo envio")
    void obtenerMetodoEnvio() throws Exception {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String[] datos = {"Envío prueba", "Envío de prueba.", "20000", "100", "100", "false"};
        DtMetodoEnvio dtMetodoEnvio = cargarMetodoEnvio(datos);

        DtMetodoEnvio dtMetodoEnvioObtenido = iControladorMetodoEnvio.obtenerMetodoEnvio(dtMetodoEnvio.getId(), secreto);

        ServicioVerificacion.getInstance().verificar(dtMetodoEnvio.getId(), dtMetodoEnvioObtenido.getId());
        ServicioVerificacion.getInstance().verificar(dtMetodoEnvio.getNombre(), dtMetodoEnvioObtenido.getNombre());
        ServicioVerificacion.getInstance().verificar(dtMetodoEnvio.getCosto().intValue(), dtMetodoEnvioObtenido.getCosto().intValue());
        ServicioVerificacion.getInstance().verificar(dtMetodoEnvio.getDescripcion(), dtMetodoEnvioObtenido.getDescripcion());
        ServicioVerificacion.getInstance().verificar(dtMetodoEnvio.getPesoMaximo(), dtMetodoEnvioObtenido.getPesoMaximo());
        ServicioVerificacion.getInstance().verificar(dtMetodoEnvio.getCantidadProductosMaximo(), dtMetodoEnvioObtenido.getCantidadProductosMaximo());
        ServicioVerificacion.getInstance().verificarFalse(dtMetodoEnvioObtenido.isBorrado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Listar metodo envio")
    void listarMetodosEnvio() {
        String cantidad = PropertyReader.getInstance().getTestValue("ControladorMetodoEnvio.listarMetodosEnvio.cantidad");
        List<DtMetodoEnvio> dtLista = iControladorMetodoEnvio.listarMetodosEnvio();
        int cantidadLista = dtLista.size();

        ServicioVerificacion.getInstance().verificarTrue(Integer.parseInt(cantidad) >= cantidadLista);
    }

    @Test
    @DisplayName("Listar metodo envio borrados")
    void listarMetodosEnvioBorrados() throws Exception {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String[] datos = {"Envío prueba 2", "Envío de prueba 2.", "20000", "100", "100.00", "false"};
        DtMetodoEnvio dtMetodoEnvio = cargarMetodoEnvio(datos);

        iControladorMetodoEnvio.borrarMetodoEnvio(dtMetodoEnvio.getId(), secreto);

        List<DtMetodoEnvio> dtLista = iControladorMetodoEnvio.listarMetodosEnvioBorrados(secreto);

        for (DtMetodoEnvio metodoEnvio : dtLista) {
            if (metodoEnvio.getId().equals(dtMetodoEnvio.getId())) {
                ServicioVerificacion.getInstance().verificar(datos[0], metodoEnvio.getNombre());
                ServicioVerificacion.getInstance().verificar(datos[1], metodoEnvio.getDescripcion());
                ServicioVerificacion.getInstance().verificar(Integer.parseInt(datos[2]), metodoEnvio.getPesoMaximo());
                ServicioVerificacion.getInstance().verificar(Integer.parseInt(datos[3]), metodoEnvio.getCantidadProductosMaximo());
                ServicioVerificacion.getInstance().verificar(new BigDecimal(datos[4]), metodoEnvio.getCosto());
                ServicioVerificacion.getInstance().verificarTrue(metodoEnvio.isBorrado());
            }
        }

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Crear metodo envio")
    void crearMetodoEnvio() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String[] datos = {"Envío prueba crear", "Envío de prueba crear", "20000", "100", "100.00", "false"};

        DtMetodoEnvio dtMetodoEnvio = new DtMetodoEnvio();
        dtMetodoEnvio.setBorrado(Boolean.parseBoolean(datos[5]));
        dtMetodoEnvio.setNombre(datos[0]);
        dtMetodoEnvio.setDescripcion(datos[1]);
        dtMetodoEnvio.setPesoMaximo(Integer.parseInt(datos[2]));
        dtMetodoEnvio.setCantidadProductosMaximo(Integer.parseInt(datos[3]));
        dtMetodoEnvio.setCosto(new BigDecimal(datos[4]));

        iControladorMetodoEnvio.crearMetodoEnvio(dtMetodoEnvio, secreto);
        DtMetodoEnvio dtPosMetodoEnvio = iControladorMetodoEnvio.listarMetodosEnvio().get(iControladorMetodoEnvio.listarMetodosEnvio().size() - 1);

        ServicioVerificacion.getInstance().verificarFalse(dtPosMetodoEnvio.isBorrado());
        ServicioVerificacion.getInstance().verificar(dtMetodoEnvio.getNombre(), dtPosMetodoEnvio.getNombre());
        ServicioVerificacion.getInstance().verificar(dtMetodoEnvio.getDescripcion(), dtPosMetodoEnvio.getDescripcion());
        ServicioVerificacion.getInstance().verificar(dtMetodoEnvio.getPesoMaximo(), dtPosMetodoEnvio.getPesoMaximo());
        ServicioVerificacion.getInstance().verificar(dtMetodoEnvio.getCantidadProductosMaximo(), dtPosMetodoEnvio.getCantidadProductosMaximo());
        ServicioVerificacion.getInstance().verificar(dtMetodoEnvio.getCosto(), dtPosMetodoEnvio.getCosto());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Modificar metodo envio")
    void modificarMetodoEnvio() throws Exception {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String[] datos = {"Envío prueba modificar", "Envío de prueba modificar.", "20000", "100", "100.00", "false"};
        DtMetodoEnvio dtMetodoEnvio = cargarMetodoEnvio(datos);

        DtMetodoEnvio dtPosMetodoEnvio = iControladorMetodoEnvio.obtenerMetodoEnvio(dtMetodoEnvio.getId(), secreto);

        int pesoNuevo = 10;
        dtPosMetodoEnvio.setPesoMaximo(pesoNuevo);

        iControladorMetodoEnvio.modificarMetodoEnvio(dtPosMetodoEnvio, secreto);

        dtPosMetodoEnvio = iControladorMetodoEnvio.obtenerMetodoEnvio(dtPosMetodoEnvio.getId(), secreto);

        ServicioVerificacion.getInstance().verificar(dtMetodoEnvio.getId(), dtPosMetodoEnvio.getId());
        ServicioVerificacion.getInstance().verificar(dtMetodoEnvio.getNombre(), dtPosMetodoEnvio.getNombre());
        ServicioVerificacion.getInstance().verificar(dtMetodoEnvio.getCosto(), dtPosMetodoEnvio.getCosto());
        ServicioVerificacion.getInstance().verificar(dtMetodoEnvio.getDescripcion(), dtPosMetodoEnvio.getDescripcion());
        ServicioVerificacion.getInstance().verificar(pesoNuevo, dtPosMetodoEnvio.getPesoMaximo());
        ServicioVerificacion.getInstance().verificar(dtMetodoEnvio.getCantidadProductosMaximo(), dtPosMetodoEnvio.getCantidadProductosMaximo());
        ServicioVerificacion.getInstance().verificarFalse(dtPosMetodoEnvio.isBorrado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);

    }

    @Test
    @DisplayName("Borrar metodo envio")
    void borrarMetodoEnvio() throws Exception {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String[] datos = {"Envío prueba borrar", "Envío de prueba borrar.", "20000", "100", "100", "false"};
        DtMetodoEnvio dtMetodoEnvio = cargarMetodoEnvio(datos);

        iControladorMetodoEnvio.borrarMetodoEnvio(dtMetodoEnvio.getId(), secreto);

        DtMetodoEnvio dtMetodoEnvioBorrado = iControladorMetodoEnvio.obtenerMetodoEnvio(dtMetodoEnvio.getId(), secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtMetodoEnvioBorrado.isBorrado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Restaurar metodo envio")
    void restaurarMetodoEnvio() throws Exception {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String[] datos = {"Envío prueba restaurar", "Envío de prueba restaurar.", "20000", "100", "100", "true"};
        DtMetodoEnvio dtMetodoEnvio = cargarMetodoEnvio(datos);

        iControladorMetodoEnvio.borrarMetodoEnvio(dtMetodoEnvio.getId(),secreto);
        DtMetodoEnvio dtPosMetodoEnvio =  iControladorMetodoEnvio.obtenerMetodoEnvio(dtMetodoEnvio.getId(), secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtPosMetodoEnvio.isBorrado());

        iControladorMetodoEnvio.restaurarMetodoEnvio(dtPosMetodoEnvio.getId(), secreto);

        dtPosMetodoEnvio = iControladorMetodoEnvio.obtenerMetodoEnvio(dtMetodoEnvio.getId(), secreto);

        ServicioVerificacion.getInstance().verificarFalse(dtPosMetodoEnvio.isBorrado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    public DtMetodoEnvio cargarMetodoEnvio(String[] datos) throws Exception {
        MetodoEnvio metodoEnvio = new MetodoEnvio();
        metodoEnvio.setBorrado(Boolean.parseBoolean(datos[5]));
        metodoEnvio.setNombre(datos[0]);
        metodoEnvio.setDescripcion(datos[1]);
        metodoEnvio.setPesoMaximo(Integer.parseInt(datos[2]));
        metodoEnvio.setCantidadProductosMaximo(Integer.parseInt(datos[3]));
        metodoEnvio.setCosto(new BigDecimal(datos[4]));

        ManejadorMetodoEnvio.getInstance().crearMetodoEnvio(metodoEnvio);

        return (DtMetodoEnvio) metodoEnvio.getDataType();
    }
}