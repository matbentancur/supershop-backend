package grupo1.supershop.controladores;

import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Vale;
import grupo1.supershop.config.properties.PropertyReader;
import grupo1.supershop.config.report.listenertest.ListenerTest;
import grupo1.supershop.config.servicios.ServicioIniciarSesion;
import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtVale;
import grupo1.supershop.enums.EstadoVale;
import grupo1.supershop.fabricas.FabricaVale;
import grupo1.supershop.instalar.InstalarSistema;
import grupo1.supershop.interfaces.controladores.IControladorVale;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import grupo1.supershop.persistencia.manejadores.ManejadorUsuario;
import grupo1.supershop.persistencia.manejadores.ManejadorVale;
import grupo1.supershop.servicios.ServicioFechaHora;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(ListenerTest.class)
class ControladorValeTest {
    private static IControladorVale iControladorVale;

    @BeforeAll
    static void configuracion() throws Exception {
        new InstalarSistema().instalar();
        iControladorVale = FabricaVale.getIControladorVale();
    }

    @Test
    @DisplayName("Obtener vale")
    void obtenerVale() {
        String email = PropertyReader.getInstance().getTestValue("ControladorMensaje.enviarMensaje.Email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorMensaje.enviarMensaje.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String[] datos = {"1", "Producto vencido.", "2023-11-10 00:00:00.0", "300.00", "1", "false"};

        String descripcion = datos[1];
        EstadoVale estado = EstadoVale.DISPONIBLE;
        String expira = datos[2];
        String monto = datos[3];
        String sucursalId = datos[4];

        DtVale dtValeObtenido = iControladorVale.obtenerVale(1L, secreto);

        ServicioVerificacion.getInstance().verificar(1L, dtValeObtenido.getId());
        ServicioVerificacion.getInstance().verificar(descripcion, dtValeObtenido.getDescripcion());
        ServicioVerificacion.getInstance().verificar(expira, dtValeObtenido.getExpira().toString());
        ServicioVerificacion.getInstance().verificar(estado.toString(), dtValeObtenido.getEstado());
        ServicioVerificacion.getInstance().verificar(monto, dtValeObtenido.getMonto().toString());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(sucursalId), dtValeObtenido.getDtSucursal().getId());
        ServicioVerificacion.getInstance().verificarFalse(dtValeObtenido.isBorrado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Listar vales")
    void listarVales() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String compradorId = PropertyReader.getInstance().getTestValue("ControladorVale.listarVales.compradorId");
        String[] descripcion = PropertyReader.getInstance().getTestValue("ControladorVale.listarVales.descripcion").split(";");

        List<DtVale> vales = iControladorVale.listarVales(Long.parseLong(compradorId), secreto);

        for (int i = 0; i < descripcion.length; i++) {
            ServicioVerificacion.getInstance().verificar(descripcion[i], vales.get(i).getDescripcion());
            ServicioVerificacion.getInstance().verificar(Long.parseLong(compradorId), vales.get(i).getDtComprador().getId());
        }

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Crear vale")
    void crearVale() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String[] datos = {"11", "Prueba crear.", "10/08/2023", "300.00", "1", "false"};

        String compradorId = datos[0];
        String descripcion = datos[1];
        String expira = datos[2];
        String monto = datos[3];
        String sucursalId = datos[4];

        DtVale dtVale = new DtVale();
        dtVale.setDescripcion(descripcion);
        dtVale.setMonto(new BigDecimal(monto));
        dtVale.setExpira(ServicioFechaHora.getInstance().stringToDate(expira));

        iControladorVale.crearVale(dtVale, Long.parseLong(compradorId), Long.parseLong(sucursalId), secreto);
        List<DtVale> listaVales = iControladorVale.listarVales(Long.parseLong(compradorId), secreto);

        DtVale dtPosVale = listaVales.get(listaVales.size() - 1);

        ServicioVerificacion.getInstance().verificar(dtVale.getDescripcion(),dtPosVale.getDescripcion());
        ServicioVerificacion.getInstance().verificar(dtVale.getMonto(),dtPosVale.getMonto());
        ServicioVerificacion.getInstance().verificar(dtVale.getExpira(),dtPosVale.getExpira());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Modificar vale")
    void modificarVale() throws Exception {
        String[] datos = {"11", "prueba modificar", "10/08/2023", "300", "1", "false"};
        DtVale dtVale = cargarVale(datos);

        String nuevaDesc = "nueva descripcion";
        dtVale.setDescripcion(nuevaDesc);

        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        DtVale dtValeAntes = iControladorVale.obtenerVale(dtVale.getId(), secreto);

        iControladorVale.modificarVale(dtVale, secreto);

        DtVale dtValeDespues = iControladorVale.obtenerVale(dtVale.getId(), secreto);

        ServicioVerificacion.getInstance().verificar(dtValeAntes.getId(), dtValeDespues.getId());
        ServicioVerificacion.getInstance().verificar(nuevaDesc, dtValeDespues.getDescripcion());
        ServicioVerificacion.getInstance().verificar(dtValeAntes.getExpira().toString(), dtValeDespues.getExpira().toString());
        ServicioVerificacion.getInstance().verificar(dtValeAntes.getEstado(), dtValeDespues.getEstado());
        ServicioVerificacion.getInstance().verificar(dtValeAntes.getMonto(), dtValeDespues.getMonto());
        ServicioVerificacion.getInstance().verificar(dtValeAntes.getDtSucursal().getId(), dtValeDespues.getDtSucursal().getId());
        ServicioVerificacion.getInstance().verificarFalse(dtValeAntes.isBorrado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Borrar vale")
    void borrarVale() throws Exception {
        String[] datos = {"11", "prueba borrar", "10/11/2023", "300", "1", "false"};
        DtVale dtVale = cargarVale(datos);

        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        iControladorVale.borrarVale(dtVale.getId(), secreto);

        DtVale dtValeDespues = iControladorVale.obtenerVale(dtVale.getId(), secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtValeDespues.isBorrado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    public DtVale cargarVale(String[] datos) throws Exception {
        Vale vale = new Vale();
        Comprador usuario = (Comprador) ManejadorUsuario.getInstance().obtenerUsuario(Long.parseLong(datos[0]));
        vale.setComprador(usuario);
        vale.setDescripcion(datos[1]);
        vale.setEstado(EstadoVale.DISPONIBLE);
        vale.setExpira(ServicioFechaHora.getInstance().stringToDate(datos[2]));
        vale.setMonto(BigDecimal.valueOf(Integer.parseInt(datos[3])));
        Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(Long.parseLong(datos[4]));
        vale.setSucursal(sucursal);
        vale.setBorrado(Boolean.parseBoolean(datos[5]));

        ManejadorVale.getInstance().crearVale(vale);

        return (DtVale) vale.getDataType();
    }
}