package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DtArchivoTest")
class DtArchivoTest extends ConfiguracionTest {

    private String nombre;
    private String mime;
    private String extension;
    private Long peso;
    private String ubicacion;

    @BeforeEach
    public void configurar() {
        nombre = "Nombre archivo";
        mime = "Mime archivo";
        extension = "Extension archivo";
        peso = 2L;
        ubicacion = "Ubicacion archivo";
    }

    @DisplayName("Crear DtArchivo - Getter/Setter")
    @Test
    void crear() {
        dtArchivo.setNombre(nombre);
        dtArchivo.setMime(mime);
        dtArchivo.setExtension(extension);
        dtArchivo.setPeso(peso);
        dtArchivo.setUbicacion(ubicacion);

        ServicioVerificacion.getInstance().verificar(nombre, dtArchivo.getNombre());
        ServicioVerificacion.getInstance().verificar(mime, dtArchivo.getMime());
        ServicioVerificacion.getInstance().verificar(extension, dtArchivo.getExtension());
        ServicioVerificacion.getInstance().verificar(peso, dtArchivo.getPeso());
        ServicioVerificacion.getInstance().verificar(ubicacion, dtArchivo.getUbicacion());
    }
}