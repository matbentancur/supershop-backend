package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtArchivo;
import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtCategoria;
import grupo1.supershop.datatypes.DtModificable;
import grupo1.supershop.datatypes.DtProducto;
import grupo1.supershop.persistencia.manejadores.ManejadorCategoria;
import grupo1.supershop.servicios.ServicioDataType;
import grupo1.supershop.servicios.ServicioListas;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
        name = "producto",
        indexes = {
            @Index(columnList = "id", unique = true),
            @Index(columnList = "borrado"),
            @Index(columnList = "nombre", unique = true),
            @Index(columnList = "descripcion"),
            @Index(columnList = "barcode", unique = true),
            @Index(columnList = "categoria_id")
        }
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Producto extends Borrable {

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

    @Column(unique = true, nullable = false, length = 128)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String barcode;

    @Column(nullable = false)
    @Positive
    @Min(1)
    @Getter
    @Setter
    //gramos o mililitros
    private Integer peso;

    @Column(nullable = false)
    @NotNull
    @Positive
    @Min(1)
    @Getter
    @Setter
    private BigDecimal precio;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Getter
    @Setter
    private Categoria categoria;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "archivo_id")
    )
    @Getter
    private Set<Archivo> imagenes = new HashSet<Archivo>();

    @ElementCollection
    @CollectionTable(name = "producto_imagenes_remotas")
    @Column(nullable = false, name = "imagen_remota")
    @Getter
    private Set<String> imagenesRemotas = new HashSet<String>();

    public Producto(DtProducto dtProducto) {
        this.nombre = dtProducto.getNombre();
        this.descripcion = dtProducto.getDescripcion();
        this.barcode = dtProducto.getBarcode();
        this.peso = dtProducto.getPeso();
        this.precio = dtProducto.getPrecio();
        this.categoria = ManejadorCategoria.getInstance().obtenerCategoria(dtProducto.getDtCategoria().getId());
    }

    @Override
    public DtBase getDataType() {
        Set<DtArchivo> conjuntoDtArchivo = ServicioDataType.getInstance().beanSetToDataTypeSet(this.getImagenes());
        return new DtProducto(
                nombre,
                descripcion,
                barcode,
                peso,
                precio,
                (DtCategoria) categoria.getDataType(),
                conjuntoDtArchivo,
                imagenesRemotas,
                this.getId(),
                this.getCreacion(),
                this.isBorrado()
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtProducto(
                nombre,
                null,
                null,
                null,
                precio,
                (DtCategoria) categoria.getSimpleDataType(),
                null,
                imagenesRemotas,
                this.getId(),
                null,
                this.isBorrado()
        );
    }

    @Override
    public void modificar(DtModificable dtModificable) {
        DtProducto dtProducto = (DtProducto) dtModificable;
        this.nombre = dtProducto.getNombre();
        this.descripcion = dtProducto.getDescripcion();
        this.barcode = dtProducto.getBarcode();
        this.peso = dtProducto.getPeso();
        this.precio = dtProducto.getPrecio();
        this.categoria = ManejadorCategoria.getInstance().obtenerCategoria(dtProducto.getDtCategoria().getId());
    }

    public String generarNombreImagen() {
        int indice = imagenes.size();
        return this.getClass().getSimpleName() + this.getId() + "-" + indice;
    }

    public boolean agregarImagen(Archivo imagen) {
        return ServicioListas.getInstance().agregarElemento(imagenes, imagen);
    }

    public boolean quitarImagen(Archivo imagen) {
        return ServicioListas.getInstance().quitarElemento(imagenes, imagen);
    }

    public boolean agregarImagenRemota(String url) {
        return ServicioListas.getInstance().agregarElemento(imagenesRemotas, url);
    }

    public boolean quitarImagenRemota(String url) {
        return ServicioListas.getInstance().quitarElemento(imagenesRemotas, url);
    }

}
