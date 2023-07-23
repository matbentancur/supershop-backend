package grupo1.supershop.fabricas;

import grupo1.supershop.controladores.ControladorActividad;
import grupo1.supershop.interfaces.controladores.IControladorActividad;

public class FabricaActividad {
    
    private FabricaActividad() {
    }
    
    public static FabricaActividad getInstance() {
        return FabricaActividadHolder.INSTANCE;
    }
    
    private static class FabricaActividadHolder {
        private static final FabricaActividad INSTANCE = new FabricaActividad();
    }
    
    public static IControladorActividad getIControladorActividad() {
            return new ControladorActividad();
    }
}
