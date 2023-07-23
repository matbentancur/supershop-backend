package grupo1.supershop.datatypes;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtCompraProducto extends DtBase {

    @Getter
    @Setter
    private DtCompra dtCompra;

    @Getter
    @Setter
    private DtProducto dtProducto;

    @Getter
    @Setter
    private String productoNombre;

    @Getter
    @Setter
    private BigDecimal precioUnitario;

    @Getter
    @Setter
    private Integer cantidad;

    @Getter
    @Setter
    private DtPromocion dtPromocion;

    @Getter
    @Setter
    private String promocionNombre;

    @Getter
    @Setter
    private Integer descuentoPorcentual;

    @Getter
    @Setter
    private BigDecimal descuentoPromocion;

    @Getter
    @Setter
    private BigDecimal subtotal;

    public DtCompraProducto(DtCompra dtCompra, DtProducto dtProducto, String productoNombre, BigDecimal precioUnitario, Integer cantidad, DtPromocion dtPromocion, String promocionNombre, Integer descuentoPorcentual, BigDecimal descuentoPromocion, BigDecimal subtotal) {
        this.dtCompra = dtCompra;
        this.dtProducto = dtProducto;
        this.productoNombre = productoNombre;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.dtPromocion = dtPromocion;
        this.promocionNombre = promocionNombre;
        this.descuentoPorcentual = descuentoPorcentual;
        this.descuentoPromocion = descuentoPromocion;
        this.subtotal = subtotal;
    }

}
