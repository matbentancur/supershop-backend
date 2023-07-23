package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtCompra;
import grupo1.supershop.datatypes.DtProducto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@DisplayName("DtCompraProductoTest")
class DtCompraProductoTest extends ConfiguracionTest {

    private DtCompra dtCompra;
    private DtProducto dtProducto;
    private Integer cantidad;
    private BigDecimal subtotal;

    @BeforeEach
    public void configurar() {
        dtCompra = new DtCompra();
        dtProducto = new DtProducto();
        cantidad = 10;
        subtotal = new BigDecimal("100.00");
    }

    @DisplayName("Crear DtCompraProductoTest - Getter/Setter")
    @Test
    void crear() {
        dtCompraProducto.setDtCompra(dtCompra);
        dtCompraProducto.setDtProducto(dtProducto);
        dtCompraProducto.setCantidad(cantidad);
        dtCompraProducto.setSubtotal(subtotal);

        ServicioVerificacion.getInstance().verificar(dtCompra, dtCompraProducto.getDtCompra());
        ServicioVerificacion.getInstance().verificar(dtProducto, dtCompraProducto.getDtProducto());
        ServicioVerificacion.getInstance().verificar(cantidad, dtCompraProducto.getCantidad());
        ServicioVerificacion.getInstance().verificar(subtotal, dtCompraProducto.getSubtotal());
    }
}