package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtMetodoEnvio;
import grupo1.supershop.datatypes.DtModificable;
import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
        name = "metodo_envio",
        indexes = {
            @Index(columnList = "id", unique = true),
            @Index(columnList = "borrado"),
            @Index(columnList = "nombre", unique = true)
        }
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MetodoEnvio extends Borrable {

    @Column(unique = true, nullable = false, length = 128)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String nombre;

    @Column(nullable = true, length = 255)
    @Size(min = 1, max = 255)
    @Getter
    @Setter
    private String descripcion;

    @Column(nullable = false, name = "peso_maximo")
    @Positive
    @Min(1)
    @Getter
    @Setter
    //gramos o mililitros
    private Integer pesoMaximo;

    @Column(nullable = false, name = "cantidad_productos_maximo")
    @NotNull
    @Positive
    @Min(1)
    @Getter
    @Setter
    private Integer cantidadProductosMaximo;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    @Getter
    @Setter
    private BigDecimal costo;

    public MetodoEnvio(DtMetodoEnvio dtMetodoEnvio) {
        this.nombre = dtMetodoEnvio.getNombre();
        this.descripcion = dtMetodoEnvio.getDescripcion();
        this.pesoMaximo = dtMetodoEnvio.getPesoMaximo();
        this.cantidadProductosMaximo = dtMetodoEnvio.getCantidadProductosMaximo();
        this.costo = dtMetodoEnvio.getCosto();
    }

    @Override
    public DtBase getDataType() {
        return new DtMetodoEnvio(
                nombre,
                descripcion,
                pesoMaximo,
                cantidadProductosMaximo,
                costo,
                this.getId(),
                this.getCreacion(),
                this.isBorrado()
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtMetodoEnvio(
                nombre,
                null,
                null,
                null,
                costo,
                this.getId(),
                null,
                this.isBorrado()
        );
    }

    @Override
    public void modificar(DtModificable dtModificable) {
        DtMetodoEnvio dtMetodoEnvio = (DtMetodoEnvio) dtModificable;
        this.nombre = dtMetodoEnvio.getNombre();
        this.descripcion = dtMetodoEnvio.getDescripcion();
        this.pesoMaximo = dtMetodoEnvio.getPesoMaximo();
        this.cantidadProductosMaximo = dtMetodoEnvio.getCantidadProductosMaximo();
        this.costo = dtMetodoEnvio.getCosto();
    }

}
