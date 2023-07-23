package grupo1.supershop.datatypes;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtArchivo extends DtIdentificable {

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String mime;

    @Getter
    @Setter
    private String extension;

    @Getter
    @Setter
    private Long peso;

    @Getter
    @Setter
    private String ubicacion;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String publicId;

    public DtArchivo(String nombre, String mime, String extension, Long peso, String ubicacion, String url, String publicId, Long id, Date creacion) {
        super(id, creacion);
        this.nombre = nombre;
        this.mime = mime;
        this.extension = extension;
        this.peso = peso;
        this.ubicacion = ubicacion;
        this.url = url;
        this.publicId = publicId;
    }

}
