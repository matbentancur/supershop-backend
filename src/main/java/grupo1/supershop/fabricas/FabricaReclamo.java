package grupo1.supershop.fabricas;

import grupo1.supershop.controladores.ControladorReclamo;
import grupo1.supershop.interfaces.controladores.IControladorReclamo;

public class FabricaReclamo {
    
    private FabricaReclamo() {
    }
    
    public static FabricaReclamo getInstance() {
        return FabricaReclamoHolder.INSTANCE;
    }
    
    private static class FabricaReclamoHolder {
        private static final FabricaReclamo INSTANCE = new FabricaReclamo();
    }
    
    public static IControladorReclamo getIControladorReclamo() {
            return new ControladorReclamo();
    }
}
