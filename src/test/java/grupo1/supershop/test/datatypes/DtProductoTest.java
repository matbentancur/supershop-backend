package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtArchivo;
import grupo1.supershop.datatypes.DtCategoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@DisplayName("DtProductoTest")
class DtProductoTest extends ConfiguracionTest {

    private String nombre;
    private String descripcion;
    private String barcode;
    private Integer peso;
    private BigDecimal precio;
    private DtCategoria dtCategoria;
    private Set<DtArchivo> imagenes;

    @BeforeEach
    public void configurar() {
        nombre = "Nombre del producto";
        descripcion = "Descripcion del producto";
        barcode = "123456789";
        peso = 500;
        precio = new BigDecimal("99.99");
        dtCategoria = new DtCategoria();
        imagenes = new HashSet<>();
    }

    @DisplayName("Crear DtProducto - Getter/Setter")
    @Test
    void crear() {
        dtProducto.setNombre(nombre);
        dtProducto.setDescripcion(descripcion);
        dtProducto.setBarcode(barcode);
        dtProducto.setPeso(peso);
        dtProducto.setPrecio(precio);
        dtProducto.setDtCategoria(dtCategoria);
        dtProducto.setImagenes(imagenes);

        ServicioVerificacion.getInstance().verificar(nombre, dtProducto.getNombre());
        ServicioVerificacion.getInstance().verificar(descripcion, dtProducto.getDescripcion());
        ServicioVerificacion.getInstance().verificar(barcode, dtProducto.getBarcode());
        ServicioVerificacion.getInstance().verificar(peso, dtProducto.getPeso());
        ServicioVerificacion.getInstance().verificar(precio, dtProducto.getPrecio());
        ServicioVerificacion.getInstance().verificar(dtCategoria, dtProducto.getDtCategoria());
        ServicioVerificacion.getInstance().verificar(imagenes, dtProducto.getImagenes());
    }
}