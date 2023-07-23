package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtComprador;
import grupo1.supershop.datatypes.DtSucursal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DtConversacionTest")
class DtConversacionTest extends ConfiguracionTest {
    private boolean finalizada;
    private DtComprador dtComprador;
    private DtSucursal dtSucursal;

    @BeforeEach
    public void configurar() {
        finalizada = true;
        dtComprador = new DtComprador();
        dtSucursal = new DtSucursal();
    }

    @DisplayName("Crear DtConversacion - Getter/Setter")
    @Test
    void crear() {
        dtConversacion.setFinalizada(finalizada);
        dtConversacion.setDtComprador(dtComprador);
        dtConversacion.setDtSucursal(dtSucursal);

        ServicioVerificacion.getInstance().verificar(finalizada, dtConversacion.isFinalizada());
        ServicioVerificacion.getInstance().verificar(dtComprador, dtConversacion.getDtComprador());
        ServicioVerificacion.getInstance().verificar(dtSucursal, dtConversacion.getDtSucursal());
    }
}