package grupo1.supershop.interfaces.controladores;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtCompra;
import grupo1.supershop.datatypes.DtCompraProducto;
import grupo1.supershop.datatypes.DtVale;
import java.util.List;

public interface IControladorCompra {

    public DtCompra obtenerCompra(Long compraId, String secreto);

    public DtCompra obtenerMiCompra(Long compraId, String secreto);

    public DtCompra obtenerMiCompraProceso(String secreto);

    public List<DtCompraProducto> listarMiCompraProductos(Long compraId, String secreto);

    public List<DtCompraProducto> listarCompraProductos(Long compraId, String secreto);

    public List<DtCompra> listarMisCompras(String secreto);

    public List<DtCompra> listarMisComprasPendientes(String secreto);

    public List<DtCompra> listarMisComprasConfirmadas(String secreto);

    public List<DtCompra> listarMisComprasConcluidas(String secreto);

    public List<DtCompra> listarMisComprasDevueltas(String secreto);

    public List<DtCompra> listarComprasUsuario(Long compradorId, String secreto);

    public List<DtCompra> listarComprasUsuarioProceso(Long compradorId, String secreto);

    public List<DtCompra> listarComprasUsuarioPendientes(Long compradorId, String secreto);

    public List<DtCompra> listarComprasUsuarioConfirmadas(Long compradorId, String secreto);

    public List<DtCompra> listarComprasUsuarioConcluidas(Long compradorId, String secreto);

    public List<DtCompra> listarComprasUsuarioDevueltas(Long compradorId, String secreto);

    public List<DtCompra> listarComprasSucursal(Long sucursalId, String secreto);

    public List<DtCompra> listarComprasSucursalProceso(Long sucursalId, String secreto);

    public List<DtCompra> listarComprasSucursalPendientes(Long sucursalId, String secreto);

    public List<DtCompra> listarComprasSucursalConfirmadas(Long sucursalId, String secreto);

    public List<DtCompra> listarComprasSucursalConcluidas(Long sucursalId, String secreto);

    public List<DtCompra> listarComprasSucursalDevueltas(Long sucursalId, String secreto);

    public List<DtVale> listarValesAplicados(Long compraId, String secreto);

    public List<DtVale> listarMisValesAplicados(Long compraId, String secreto);
    
    public List<DtActividad> listarActividadCompra(Long compraId, String secreto);

    public DtMensajeSistema iniciarCompraDomicilio(String secreto);

    public DtMensajeSistema iniciarCompraSucursal(String secreto);

    public DtMensajeSistema cancelarCompra(Long compraId, String secreto);

    public DtMensajeSistema confirmarCompra(Long compraId, String secreto);

    public DtMensajeSistema concluirCompra(Long compraId, String secreto);

    public DtMensajeSistema devolverCompra(Long compraId, String secreto);

    public DtMensajeSistema aplicarMisVales(List<DtVale> listaDtVales, Long compraId, String secreto);

    public DtMensajeSistema aplicarMetodoPagoEfectivo(Long compraId, String secreto);

    public DtMensajeSistema aplicarMetodoPagoPayPal(Long compraId, String secreto);

    public DtMensajeSistema aplicarMetodoEnvio(Long compraId, Long metodoEnvioId, String secreto);

    public DtMensajeSistema modificarDomicilio(Long compraId, Long domicilioId, String secreto);

    public DtMensajeSistema aplicarObservacionesComprador(String observaciones, Long compraId, String secreto);

    public DtMensajeSistema modificarObservacionesSucursal(String observaciones, Long compraId, String secreto);

    public DtMensajeSistema finalizarCompra(Long compraId, String secreto);

    public List<DtActividad> listarActividadesComprasConcluidasSucursal(Long sucursalId, String secreto);

}
