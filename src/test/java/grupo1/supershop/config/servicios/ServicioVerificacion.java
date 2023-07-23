package grupo1.supershop.config.servicios;

import grupo1.supershop.config.properties.PropertyReader;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Set;

public class ServicioVerificacion {

    private static ServicioVerificacion instance = null;

    private ServicioVerificacion() {
    }

    public static ServicioVerificacion getInstance() {
        if (instance == null) {
            instance = new ServicioVerificacion();
        }
        return instance;
    }

    private String mensajeVerificacion(String key) {
        return PropertyReader.getInstance().getTestMessage(key);
    }

    public void verificarTrue(boolean valor) {
        Assertions.assertTrue(valor, mensajeVerificacion("Testing.verificacion.true"));
    }

    public void verificarFalse(boolean valor) {
        Assertions.assertFalse(valor, mensajeVerificacion("Testing.verificacion.false"));
    }

    public <T1> void verificar(T1 valorEsperado, T1 valorActual) {
        Assertions.assertEquals(valorEsperado, valorActual, mensajeVerificacion("Testing.verificacion.inconsistencia"));
    }

    public <T1> void verificar(Set<T1> valorEsperado, Set<T1> valorActual) {
        Assertions.assertNotNull(valorEsperado, mensajeVerificacion("Testing.verificacion.null"));
        Assertions.assertNotNull(valorActual, mensajeVerificacion("Testing.verificacion.null"));
        Assertions.assertEquals(valorEsperado.size(), valorActual.size(), mensajeVerificacion("Testing.verificacion.tamanio"));
        Assertions.assertTrue(valorEsperado.containsAll(valorActual), mensajeVerificacion("Testing.verificacion.set"));
    }

    public <T1> void verificar(List<T1> valorEsperado, List<T1> valorActual) {
        Assertions.assertEquals(valorEsperado, valorActual, mensajeVerificacion("Testing.verificacion.inconsistencia"));
    }
}
