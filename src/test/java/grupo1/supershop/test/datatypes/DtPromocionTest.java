package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtArchivo;
import grupo1.supershop.datatypes.DtSucursal;
import grupo1.supershop.servicios.ServicioFechaHora;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DtPromocionTest")
class DtPromocionTest extends ConfiguracionTest {

    private String nombre;
    private String descripcion;
    private Date inicia;
    private Date finaliza;
    private Integer descuentoPorcentual;
    private DtArchivo imagen;
    private DtSucursal dtSucursal;

    @BeforeEach
    public void configurar() {
        nombre = "Nombre de la promocion";
        descripcion = "Descripcion de la promocion";
        inicia = ServicioFechaHora.getInstance().stringToDate("01/06/2023");
        finaliza = ServicioFechaHora.getInstance().stringToDate("30/06/2023");
        descuentoPorcentual = 10;
        imagen = new DtArchivo();
        dtSucursal = new DtSucursal();
    }

    @DisplayName("Crear DtPromocion - Getter/Setter")
    @Test
    void crear() {
        dtPromocion.setNombre(nombre);
        dtPromocion.setDescripcion(descripcion);
        dtPromocion.setInicia(inicia);
        dtPromocion.setFinaliza(finaliza);
        dtPromocion.setDescuentoPorcentual(descuentoPorcentual);
        dtPromocion.setImagen(imagen);
        dtPromocion.setDtSucursal(dtSucursal);

        ServicioVerificacion.getInstance().verificar(nombre, dtPromocion.getNombre());
        ServicioVerificacion.getInstance().verificar(descripcion, dtPromocion.getDescripcion());
        ServicioVerificacion.getInstance().verificar(inicia, dtPromocion.getInicia());
        ServicioVerificacion.getInstance().verificar(finaliza, dtPromocion.getFinaliza());
        ServicioVerificacion.getInstance().verificar(descuentoPorcentual, dtPromocion.getDescuentoPorcentual());
        ServicioVerificacion.getInstance().verificar(imagen, dtPromocion.getImagen());
        ServicioVerificacion.getInstance().verificar(dtSucursal, dtPromocion.getDtSucursal());
    }
}