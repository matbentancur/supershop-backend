package grupo1.supershop.beans;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public abstract class ElementoProducto extends Base {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Getter
    @Setter
    private Producto producto;

    @Column(nullable = false, length = 128, name = "producto_nombre")
    @NotNull
    @NotBlank
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String productoNombre;

    @Column(nullable = false, name = "precio_unitario")
    @NotNull
    @Positive
    @Min(1)
    @Getter
    @Setter
    private BigDecimal precioUnitario;

    @Column(nullable = false)
    @NotNull
    @Positive
    @Min(1)
    @Getter
    @Setter
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Promocion promocion;

    @Column(nullable = true, length = 128, name = "promocion_nombre")
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String promocionNombre;

    @Column(nullable = true, name = "descuento_porcentual")
    @Min(0)
    @Max(99)
    @Getter
    @Setter
    private Integer descuentoPorcentual;

    @Column(nullable = true, name = "descuento_promocion")
    @Min(0)
    @Getter
    @Setter
    private BigDecimal descuentoPromocion;

    @Column(nullable = false)
    @NotNull
    @Positive
    @Min(1)
    @Getter
    @Setter
    private BigDecimal subtotal;

    public ElementoProducto(ElementoProducto elementoProducto) {
        this.producto = elementoProducto.getProducto();
        this.productoNombre = elementoProducto.getProductoNombre();
        this.precioUnitario = elementoProducto.getPrecioUnitario();
        this.cantidad = elementoProducto.getCantidad();
        this.promocion = elementoProducto.getPromocion();
        this.promocionNombre = elementoProducto.getPromocionNombre();
        this.descuentoPorcentual = elementoProducto.getDescuentoPorcentual();
        this.descuentoPromocion = elementoProducto.getDescuentoPromocion();
        this.subtotal = elementoProducto.getSubtotal();
    }

    public void agregarCantidad(Integer cantidad) {
        this.cantidad = this.cantidad + cantidad;
        this.controlarCantidad();
        this.calcularSubtotal();
    }

    public void quitarCantidad(Integer cantidad) {
        this.cantidad = this.cantidad - cantidad;
        this.controlarCantidad();
        this.calcularSubtotal();
    }

    public void modificarCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        this.controlarCantidad();
        this.calcularSubtotal();
    }

    private void controlarCantidad() {
        if (this.cantidad < 0) {
            this.cantidad = 0;
        }
        if (this.cantidad > 200) {
            this.cantidad = 200;
        }
    }

    public void calcularDescuentoPromocion(Integer cantidadPromocion) {
        this.descuentoPromocion = new BigDecimal(0);

        Integer multiplicadorCantidad = this.cantidad / cantidadPromocion;

        BigDecimal multiplicadorDescuento = this.precioUnitario.multiply(BigDecimal.valueOf(multiplicadorCantidad));

        BigDecimal multiplicadorPorcentual = BigDecimal.valueOf(this.descuentoPorcentual).movePointLeft(2);

        this.descuentoPromocion = multiplicadorDescuento.multiply(multiplicadorPorcentual);
    }

    public void calcularSubtotal() {
        this.subtotal = this.precioUnitario.multiply(BigDecimal.valueOf(this.cantidad));
        if (this.descuentoPromocion != null) {
            this.subtotal = this.subtotal.subtract(descuentoPromocion);
        }
    }

}
