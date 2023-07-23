package grupo1.supershop.controladores;

import grupo1.supershop.beans.*;
import grupo1.supershop.config.properties.PropertyReader;
import grupo1.supershop.config.report.listenertest.ListenerTest;
import grupo1.supershop.config.servicios.ServicioIniciarSesion;
import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtCategoria;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtReclamo;
import grupo1.supershop.datatypes.DtVale;
import grupo1.supershop.enums.EstadoReclamo;
import grupo1.supershop.enums.EstadoVale;
import grupo1.supershop.fabricas.FabricaMensaje;
import grupo1.supershop.fabricas.FabricaReclamo;
import grupo1.supershop.fabricas.FabricaSesion;
import grupo1.supershop.instalar.InstalarSistema;
import grupo1.supershop.interfaces.controladores.IControladorMensaje;
import grupo1.supershop.interfaces.controladores.IControladorReclamo;
import grupo1.supershop.interfaces.controladores.IControladorSesion;
import grupo1.supershop.persistencia.manejadores.ManejadorReclamo;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import grupo1.supershop.persistencia.manejadores.ManejadorUsuario;
import grupo1.supershop.persistencia.manejadores.ManejadorVale;
import grupo1.supershop.servicios.*;
import grupo1.supershop.validadores.ValidadorReclamo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ListenerTest.class)
class ControladorReclamoTest {
    private static IControladorReclamo iControladorReclamo;

    @BeforeAll
    static void configuracion() throws Exception {
        new InstalarSistema().instalar();
        iControladorReclamo = FabricaReclamo.getIControladorReclamo();
    }

    @Test
    @DisplayName("Obtener reclamo")
    void obtenerReclamo() {
        String email = PropertyReader.getInstance().getTestValue("ControladorMensaje.obtenerReclamo.Email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorMensaje.obtenerReclamo.Passw");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String id = PropertyReader.getInstance().getTestValue("ControladorMensaje.obtenerReclamo.id");

        String[] datos = {"11", "1", "El producto no ha llegado", "", "PENDIENTE", "false"};
        String compradorId = datos[0];
        String sucursalId = datos[1];
        String descripcion = datos[2];
        String estado = datos[4];

        DtReclamo dtReclamo = iControladorReclamo.obtenerReclamo(Long.parseLong(id), secreto);

        ServicioVerificacion.getInstance().verificar(descripcion, dtReclamo.getDescripcion());
        ServicioVerificacion.getInstance().verificar(estado, dtReclamo.getEstado());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(compradorId), dtReclamo.getDtComprador().getId());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(sucursalId), dtReclamo.getDtSucursal().getId());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Listar reclamos")
    void listarReclamos() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdmin.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String[] listaReclamos = PropertyReader.getInstance().getTestValue("ControladorMensaje.listarReclamos.reclamos").split(";");
        List<DtReclamo> dtReclamoList = iControladorReclamo.listarReclamos(secreto);

        for (int i = 0; i < listaReclamos.length; i++)
            ServicioVerificacion.getInstance().verificar(listaReclamos[i], dtReclamoList.get(i).getDescripcion());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Crear reclamo")
    void crearReclamo() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String[] datos = {"2", "Prueba reclamo"};
        String sucursalId = datos[0];
        String descripcion = datos[1];

        DtReclamo dtReclamo = new DtReclamo();
        dtReclamo.setDescripcion(descripcion);

        iControladorReclamo.crearReclamo(dtReclamo, Long.parseLong(sucursalId), secreto);
        Reclamo reclamo = ManejadorReclamo.getInstance().obtenerReclamo("descripcion", descripcion);

        ServicioVerificacion.getInstance().verificar(descripcion, reclamo.getDescripcion());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Confirmar reclamo")
    void confirmarReclamo() throws Exception {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String[] datos = {"11", "1", "El producto no ha llegado", "", "PENDIENTE"};

        DtReclamo dtReclamo = cargarReclamo(datos);

        iControladorReclamo.confirmarReclamo(dtReclamo.getId(), secreto);

        DtReclamo reclamoDespues = iControladorReclamo.obtenerReclamo(dtReclamo.getId(), secreto);

        ServicioVerificacion.getInstance().verificar(EstadoReclamo.CONFIRMADO.toString(), reclamoDespues.getEstado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Concluir reclamo")
    void concluirReclamo() throws Exception {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String conclusion = "conclusion prueba";
        String[] datos = {"11", "1", "El producto no ha llegado",conclusion, "CONFIRMADO"};

        DtReclamo dtReclamo = cargarReclamo(datos);

        iControladorReclamo.concluirReclamo(dtReclamo, secreto);

        DtReclamo reclamoDespues = iControladorReclamo.obtenerReclamo(dtReclamo.getId(), secreto);

        ServicioVerificacion.getInstance().verificar(EstadoReclamo.CONCLUIDO.toString(), reclamoDespues.getEstado());
        ServicioVerificacion.getInstance().verificar(conclusion, reclamoDespues.getConclusion());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    public DtReclamo cargarReclamo(String[] datos) throws Exception {
        Comprador comprador = ManejadorUsuario.getInstance().obtenerComprador(Long.parseLong(datos[0]));
        Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(Long.parseLong(datos[1]));

        Reclamo reclamo = new Reclamo();
        reclamo.setComprador(comprador);
        reclamo.setSucursal(sucursal);
        reclamo.setDescripcion(datos[2]);

        if (!datos[3].isEmpty()) {
            reclamo.setConclusion(datos[3]);
        }

        switch (datos[4]) {
            case "PENDIENTE" -> reclamo.setEstado(EstadoReclamo.PENDIENTE);
            case "CONFIRMADO" -> reclamo.setEstado(EstadoReclamo.CONFIRMADO);
            case "CONCLUIDO" -> reclamo.setEstado(EstadoReclamo.CONCLUIDO);
        }

        ManejadorReclamo.getInstance().crearReclamo(reclamo);

        return (DtReclamo) reclamo.getDataType();
    }
}