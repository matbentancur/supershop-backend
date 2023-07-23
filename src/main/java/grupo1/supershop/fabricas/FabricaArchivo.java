package grupo1.supershop.fabricas;

import grupo1.supershop.controladores.ControladorArchivo;
import grupo1.supershop.interfaces.controladores.IControladorArchivo;

public class FabricaArchivo {
    
    private FabricaArchivo() {
    }
    
    public static FabricaArchivo getInstance() {
        return FabricaArchivoHolder.INSTANCE;
    }
    
    private static class FabricaArchivoHolder {
        private static final FabricaArchivo INSTANCE = new FabricaArchivo();
    }
    
    public static IControladorArchivo getIControladorArchivo() {
            return new ControladorArchivo();
    }
}
