package grupo1.supershop.datatypes;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtSucursal extends DtBorrable {

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String telefono;

    @Getter
    @Setter
    private String horario;

    @Getter
    @Setter
    private DtArchivo imagen;

    @Getter
    @Setter
    private String imagenRemota;

    public DtSucursal(String nombre, String email, String telefono, String horario, DtArchivo imagen, String imagenRemota, Long id, Date creacion, boolean borrado) {
        super(id, creacion, borrado);
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.horario = horario;
        this.imagen = imagen;
        this.imagenRemota = imagenRemota;
    }

}
