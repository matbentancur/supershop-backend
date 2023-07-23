package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;

import grupo1.supershop.datatypes.DtProducto;
import grupo1.supershop.datatypes.DtSucursal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DtStockTest")
class DtStockTest extends ConfiguracionTest {

    private DtProducto dtProducto;
    private DtSucursal dtSucursal;
    private Integer cantidad;

    @BeforeEach
    public void configurar() {
        dtProducto = new DtProducto();
        dtSucursal = new DtSucursal();
        cantidad = 10;
    }

    @DisplayName("Crear DtStock - Getter/Setter")
    @Test
    void crear() {
        dtStock.setDtProducto(dtProducto);
        dtStock.setDtSucursal(dtSucursal);
        dtStock.setCantidad(cantidad);

        ServicioVerificacion.getInstance().verificar(dtProducto, dtStock.getDtProducto());
        ServicioVerificacion.getInstance().verificar(dtSucursal, dtStock.getDtSucursal());
        ServicioVerificacion.getInstance().verificar(cantidad, dtStock.getCantidad());
    }
}