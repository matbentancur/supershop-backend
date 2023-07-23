package grupo1.supershop.interfaces.controladores;

public interface IControladorPayPal {

    public String crearOrden(Long compraId, String secreto);

    public String capturarPago(Long compraId, String secreto);

}
