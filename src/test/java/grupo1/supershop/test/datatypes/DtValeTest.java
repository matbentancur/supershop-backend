package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtComprador;
import grupo1.supershop.datatypes.DtSucursal;
import grupo1.supershop.servicios.ServicioFechaHora;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

@DisplayName("DtValeTest")
class DtValeTest extends ConfiguracionTest {

    private String estado;
    private String descripcion;
    private BigDecimal monto;
    private Date expira;
    private DtComprador dtComprador;
    private DtSucursal dtSucursal;

    @BeforeEach
    public void configurar() {
        estado = "Estado de la transaccion";
        descripcion = "Descripcion de la transaccion";
        monto = new BigDecimal("100.00");
        expira = ServicioFechaHora.getInstance().stringToDate("30/06/2023");
        dtComprador = new DtComprador();
        dtSucursal = new DtSucursal();
    }

    @DisplayName("Crear DtVale - Getter/Setter")
    @Test
    void crear() {
        dtVale.setEstado(estado);
        dtVale.setDescripcion(descripcion);
        dtVale.setMonto(monto);
        dtVale.setExpira(expira);
        dtVale.setDtComprador(dtComprador);
        dtVale.setDtSucursal(dtSucursal);

        ServicioVerificacion.getInstance().verificar(estado, dtVale.getEstado());
        ServicioVerificacion.getInstance().verificar(descripcion, dtVale.getDescripcion());
        ServicioVerificacion.getInstance().verificar(monto, dtVale.getMonto());
        ServicioVerificacion.getInstance().verificar(expira, dtVale.getExpira());
        ServicioVerificacion.getInstance().verificar(dtComprador, dtVale.getDtComprador());
        ServicioVerificacion.getInstance().verificar(dtSucursal, dtVale.getDtSucursal());
    }
}