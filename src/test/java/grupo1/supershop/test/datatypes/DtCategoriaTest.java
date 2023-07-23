package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtArchivo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DtCategoriaTest")
class DtCategoriaTest extends ConfiguracionTest {

    private String nombre;
    private String descripcion;
    private DtArchivo imagen;

    @BeforeEach
    public void configurar() {
        nombre = "Nombre de categoria";
        descripcion = "Descripcion de categoria";
        imagen = new DtArchivo();
    }

    @DisplayName("Crear DtCategoria - Getter/Setter")
    @Test
    void crear() {
        dtCategoria.setNombre(nombre);
        dtCategoria.setDescripcion(descripcion);
        dtCategoria.setImagen(imagen);

        ServicioVerificacion.getInstance().verificar(nombre, dtCategoria.getNombre());
        ServicioVerificacion.getInstance().verificar(descripcion, dtCategoria.getDescripcion());
        ServicioVerificacion.getInstance().verificar(imagen, dtCategoria.getImagen());
    }
}