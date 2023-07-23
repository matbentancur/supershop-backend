package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtComprador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@DisplayName("DtCarritoTest")
class DtCarritoTest extends ConfiguracionTest {

    private DtComprador dtComprador;
    private BigDecimal total;

    @BeforeEach
    public void configurar() {
        dtComprador = new DtComprador();
        total = new BigDecimal("21.12345");
    }

    @DisplayName("Crear DtCarrito - Getter/Setter")
    @Test
    void crear() {
        dtCarrito.setDtComprador(dtComprador);
        dtCarrito.setTotal(total);

        ServicioVerificacion.getInstance().verificar(dtComprador, dtCarrito.getDtComprador());
        ServicioVerificacion.getInstance().verificar(total, dtCarrito.getTotal());
    }
}