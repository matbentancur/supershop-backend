package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtProducto;
import grupo1.supershop.datatypes.DtStock;
import grupo1.supershop.datatypes.DtSucursal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
        name = "stock"
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Stock extends Base {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Getter
    @Setter
    private Producto producto;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Getter
    @Setter
    private Sucursal sucursal;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    @Getter
    @Setter
    private Integer cantidad;

    public Stock(DtStock dtStock) {
        this.cantidad = dtStock.getCantidad();
    }

    @Override
    public DtBase getDataType() {
        return new DtStock(
                (DtProducto) producto.getDataType(),
                (DtSucursal) sucursal.getDataType(),
                cantidad
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtStock(
                (DtProducto) producto.getSimpleDataType(),
                (DtSucursal) sucursal.getSimpleDataType(),
                cantidad
        );
    }

    public void agregarCantidad(Integer cantidad) {
        this.cantidad = this.cantidad + cantidad;
        this.controlarCantidad();
    }

    public void quitarCantidad(Integer cantidad) {
        this.cantidad = this.cantidad - cantidad;
        this.controlarCantidad();
    }

    public void modificarCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        this.controlarCantidad();
    }

    private void controlarCantidad() {
        if (this.cantidad < 0) {
            this.cantidad = 0;
        }
    }

}
