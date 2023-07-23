package grupo1.supershop.servicios;

import grupo1.supershop.enums.EstadoCompra;
import grupo1.supershop.enums.EstadoReclamo;
import grupo1.supershop.enums.EstadoVale;
import grupo1.supershop.enums.MetodoPago;
import java.util.ArrayList;
import java.util.List;

public class ServicioEnum {

    private static ServicioEnum instance = null;

    private ServicioEnum() {
    }

    public static ServicioEnum getInstance() {
        if (instance == null) {
            instance = new ServicioEnum();
        }
        return instance;
    }

    public List<String> getListEnum(String enumName) {
        List<String> lista = new ArrayList<String>();

        switch (enumName) {
            case "EstadoCompra":
                return this.enumToList(EstadoCompra.class);
            case "EstadoReclamo":
                return this.enumToList(EstadoReclamo.class);
            case "EstadoVale":
                return this.enumToList(EstadoVale.class);
            case "MetodoPago":
                return this.enumToList(MetodoPago.class);
            default:
                break;
        }

        return lista;
    }

    public <E extends Enum<E>> E parseEnum(Class<E> enumType, String elementoEnumString) {
        return E.valueOf(enumType, elementoEnumString);
    }

    private <E extends Enum<E>> List<String> enumToList(Class<E> enumType) {
        List<String> lista = new ArrayList<String>();
        for (E item : enumType.getEnumConstants()) {
            lista.add(item.toString());
        }
        return lista;
    }

}
