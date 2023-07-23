package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtCarrito;
import grupo1.supershop.datatypes.DtProducto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@DisplayName("DtCarritoProductoTest")
class DtCarritoProductoTest extends ConfiguracionTest {

    private DtCarrito dtCarrito;
    private DtProducto producto;
    private Integer cantidad;
    private BigDecimal subtotal;

    @BeforeEach
    public void configurar() {
        dtCarrito = new DtCarrito();
        producto = new DtProducto();
        cantidad = 1;
        subtotal = new BigDecimal("220.34");
    }

    @DisplayName("Crear DtCarritoProducto - Getter/Setter")
    @Test
    void crear() {
        dtCarritoProducto.setDtCarrito(dtCarrito);
        dtCarritoProducto.setDtProducto(producto);
        dtCarritoProducto.setCantidad(cantidad);
        dtCarritoProducto.setSubtotal(subtotal);

        ServicioVerificacion.getInstance().verificar(dtCarrito, dtCarritoProducto.getDtCarrito());
        ServicioVerificacion.getInstance().verificar(producto, dtCarritoProducto.getDtProducto());
        ServicioVerificacion.getInstance().verificar(cantidad, dtCarritoProducto.getCantidad());
        ServicioVerificacion.getInstance().verificar(subtotal, dtCarritoProducto.getSubtotal());
    }
}