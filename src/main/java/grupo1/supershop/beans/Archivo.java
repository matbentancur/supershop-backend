package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtArchivo;
import grupo1.supershop.datatypes.DtBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
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
        name = "archivo"
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Archivo extends Identificable {

    @Column(nullable = false, length = 128)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String nombre;

    @Column(nullable = false, length = 255)
    @NotNull
    @NotBlank
    @Getter
    @Setter
    private String mime;

    @Column(nullable = false, length = 8)
    @NotNull
    @NotBlank
    @Getter
    @Setter
    private String extension;

    @Column(nullable = false)
    @NotNull
    @Max(2097152)
    @Getter
    @Setter
    private Long peso;

    @Column(nullable = false, length = 255)
    @NotNull
    @NotBlank
    @Getter
    @Setter
    private String ubicacion;

    @Column(nullable = false, length = 255)
    @NotNull
    @NotBlank
    @Getter
    @Setter
    private String url;

    @Column(nullable = true, length = 255, name = "public_id")
    @Getter
    @Setter
    private String publicId;

    @Override
    public DtBase getDataType() {
        return new DtArchivo(
                nombre,
                mime,
                extension,
                peso,
                ubicacion,
                url,
                publicId,
                this.getId(),
                this.getCreacion()
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtArchivo(
                nombre,
                null,
                null,
                null,
                null,
                url,
                null,
                this.getId(),
                null
        );
    }

}
