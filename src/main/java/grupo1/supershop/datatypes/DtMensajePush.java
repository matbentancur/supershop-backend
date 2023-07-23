package grupo1.supershop.datatypes;

import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public final class DtMensajePush {

    @Getter
    @Setter
    private String asunto;

    @Getter
    @Setter
    private String contenido;

    @Getter
    @Setter
    private Map<String, String> datos;

    @Getter
    @Setter
    private String imagen;

    public DtMensajePush(String asunto, String contenido, Map<String, String> datos, String imagen) {
        this.asunto = asunto;
        this.contenido = contenido;
        this.datos = datos;
        this.imagen = imagen;
    }

    public DtMensajePush(String asunto, String contenido) {
        this.asunto = asunto;
        this.contenido = contenido;
    }

}
