package grupo1.supershop.controladores;

import grupo1.supershop.beans.Producto;
import grupo1.supershop.beans.Promocion;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.config.properties.PropertyReader;
import grupo1.supershop.config.report.listenertest.ListenerTest;
import grupo1.supershop.config.servicios.ServicioIniciarSesion;
import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtPromocion;
import grupo1.supershop.fabricas.FabricaPromocion;
import grupo1.supershop.instalar.InstalarSistema;
import grupo1.supershop.interfaces.controladores.IControladorPromocion;
import grupo1.supershop.persistencia.manejadores.ManejadorProducto;
import grupo1.supershop.persistencia.manejadores.ManejadorPromocion;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import grupo1.supershop.servicios.ServicioFechaHora;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Date;
import java.util.List;

@ExtendWith(ListenerTest.class)
class ControladorPromocionTest {
    private static IControladorPromocion iControladorPromocion;

    @BeforeAll
    static void configuracion() throws Exception {
        new InstalarSistema().instalar();
        iControladorPromocion = FabricaPromocion.getIControladorPromocion();

    }

    @Test
    @DisplayName("Obtener promocion")
    void obtenerPromocion() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String promocionId = PropertyReader.getInstance().getTestValue("ControladorPromocion.ObtenerPromocion.id");
        String nombre = PropertyReader.getInstance().getTestValue("ControladorPromocion.ObtenerPromocion.nombre");
        String descripcion = PropertyReader.getInstance().getTestValue("ControladorPromocion.ObtenerPromocion.descripcion");
        String descuentoPorcentual = PropertyReader.getInstance().getTestValue("ControladorPromocion.ObtenerPromocion.descuentoPorcentual");
        Date inicia = ServicioFechaHora.getInstance().stringToDate(PropertyReader.getInstance().getTestValue("ControladorPromocion.ObtenerPromocion.inicia"));
        Date finaliza = ServicioFechaHora.getInstance().stringToDate(PropertyReader.getInstance().getTestValue("ControladorPromocion.ObtenerPromocion.finaliza"));
        String cantidad = PropertyReader.getInstance().getTestValue("ControladorPromocion.ObtenerPromocion.cantidad");
        String productoId = PropertyReader.getInstance().getTestValue("ControladorPromocion.ObtenerPromocion.productoId");
        String sucursalId = PropertyReader.getInstance().getTestValue("ControladorPromocion.ObtenerPromocion.sucursalId");
        String imagenRemota = PropertyReader.getInstance().getTestValue("ControladorPromocion.ObtenerPromocion.imagenRemota");

        DtPromocion dtPromocion = iControladorPromocion.obtenerPromocion(Long.parseLong(promocionId), secreto);

        ServicioVerificacion.getInstance().verificar(nombre, dtPromocion.getNombre());
        ServicioVerificacion.getInstance().verificar(descripcion, dtPromocion.getDescripcion());
        ServicioVerificacion.getInstance().verificar(Integer.parseInt(descuentoPorcentual), dtPromocion.getDescuentoPorcentual());
        ServicioVerificacion.getInstance().verificar(inicia, dtPromocion.getInicia());
        ServicioVerificacion.getInstance().verificar(finaliza, dtPromocion.getFinaliza());
        ServicioVerificacion.getInstance().verificar(Integer.parseInt(cantidad), dtPromocion.getCantidad());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(productoId), dtPromocion.getDtProducto().getId());
        ServicioVerificacion.getInstance().verificar(Long.parseLong(sucursalId), dtPromocion.getDtSucursal().getId());
        ServicioVerificacion.getInstance().verificar(imagenRemota, dtPromocion.getImagenRemota());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Listar promociones")
    void listarPromociones() {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String sucursalId = PropertyReader.getInstance().getTestValue("ControladorPromocion.listarPromociones.sucursalId");
        String[] nombrePromociones = PropertyReader.getInstance().getTestValue("ControladorPromocion.listarPromociones.nombrePromociones").split(",");

        List<DtPromocion> dtPromociones = iControladorPromocion.listarPromociones(Long.parseLong(sucursalId), secreto);

        for (int i = 0; i < nombrePromociones.length; i++)
            ServicioVerificacion.getInstance().verificar(nombrePromociones[i], dtPromociones.get(i).getNombre());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Crear promocion")
    void crearPromocion() {
        String email = PropertyReader.getInstance().getTestValue("ControladorPromocion.crearPromocion.emailAdmin");
        String passw = PropertyReader.getInstance().getTestValue("ControladorPromocion.crearPromocion.passAdmin");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String[] datos = {"Crear promocion test", "test crear", "30", "10/08/2023", "10/11/2023", "40", "32", "2", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1688579847/promocion_pwx5vn.png"};

        String imagen = datos[8];
        DtPromocion promocion = new DtPromocion();
        promocion.setBorrado(false);
        promocion.setNombre(datos[0]);
        promocion.setDescripcion(datos[1]);
        promocion.setDescuentoPorcentual(Integer.parseInt(datos[2]));
        promocion.setInicia(ServicioFechaHora.getInstance().stringToDate(datos[3]));
        promocion.setFinaliza(ServicioFechaHora.getInstance().stringToDate(datos[4]));
        promocion.setCantidad(Integer.parseInt(datos[5]));
        Long productoId = Long.parseLong(datos[6]);
        Long sucursalId = Long.parseLong(datos[7]);
        promocion.setImagenRemota(imagen);

        iControladorPromocion.crearPromocion(promocion, productoId, sucursalId, secreto);
        List<DtPromocion> dtPromocionLista = iControladorPromocion.listarPromociones(sucursalId, secreto);

        ServicioVerificacion.getInstance().verificarFalse(dtPromocionLista.get(0).isBorrado());
        ServicioVerificacion.getInstance().verificar(promocion.getNombre(), dtPromocionLista.get(0).getNombre());
        ServicioVerificacion.getInstance().verificar(promocion.getDescripcion(), dtPromocionLista.get(0).getDescripcion());
        ServicioVerificacion.getInstance().verificar(promocion.getDescuentoPorcentual(), dtPromocionLista.get(0).getDescuentoPorcentual());
        ServicioVerificacion.getInstance().verificar(promocion.getInicia(), dtPromocionLista.get(0).getInicia());
        ServicioVerificacion.getInstance().verificar(promocion.getFinaliza(), dtPromocionLista.get(0).getFinaliza());
        ServicioVerificacion.getInstance().verificar(promocion.getCantidad(), dtPromocionLista.get(0).getCantidad());
        ServicioVerificacion.getInstance().verificar(promocion.getImagenRemota(), dtPromocionLista.get(0).getImagenRemota());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Modificar promocion")
    void modificarPromocion() throws Exception {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String[] datos = {"test modificar", "test modificar", "30", "10/08/2023", "10/11/2023", "40", "32", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1688579847/promocion_pwx5vn.png"};
        DtPromocion dtPromocion = cargarPromocion(datos);

        String descripcionNueva = "probando modificacion";
        dtPromocion.setDescripcion(descripcionNueva);

        iControladorPromocion.modificarPromocion(dtPromocion, secreto);

        DtPromocion promocionDespues = iControladorPromocion.obtenerPromocion(dtPromocion.getId(), secreto);

        ServicioVerificacion.getInstance().verificar(descripcionNueva, promocionDespues.getDescripcion());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Borrar promocion")
    void borrarPromocion() throws Exception {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String[] datos = {"test borrar", "test borrar", "30", "10/08/2023", "10/11/2023", "40", "32", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1688579847/promocion_pwx5vn.png"};
        DtPromocion dtPromocion = cargarPromocion(datos);

        DtPromocion promocionAntes = iControladorPromocion.obtenerPromocion(dtPromocion.getId(), secreto);

        ServicioVerificacion.getInstance().verificarFalse(promocionAntes.isBorrado());

        iControladorPromocion.borrarPromocion(dtPromocion.getId(), secreto);

        DtPromocion promocionDespues = iControladorPromocion.obtenerPromocion(dtPromocion.getId(), secreto);

        ServicioVerificacion.getInstance().verificarTrue(promocionDespues.isBorrado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    @Test
    @DisplayName("Restaurar promocion")
    void restaurarPromocion() throws Exception {
        String email = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String passw = PropertyReader.getInstance().getTestValue("General.UsuarioAdminSucursal.Email");
        String secreto = ServicioIniciarSesion.getInstance().iniciarSesion(email, passw);

        String[] datos = {"test restaurar", "test restaurar", "30", "10/08/2023", "10/11/2023", "40", "32", "1", "https://res.cloudinary.com/hnxrbcyvp/image/upload/v1688579847/promocion_pwx5vn.png"};
        DtPromocion dtPromocion = cargarPromocion(datos);

        iControladorPromocion.borrarPromocion(dtPromocion.getId(), secreto);
        DtPromocion promocionAntes = iControladorPromocion.obtenerPromocion(dtPromocion.getId(), secreto);

        ServicioVerificacion.getInstance().verificarTrue(promocionAntes.isBorrado());

        iControladorPromocion.restaurarPromocion(dtPromocion.getId(), secreto);

        DtPromocion promocionDespues = iControladorPromocion.obtenerPromocion(dtPromocion.getId(), secreto);

        ServicioVerificacion.getInstance().verificarFalse(promocionDespues.isBorrado());

        ServicioIniciarSesion.getInstance().cerrarSesion(secreto);
    }

    public DtPromocion cargarPromocion(String[] datos) throws Exception {

        String imagen = datos[8];
        Promocion promocion = new Promocion();
        promocion.setBorrado(false);
        promocion.setNombre(datos[0]);
        promocion.setDescripcion(datos[1]);
        promocion.setDescuentoPorcentual(Integer.parseInt(datos[2]));
        promocion.setInicia(ServicioFechaHora.getInstance().stringToDate(datos[3]));
        promocion.setFinaliza(ServicioFechaHora.getInstance().stringToDate(datos[4]));
        promocion.setCantidad(Integer.parseInt(datos[5]));
        Producto producto = ManejadorProducto.getInstance().obtenerProducto(Long.parseLong(datos[6]));
        promocion.setProducto(producto);
        Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(Long.parseLong(datos[7]));
        promocion.setSucursal(sucursal);
        promocion.setImagenRemota(imagen);

        ManejadorPromocion.getInstance().crearPromocion(promocion);

        return (DtPromocion) promocion.getDataType();

    }
}