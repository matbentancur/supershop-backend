package grupo1.supershop.interfaces.controladores;

import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtStock;
import java.util.List;

public interface IControladorStock {

    public DtStock obtenerStock(Long sucursalId, Long productoId, String secreto);

    public List<DtStock> listarStockSucursal(Long sucursalId, String secreto);

    public List<DtStock> listarStockProducto(Long productoId, String secreto);

    public List<DtStock> listarSinStock(String secreto);

    public List<DtStock> listarSinStockSucursal(Long sucursalId, String secreto);

    public List<DtStock> listarSinStockProducto(Long productoId, String secreto);

    public DtMensajeSistema agregarStock(Long sucursalId, Long productoId, Integer cantidad, String secreto);

    public DtMensajeSistema quitarStock(Long sucursalId, Long productoId, Integer cantidad, String secreto);

    public DtMensajeSistema modificarStock(Long sucursalId, Long productoId, Integer cantidad, String secreto);

}
