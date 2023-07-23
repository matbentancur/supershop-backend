package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;

import grupo1.supershop.datatypes.DtComprador;
import grupo1.supershop.datatypes.DtSucursal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DtReclamoTest")
class DtReclamoTest extends ConfiguracionTest {

    private String estado;
    private String descripcion;
    private String conclusion;
    private DtComprador dtComprador;
    private DtSucursal dtSucursal;

    @BeforeEach
    public void configurar() {
        estado = "Estado de la solicitud";
        descripcion = "Descripcion de la solicitud";
        conclusion = "Conclusiones de la solicitud";
        dtComprador = new DtComprador();
        dtSucursal = new DtSucursal();
    }

    @DisplayName("Crear DtReclamo - Getter/Setter")
    @Test
    void crear() {
        dtReclamo.setEstado(estado);
        dtReclamo.setDescripcion(descripcion);
        dtReclamo.setConclusion(conclusion);
        dtReclamo.setDtComprador(dtComprador);
        dtReclamo.setDtSucursal(dtSucursal);

        ServicioVerificacion.getInstance().verificar(estado, dtReclamo.getEstado());
        ServicioVerificacion.getInstance().verificar(descripcion, dtReclamo.getDescripcion());
        ServicioVerificacion.getInstance().verificar(conclusion, dtReclamo.getConclusion());
        ServicioVerificacion.getInstance().verificar(dtComprador, dtReclamo.getDtComprador());
        ServicioVerificacion.getInstance().verificar(dtSucursal, dtReclamo.getDtSucursal());
    }
}