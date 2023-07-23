package grupo1.supershop.servicios;

import grupo1.supershop.beans.Base;
import grupo1.supershop.datatypes.DtBase;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServicioDataType {

    private static ServicioDataType instance = null;

    private ServicioDataType() {
    }

    public static ServicioDataType getInstance() {
        if (instance == null) {
            instance = new ServicioDataType();
        }
        return instance;
    }

    public <T extends DtBase> List<T> beanToDataType(List<? extends Base> listaBase) {

        List<T> listaDtBase = new ArrayList<>();

        if (listaBase != null) {
            if (!listaBase.isEmpty()) {
                for (Base base : listaBase) {
                    listaDtBase.add((T) base.getDataType());
                }
            }
        }

        return listaDtBase;
    }

    public <T extends DtBase> List<T> beanToSimpleDataType(List<? extends Base> listaBase) {

        List<T> listaDtBase = new ArrayList<>();

        if (listaBase != null) {
            if (!listaBase.isEmpty()) {
                for (Base base : listaBase) {
                    listaDtBase.add((T) base.getSimpleDataType());
                }
            }
        }

        return listaDtBase;
    }

    public <T extends DtBase> List<T> beanSetToDataTypeList(Set<? extends Base> conjuntoBase) {

        List<T> listaDtBase = new ArrayList<>();

        if (conjuntoBase != null) {
            if (!conjuntoBase.isEmpty()) {
                for (Base base : conjuntoBase) {
                    listaDtBase.add((T) base.getDataType());
                }
            }
        }

        return listaDtBase;
    }

    public <T extends DtBase> Set<T> beanSetToDataTypeSet(Set<? extends Base> conjuntoBase) {

        Set<T> conjuntoDtBase = new HashSet<T>();

        if (conjuntoBase != null) {
            if (!conjuntoBase.isEmpty()) {
                for (Base base : conjuntoBase) {
                    conjuntoDtBase.add((T) base.getDataType());
                }
            }
        }

        return conjuntoDtBase;
    }

    public <T extends DtBase> Set<T> beanSetToSimpleDataTypeSet(Set<? extends Base> conjuntoBase) {

        Set<T> conjuntoDtBase = new HashSet<T>();

        if (conjuntoBase != null) {
            if (!conjuntoBase.isEmpty()) {
                for (Base base : conjuntoBase) {
                    conjuntoDtBase.add((T) base.getSimpleDataType());
                }
            }
        }

        return conjuntoDtBase;
    }

//    public <T extends Base> List<T> dataTypeToBean(List<? extends DtBase> listaDtBase) {
//
//        List<T> listaBase = new ArrayList<>();
//
//        for (DtBase dtBase : listaDtBase) {
//            T bean = (T) ManejadorBase.getInstance().obtenerBean(dtBase);
//            if (bean != null) {
//                listaBase.add(bean);
//            }
//        }
//
//        return listaBase;
//    }

}
