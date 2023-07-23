package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;

import grupo1.supershop.datatypes.DtSucursal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DtAtencionClienteTest")
class DtAtencionClienteTest extends ConfiguracionTest {

    private DtSucursal dtSucursal;

    @BeforeEach
    public void configurar() {
        dtSucursal = new DtSucursal();
    }

    @DisplayName("Crear DtAtencionCliente - Getter/Setter")
    @Test
    void crear() {
        dtAtencionCliente.setDtSucursal(dtSucursal);

        ServicioVerificacion.getInstance().verificar(dtSucursal, dtAtencionCliente.getDtSucursal());
    }
}