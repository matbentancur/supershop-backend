package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.Archivo;

public class ManejadorArchivo {

    private static ManejadorArchivo instance = null;

    private ManejadorArchivo() {
    }

    public static ManejadorArchivo getInstance() {
        if (instance == null) {
            instance = new ManejadorArchivo();
        }
        return instance;
    }

    public Archivo obtenerArchivo(Long id) {
        return (Archivo) ManejadorBase.getInstance().obtenerBean(new Archivo(), "id", id);
    }

    public <T> Archivo obtenerArchivo(String parametro, T valor) {
        return (Archivo) ManejadorBase.getInstance().obtenerBean(new Archivo(), parametro, valor);
    }

    public void crearArchivo(Archivo archivo) throws Exception {
        ManejadorBase.getInstance().crearBean(archivo);
    }

    public void modificarArchivo(Archivo archivo) throws Exception {
        ManejadorBase.getInstance().modificarBean(archivo);
    }

    public void borrarArchivo(Archivo archivo) throws Exception {
        ManejadorBase.getInstance().borrarBean(archivo);
    }

}
