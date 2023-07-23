package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtCarrito;
import grupo1.supershop.datatypes.DtComprador;
import grupo1.supershop.datatypes.DtSucursal;
import grupo1.supershop.persistencia.manejadores.ManejadorCarritoProducto;
import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
        name = "carrito",
        indexes = {
            @Index(columnList = "comprador_usuario_id, sucursal_id", unique = true)
        }
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class Carrito extends Base {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Getter
    @Setter
    private Comprador comprador;

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
    private BigDecimal total;

    @Override
    public DtBase getDataType() {
        return new DtCarrito(
                (DtComprador) comprador.getDataType(),
                (DtSucursal) sucursal.getDataType(),
                total
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtCarrito(
                (DtComprador) comprador.getSimpleDataType(),
                (DtSucursal) sucursal.getSimpleDataType(),
                total
        );
    }

    public void modificarTotal(BigDecimal total) {
        this.total = total;
    }

    public void calcularTotal() {
        List<CarritoProducto> lista = ManejadorCarritoProducto.getInstance().listarCarritoProductos("carrito.comprador.id", this.getComprador().getId());
        BigDecimal suma = new BigDecimal(0);
        for (CarritoProducto carritoProducto : lista) {
            suma = suma.add(carritoProducto.getSubtotal());
        }
        this.modificarTotal(suma);
    }

}
