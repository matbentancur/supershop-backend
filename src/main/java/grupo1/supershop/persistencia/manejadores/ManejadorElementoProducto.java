package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.ElementoProducto;

public class ManejadorElementoProducto {

    private static ManejadorElementoProducto instance = null;

    private ManejadorElementoProducto() {
    }

    public static ManejadorElementoProducto getInstance() {
        if (instance == null) {
            instance = new ManejadorElementoProducto();
        }
        return instance;
    }
   
    public void crearElementoProducto(ElementoProducto elementoProducto) throws Exception {
        ManejadorBase.getInstance().crearBean(elementoProducto);
    }

    public void modificarElementoProducto(ElementoProducto elementoProducto) throws Exception {
        ManejadorBase.getInstance().modificarBean(elementoProducto);
    }

    public void borrarElementoProducto(ElementoProducto elementoProducto) throws Exception {
        ManejadorBase.getInstance().borrarBean(elementoProducto);
    }

}
