package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtCompra;
import grupo1.supershop.datatypes.DtCompraProducto;
import grupo1.supershop.datatypes.DtProducto;
import grupo1.supershop.datatypes.DtPromocion;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
        name = "compra_producto"
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CompraProducto extends ElementoProducto {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Getter
    @Setter
    private Compra compra;

    public CompraProducto(Compra compra, CarritoProducto carritoProducto) {
        super(carritoProducto);
        this.compra = compra;
    }

    @Override
    public DtBase getDataType() {
        return new DtCompraProducto(
                (DtCompra) compra.getDataType(),
                (DtProducto) this.getProducto().getDataType(),
                this.getProductoNombre(),
                this.getPrecioUnitario(),
                this.getCantidad(),
                this.getPromocion() != null ? (DtPromocion) this.getPromocion().getDataType() : null,
                this.getPromocionNombre(),
                this.getDescuentoPorcentual(),
                this.getDescuentoPromocion(),
                this.getSubtotal()
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtCompraProducto(
                null,
                null,
                this.getProductoNombre(),
                this.getPrecioUnitario(),
                this.getCantidad(),
                null,
                this.getPromocionNombre(),
                this.getDescuentoPorcentual(),
                this.getDescuentoPromocion(),
                this.getSubtotal()
        );
    }

}
