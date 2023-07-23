package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtArchivo;
import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtModificable;
import grupo1.supershop.datatypes.DtSucursal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
        name = "sucursal",
        indexes = {
            @Index(columnList = "id", unique = true),
            @Index(columnList = "borrado"),
            @Index(columnList = "nombre", unique = true)
        }
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Sucursal extends Borrable {

    @Column(unique = true, nullable = false, length = 128)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String nombre;

    @Column(nullable = true, length = 128)
    @Size(min = 3, max = 255)
    @Email
    @Getter
    @Setter
    private String email;

    @Column(nullable = true, length = 32)
    @Size(min = 8, max = 32)
    @Getter
    @Setter
    private String telefono;

    @Column(nullable = true, length = 255)
    @Size(min = 1, max = 255)
    @Getter
    @Setter
    private String horario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "archivo_id")
    @Getter
    @Setter
    private Archivo imagen;

    @Column(nullable = true, length = 255, name = "imagen_remota")
    @Getter
    @Setter
    private String imagenRemota;

    public Sucursal(DtSucursal dtSucursal) {
        this.nombre = dtSucursal.getNombre();
        this.email = dtSucursal.getEmail();
        this.telefono = dtSucursal.getTelefono();
        this.horario = dtSucursal.getHorario();
        this.imagenRemota = dtSucursal.getImagenRemota();
    }

    @Override
    public DtBase getDataType() {
        return new DtSucursal(
                nombre,
                email,
                telefono,
                horario,
                imagen != null ? (DtArchivo) imagen.getDataType() : null,
                imagenRemota,
                this.getId(),
                this.getCreacion(),
                this.isBorrado()
        );

    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtSucursal(
                nombre,
                null,
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
        DtSucursal dtSucursal = (DtSucursal) dtModificable;
        this.nombre = dtSucursal.getNombre();
        this.email = dtSucursal.getEmail();
        this.telefono = dtSucursal.getTelefono();
        this.horario = dtSucursal.getHorario();
        this.imagenRemota = dtSucursal.getImagenRemota();
    }

    public String generarNombreImagen() {
        return this.getClass().getSimpleName() + this.getId();
    }

}
