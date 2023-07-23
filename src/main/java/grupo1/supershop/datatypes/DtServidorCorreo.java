package grupo1.supershop.datatypes;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtServidorCorreo extends DtBorrable {

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String servidor;

    @Getter
    @Setter
    private Integer puerto;

    @Getter
    @Setter
    private String usuario;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String seguridad;

    @Getter
    @Setter
    private String identificacion;

    @Getter
    @Setter
    private String desdeCorreo;

    @Getter
    @Setter
    private String desdeNombre;

    public DtServidorCorreo(String nombre, String servidor, Integer puerto, String usuario, String password, String seguridad, String identificacion, String desdeCorreo, String desdeNombre, Long id, Date creacion, boolean borrado) {
        super(id, creacion, borrado);
        this.nombre = nombre;
        this.servidor = servidor;
        this.puerto = puerto;
        this.usuario = usuario;
        this.password = password;
        this.seguridad = seguridad;
        this.identificacion = identificacion;
        this.desdeCorreo = desdeCorreo;
        this.desdeNombre = desdeNombre;
    }

}
