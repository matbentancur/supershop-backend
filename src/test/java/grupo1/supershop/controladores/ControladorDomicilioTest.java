package grupo1.supershop.controladores;

import grupo1.supershop.config.properties.PropertyReader;
import grupo1.supershop.config.report.listenertest.ListenerTest;
import grupo1.supershop.config.servicios.ServicioIniciarSesion;
import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtDomicilio;
import grupo1.supershop.fabricas.FabricaDomicilio;
import grupo1.supershop.instalar.InstalarSistema;
import grupo1.supershop.interfaces.controladores.IControladorDomicilio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;


@ExtendWith(ListenerTest.class)
class ControladorDomicilioTest {
    private static IControladorDomicilio iControladorDomicilio;

    @BeforeAll
    static void configuracion() throws Exception {
        iControladorDomicilio = FabricaDomicilio.getIControladorDomicilio();
        new InstalarSistema().instalar();
    }

    @Test
    @DisplayName("Obtener domicilio")
    void obtenerDomicilio() {
        String email = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Obtener.Email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Obtener.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String domicilioId = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Obtener.domicilioId");
        String apartamento = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Obtener.Apartamento");
        String barrio = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Obtener.Barrio");
        String nombre = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Obtener.Nombre");
        String descripcion = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Obtener.Descripcion");
        BigDecimal longitud = BigDecimal.valueOf(Long.parseLong(PropertyReader.getInstance().getTestValue("ControladorDomicilio.Obtener.Longitud")));
        BigDecimal latitud = BigDecimal.valueOf(Long.parseLong(PropertyReader.getInstance().getTestValue("ControladorDomicilio.Obtener.Latitud")));
        String calle = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Obtener.Calle");
        String numeracion = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Obtener.Numeracion");
        String codigoPostal = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Obtener.CodigoPostal");
        String block = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Obtener.Block");
        String esquina = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Obtener.Esquina");
        String departamento = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Obtener.Departamento");
        String referencias = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Obtener.Referencias");
        String compradorId = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Obtener.compradorId");

        DtDomicilio dtDomicilio = iControladorDomicilio.obtenerDomicilio(Long.parseLong(domicilioId), secreto);

        ServicioVerificacion.getInstance().verificar(Long.parseLong(compradorId), dtDomicilio.getDtComprador().getId());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(domicilioId), dtDomicilio.getId());
        ServicioVerificacion.getInstance().verificar(apartamento, dtDomicilio.getApartamento());
        ServicioVerificacion.getInstance().verificar(barrio, dtDomicilio.getBarrio());
        ServicioVerificacion.getInstance().verificarFalse(dtDomicilio.isBorrado());
        ServicioVerificacion.getInstance().verificar(nombre, dtDomicilio.getNombre());
        ServicioVerificacion.getInstance().verificar(descripcion, dtDomicilio.getDescripcion());
        ServicioVerificacion.getInstance().verificar(longitud.toBigInteger(), dtDomicilio.getLongitud().toBigInteger());
        ServicioVerificacion.getInstance().verificar(latitud.toBigInteger(), dtDomicilio.getLatitud().toBigInteger());
        ServicioVerificacion.getInstance().verificar(calle, dtDomicilio.getCalle());
        ServicioVerificacion.getInstance().verificar(numeracion, dtDomicilio.getNumeracion());
        ServicioVerificacion.getInstance().verificar(Integer.parseInt(codigoPostal), dtDomicilio.getCodigoPostal());
        ServicioVerificacion.getInstance().verificar(block, dtDomicilio.getBlock());
        ServicioVerificacion.getInstance().verificar(esquina, dtDomicilio.getEsquina());
        ServicioVerificacion.getInstance().verificar(departamento, dtDomicilio.getDepartamento());
        ServicioVerificacion.getInstance().verificar(referencias, dtDomicilio.getReferencias());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Listar domicilios comprador")
    void listarDomiciliosComprador() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioComprador.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);
        String id = PropertyReader.getInstance().getTestValue("ControladorDomicilio.listarDomiciliosComprador.id");

        List<DtDomicilio> dtDomicilios = iControladorDomicilio.listarDomiciliosComprador(secreto);

        ServicioVerificacion.getInstance().verificar(Long.parseLong(id), dtDomicilios.get(0).getId());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Listar domicilios comprador borrados")
    void listarDomiciliosCompradorBorrados() {
        String email = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Obtener.Email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Obtener.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        List<DtDomicilio> dtLista = iControladorDomicilio.listarDomiciliosCompradorBorrados(secreto);

        ServicioVerificacion.getInstance().verificar(0, dtLista.size());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Crear domicilio")
    void crearDomicilio() {
        String email = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Crear.Email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Crear.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String apartamento = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Crear.Apartamento");
        String barrio = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Crear.Barrio");
        String borrado = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Crear.Borrado");
        String nombre = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Crear.Nombre");
        String descripcion = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Crear.Descripcion");
        String longitud = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Crear.Longitud");
        String latitud = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Crear.Latitud");
        String calle = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Crear.Calle");
        String numeracion = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Crear.Numeracion");
        String codigoPostal = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Crear.CodigoPostal");
        String block = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Crear.Block");
        String esquina = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Crear.Esquina");
        String departamento = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Crear.Departamento");
        String referencia = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Crear.Referencias");

        DtDomicilio dtDomicilio = new DtDomicilio();
        dtDomicilio.setApartamento(apartamento);
        dtDomicilio.setBarrio(barrio);
        dtDomicilio.setBorrado(Boolean.parseBoolean(borrado));
        dtDomicilio.setNombre(nombre);
        dtDomicilio.setDescripcion(descripcion);
        dtDomicilio.setLongitud(new BigDecimal(longitud));
        dtDomicilio.setLatitud(new BigDecimal(latitud));
        dtDomicilio.setCalle(calle);
        dtDomicilio.setNumeracion(numeracion);
        dtDomicilio.setCodigoPostal(Integer.parseInt(codigoPostal));
        dtDomicilio.setBlock(block);
        dtDomicilio.setEsquina(esquina);
        dtDomicilio.setDepartamento(departamento);
        dtDomicilio.setReferencias(referencia);

        iControladorDomicilio.crearDomicilio(dtDomicilio, secreto);

        List<DtDomicilio> domicilios = iControladorDomicilio.listarDomiciliosComprador(secreto);
        DtDomicilio dtPosDomicilio = domicilios.get(domicilios.size() - 1);

        ServicioVerificacion.getInstance().verificar(apartamento, dtPosDomicilio.getApartamento());
        ServicioVerificacion.getInstance().verificar(barrio, dtPosDomicilio.getBarrio());
        ServicioVerificacion.getInstance().verificarFalse(dtPosDomicilio.isBorrado());
        ServicioVerificacion.getInstance().verificar(nombre, dtPosDomicilio.getNombre());
        ServicioVerificacion.getInstance().verificar(descripcion, dtPosDomicilio.getDescripcion());
        ServicioVerificacion.getInstance().verificar(new BigDecimal(longitud), dtPosDomicilio.getLongitud());
        ServicioVerificacion.getInstance().verificar(new BigDecimal(latitud), dtPosDomicilio.getLatitud());
        ServicioVerificacion.getInstance().verificar(calle, dtPosDomicilio.getCalle());
        ServicioVerificacion.getInstance().verificar(numeracion, dtPosDomicilio.getNumeracion());
        ServicioVerificacion.getInstance().verificar(Integer.parseInt(codigoPostal), dtPosDomicilio.getCodigoPostal());
        ServicioVerificacion.getInstance().verificar(block, dtPosDomicilio.getBlock());
        ServicioVerificacion.getInstance().verificar(esquina, dtPosDomicilio.getEsquina());
        ServicioVerificacion.getInstance().verificar(departamento, dtPosDomicilio.getDepartamento());
        ServicioVerificacion.getInstance().verificar(referencia, dtPosDomicilio.getReferencias());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Modificar domicilio")
    void modificarDomicilio() throws Exception {
        String email = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Modificar.Email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Modificar.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String[] datos = {"001", "prueba", "Casa", "Dirección de casa.", "Homero de Gregorio", "3527", "11700", "1", "Andres lamas", "Montevdieo", "Referencia", "18", "false"};
        String[] datosNuevos = {"111", "barrio modificado", "nombre modificado", "Dirección modificada.", "Calle modificada", "1111", "22222", "7", "Esquina modificada", "Departamento modificado", "Referencia modificada", "18", "false"};
        String idUsuario = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Modificar.IdUsuario");
        String longitud = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Modificar.Longitud");
        String latitud = PropertyReader.getInstance().getTestValue("ControladorDomicilio.Modificar.Latitud");
        DtDomicilio dtDomicilio = cargarDomicilio(datos, longitud, latitud);

        iControladorDomicilio.crearDomicilio(dtDomicilio, secreto);

        List<DtDomicilio> domicilios = iControladorDomicilio.listarDomiciliosComprador(secreto);
        DtDomicilio dtPosDomicilio = domicilios.get(domicilios.size() - 1);

        dtPosDomicilio.setApartamento(datosNuevos[0]);
        dtPosDomicilio.setBarrio(datosNuevos[1]);
        dtPosDomicilio.setBorrado(Boolean.parseBoolean(datos[12]));
        dtPosDomicilio.setNombre(datosNuevos[2]);
        dtPosDomicilio.setDescripcion(datosNuevos[3]);
        dtPosDomicilio.setLongitud(new BigDecimal(longitud));
        dtPosDomicilio.setLatitud(new BigDecimal(latitud));
        dtPosDomicilio.setCalle(datosNuevos[4]);
        dtPosDomicilio.setNumeracion(datosNuevos[5]);
        dtPosDomicilio.setCodigoPostal(Integer.parseInt(datosNuevos[6]));
        dtPosDomicilio.setBlock(datosNuevos[7]);
        dtPosDomicilio.setEsquina(datosNuevos[8]);
        dtPosDomicilio.setDepartamento(datosNuevos[9]);
        dtPosDomicilio.setReferencias(datosNuevos[10]);

        iControladorDomicilio.modificarDomicilio(dtPosDomicilio, secreto);
        dtPosDomicilio = iControladorDomicilio.obtenerDomicilio(dtPosDomicilio.getId(), secreto);

        ServicioVerificacion.getInstance().verificar(Long.parseLong(idUsuario), dtPosDomicilio.getDtComprador().getId());
        ServicioVerificacion.getInstance().verificar(datosNuevos[0], dtPosDomicilio.getApartamento());
        ServicioVerificacion.getInstance().verificar(datosNuevos[1], dtPosDomicilio.getBarrio());
        ServicioVerificacion.getInstance().verificarFalse(dtPosDomicilio.isBorrado());
        ServicioVerificacion.getInstance().verificar(datosNuevos[2], dtPosDomicilio.getNombre());
        ServicioVerificacion.getInstance().verificar(datosNuevos[3], dtPosDomicilio.getDescripcion());
        ServicioVerificacion.getInstance().verificar(new BigDecimal(longitud), dtPosDomicilio.getLongitud());
        ServicioVerificacion.getInstance().verificar(new BigDecimal(latitud), dtPosDomicilio.getLatitud());
        ServicioVerificacion.getInstance().verificar(datosNuevos[4], dtPosDomicilio.getCalle());
        ServicioVerificacion.getInstance().verificar(datosNuevos[5], dtPosDomicilio.getNumeracion());
        ServicioVerificacion.getInstance().verificar(Integer.parseInt(datosNuevos[6]), dtPosDomicilio.getCodigoPostal());
        ServicioVerificacion.getInstance().verificar(datosNuevos[7], dtPosDomicilio.getBlock());
        ServicioVerificacion.getInstance().verificar(datosNuevos[8], dtPosDomicilio.getEsquina());
        ServicioVerificacion.getInstance().verificar(datosNuevos[9], dtPosDomicilio.getDepartamento());
        ServicioVerificacion.getInstance().verificar(datosNuevos[10], dtPosDomicilio.getReferencias());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Borrar domicilio")
    void borrarDomicilio() throws Exception {
        String email = PropertyReader.getInstance().getTestValue("ControladorDomicilio.borrarDomicilio.Email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorDomicilio.borrarDomicilio.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String[] datos = {"001", "pruebaBorrar", "Casa", "Dirección de casa.", "Homero de Gregorio", "3527", "11700", "1", "Andres lamas", "Montevdieo", "Referencia", "18", "false"};
        String longitud = PropertyReader.getInstance().getTestValue("ControladorDomicilio.borrarDomicilio.Longitud");
        String latitud = PropertyReader.getInstance().getTestValue("ControladorDomicilio.borrarDomicilio.Latitud");
        DtDomicilio dtDomicilio = cargarDomicilio(datos, longitud, latitud);

        iControladorDomicilio.crearDomicilio(dtDomicilio, secreto);

        List<DtDomicilio> domicilios = iControladorDomicilio.listarDomiciliosComprador(secreto);
        DtDomicilio dtPosDomicilio = domicilios.get(domicilios.size() - 1);

        iControladorDomicilio.borrarDomicilio(dtPosDomicilio.getId(), secreto);

        dtPosDomicilio = iControladorDomicilio.obtenerDomicilio(dtPosDomicilio.getId(), secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtPosDomicilio.isBorrado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Restaurar domicilio")
    void restaurarDomicilio() throws Exception {
        String email = PropertyReader.getInstance().getTestValue("ControladorDomicilio.restaurarDomicilio.Email");
        String passw = PropertyReader.getInstance().getTestValue("ControladorDomicilio.restaurarDomicilio.Password");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String[] datos = {"001", "pruebaBorrar", "Casa", "Dirección de casa.", "Homero de Gregorio", "3527", "11700", "1", "Andres lamas", "Montevdieo", "Referencia", "18", "true"};
        String longitud = PropertyReader.getInstance().getTestValue("ControladorDomicilio.restaurarDomicilio.Longitud");
        String latitud = PropertyReader.getInstance().getTestValue("ControladorDomicilio.restaurarDomicilio.Latitud");
        DtDomicilio dtDomicilio = cargarDomicilio(datos, longitud, latitud);

        iControladorDomicilio.crearDomicilio(dtDomicilio, secreto);

        List<DtDomicilio> domicilios = iControladorDomicilio.listarDomiciliosComprador(secreto);
        DtDomicilio dtPosDomicilio = domicilios.get(domicilios.size() - 1);

        iControladorDomicilio.borrarDomicilio(dtPosDomicilio.getId(), secreto);

        dtPosDomicilio = iControladorDomicilio.obtenerDomicilio(dtPosDomicilio.getId(), secreto);

        ServicioVerificacion.getInstance().verificarTrue(dtPosDomicilio.isBorrado());

        iControladorDomicilio.restaurarDomicilio(dtPosDomicilio.getId(), secreto);

        dtPosDomicilio = iControladorDomicilio.obtenerDomicilio(dtPosDomicilio.getId(), secreto);

        ServicioVerificacion.getInstance().verificarFalse(dtPosDomicilio.isBorrado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);

    }

    private DtDomicilio cargarDomicilio(String[] datos, String longitud, String latitud) throws Exception {
        DtDomicilio dtDomicilio = new DtDomicilio();
        dtDomicilio.setApartamento(datos[0]);
        dtDomicilio.setBarrio(datos[1]);
        dtDomicilio.setBorrado(Boolean.parseBoolean(datos[12]));
        dtDomicilio.setNombre(datos[2]);
        dtDomicilio.setDescripcion(datos[3]);
        dtDomicilio.setLongitud(new BigDecimal(longitud));
        dtDomicilio.setLatitud(new BigDecimal(latitud));
        dtDomicilio.setCalle(datos[4]);
        dtDomicilio.setNumeracion(datos[5]);
        dtDomicilio.setCodigoPostal(Integer.parseInt(datos[6]));
        dtDomicilio.setBlock(datos[7]);
        dtDomicilio.setEsquina(datos[8]);
        dtDomicilio.setDepartamento(datos[9]);
        dtDomicilio.setReferencias(datos[10]);

        return dtDomicilio;
    }
}