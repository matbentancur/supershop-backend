package grupo1.supershop.servicios;

import java.util.List;
import java.util.Set;

public class ServicioListas {

    private static ServicioListas instance = null;

    private ServicioListas() {
    }

    public static ServicioListas getInstance() {
        if (instance == null) {
            instance = new ServicioListas();
        }
        return instance;
    }

    public <T> boolean existeElemento(List<T> lista, T elemento) {
        return lista.contains(elemento);
    }
    
    public <T> boolean existeElemento(Set<T> conjunto, T elemento) {
        return conjunto.contains(elemento);
    }

    public <T> boolean agregarElemento(Set<T> conjunto, T elemento) {
        if (this.existeElemento(conjunto, elemento)) {
            return false;
        }
        return conjunto.add(elemento);
    }

    public <T> boolean agregarElemento(List<T> lista, T elemento) {
        if (this.existeElemento(lista, elemento)) {
            return false;
        }
        return lista.add(elemento);
    }

    public <T> boolean quitarElemento(Set<T> conjunto, T elemento) {
        if (!this.existeElemento(conjunto, elemento)) {
            return false;
        }
        return conjunto.remove(elemento);
    }

    public <T> boolean quitarElemento(List<T> lista, T elemento) {
        if (!this.existeElemento(lista, elemento)) {
            return false;
        }
        return lista.remove(elemento);
    }

}
