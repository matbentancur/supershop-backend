package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtSucursal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DtAdministradorSucursalTest")
class DtAdministradorSucursalTest extends ConfiguracionTest {

    private DtSucursal dtSucursal;

    @BeforeEach
    public void configurar() {
        dtSucursal = new DtSucursal();
    }

    @DisplayName("Crear DtAdministradorSucursal - Getter/Setter")
    @Test
    void crearDtAdministradorSucursal() {
        dtAdministradorSucursal.setDtSucursal(dtSucursal);

        ServicioVerificacion.getInstance().verificar(dtSucursal, dtAdministradorSucursal.getDtSucursal());
    }
}