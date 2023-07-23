package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtComprador;
import grupo1.supershop.datatypes.DtPromocion;
import grupo1.supershop.datatypes.DtSucursal;
import grupo1.supershop.datatypes.DtVale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@DisplayName("DtCompraSucursalTest")
class DtCompraSucursalTest extends ConfiguracionTest {

    private String estado;
    private String observacionesCliente;
    private String observacionesSucursal;
    private boolean pago;
    private String metodoPago;
    private DtComprador dtComprador;
    private DtSucursal dtSucursal;
    private Set<DtVale> vales;
    private BigDecimal costoCompra;
    private BigDecimal descuentoVales;
    private BigDecimal descuentoPromociones;
    private BigDecimal total;

    @BeforeEach
    public void configurar() {
        prepararDatos();
    }

    @DisplayName("Crear DtCompraSucursal - Getter/Setter")
    @Test
    void crear() {
        dtCompraSucursal.setEstado(estado);
        dtCompraSucursal.setObservacionesCliente(observacionesCliente);
        dtCompraSucursal.setObservacionesSucursal(observacionesSucursal);
        dtCompraSucursal.setPago(pago);
        dtCompraSucursal.setMetodoPago(metodoPago);
        dtCompraSucursal.setDtComprador(dtComprador);
        dtCompraSucursal.setDtSucursal(dtSucursal);
        dtCompraSucursal.setVales(vales);
        dtCompraSucursal.setCostoCompra(costoCompra);
        dtCompraSucursal.setDescuentoVales(descuentoVales);
        dtCompraSucursal.setDescuentoPromociones(descuentoPromociones);
        dtCompraSucursal.setTotal(total);

        ServicioVerificacion.getInstance().verificar(estado, dtCompraSucursal.getEstado());
        ServicioVerificacion.getInstance().verificar(observacionesCliente, dtCompraSucursal.getObservacionesCliente());
        ServicioVerificacion.getInstance().verificar(observacionesSucursal, dtCompraSucursal.getObservacionesSucursal());
        ServicioVerificacion.getInstance().verificar(pago, dtCompraSucursal.isPago());
        ServicioVerificacion.getInstance().verificar(dtComprador, dtCompraSucursal.getDtComprador());
        ServicioVerificacion.getInstance().verificar(metodoPago, dtCompraSucursal.getMetodoPago());
        ServicioVerificacion.getInstance().verificar(dtSucursal, dtCompraSucursal.getDtSucursal());
        ServicioVerificacion.getInstance().verificar(vales, dtCompraSucursal.getVales());
        ServicioVerificacion.getInstance().verificar(costoCompra, dtCompraSucursal.getCostoCompra());
        ServicioVerificacion.getInstance().verificar(descuentoVales, dtCompraSucursal.getDescuentoVales());
        ServicioVerificacion.getInstance().verificar(descuentoPromociones, dtCompraSucursal.getDescuentoPromociones());
        ServicioVerificacion.getInstance().verificar(total, dtCompraSucursal.getTotal());
    }

    private void prepararDatos() {
        estado = "Estado de compra";
        observacionesCliente = "Observaciones del cliente";
        observacionesSucursal = "Observaciones de la sucursal";
        pago = true;
        metodoPago = "Metodo de pago";
        dtComprador = new DtComprador();
        dtSucursal = new DtSucursal();
        vales = new HashSet<>();
        DtVale vale1 = new DtVale();
        DtVale vale2 = new DtVale();
        vales.add(vale1);
        vales.add(vale2);
        DtPromocion promocion1 = new DtPromocion();
        DtPromocion promocion2 = new DtPromocion();
        costoCompra = new BigDecimal("100.00");
        descuentoVales = new BigDecimal("10.00");
        descuentoPromociones = new BigDecimal("20.00");
        total = new BigDecimal("70.00");
    }
}