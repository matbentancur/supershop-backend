package grupo1.supershop.fabricas;

import grupo1.supershop.controladores.ControladorMetodoEnvio;
import grupo1.supershop.interfaces.controladores.IControladorMetodoEnvio;

public class FabricaMetodoEnvio {
    
    private FabricaMetodoEnvio() {
    }
    
    public static FabricaMetodoEnvio getInstance() {
        return FabricaMetodoEnvioHolder.INSTANCE;
    }
    
    private static class FabricaMetodoEnvioHolder {
        private static final FabricaMetodoEnvio INSTANCE = new FabricaMetodoEnvio();
    }
    
    public static IControladorMetodoEnvio getIControladorMetodoEnvio() {
            return new ControladorMetodoEnvio();
    }
}
