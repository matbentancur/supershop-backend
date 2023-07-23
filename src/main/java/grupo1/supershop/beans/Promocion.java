package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtArchivo;
import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtModificable;
import grupo1.supershop.datatypes.DtProducto;
import grupo1.supershop.datatypes.DtPromocion;
import grupo1.supershop.datatypes.DtSucursal;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
        name = "promocion",
        indexes = {
            @Index(columnList = "id", unique = true),
            @Index(columnList = "borrado"),
            @Index(columnList = "nombre", unique = true),
            @Index(columnList = "producto_id"),
            @Index(columnList = "sucursal_id")
        }
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Promocion extends Borrable {

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    @Getter
    @Setter
    private Date inicia;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    @Future
    @Getter
    @Setter
    private Date finaliza;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    @Getter
    @Setter
    private Integer cantidad;

    @Column(nullable = false, name = "descuento_porcentual")
    @NotNull
    @Min(0)
    @Max(99)
    @Getter
    @Setter
    private Integer descuentoPorcentual;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Getter
    @Setter
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Getter
    @Setter
    private Sucursal sucursal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "archivo_id")
    @Getter
    @Setter
    private Archivo imagen;

    @Column(nullable = true, length = 255, name = "imagen_remota")
    @Getter
    @Setter
    private String imagenRemota;

    public Promocion(DtPromocion dtPromocion) {
        this.nombre = dtPromocion.getNombre();
        this.descripcion = dtPromocion.getDescripcion();
        this.inicia = dtPromocion.getInicia();
        this.finaliza = dtPromocion.getFinaliza();
        this.cantidad = dtPromocion.getCantidad();
        this.descuentoPorcentual = dtPromocion.getDescuentoPorcentual();
        this.imagenRemota = dtPromocion.getImagenRemota();
    }

    @Override
    public DtBase getDataType() {
        return new DtPromocion(
                nombre,
                descripcion,
                inicia,
                finaliza,
                cantidad,
                descuentoPorcentual,
                (DtProducto) producto.getDataType(),
                (DtSucursal) sucursal.getDataType(),
                imagen != null ? (DtArchivo) imagen.getDataType() : null,
                imagenRemota,
                this.getId(),
                this.getCreacion(),
                this.isBorrado()
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtPromocion(
                nombre,
                null,
                null,
                null,
                cantidad,
                descuentoPorcentual,
                null,
                null,
                null,
                null,
                this.getId(),
                null,
                this.isBorrado()
        );
    }

    @Override
    public void modificar(DtModificable dtModificable) {
        DtPromocion dtPromocion = (DtPromocion) dtModificable;
        this.nombre = dtPromocion.getNombre();
        this.descripcion = dtPromocion.getDescripcion();
        this.inicia = dtPromocion.getInicia();
        this.finaliza = dtPromocion.getFinaliza();
        this.cantidad = dtPromocion.getCantidad();
        this.descuentoPorcentual = dtPromocion.getDescuentoPorcentual();
        this.imagenRemota = dtPromocion.getImagenRemota();
    }

    public String generarNombreImagen() {
        return this.getClass().getSimpleName() + this.getId();
    }

}
