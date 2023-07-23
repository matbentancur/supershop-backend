package grupo1.supershop.controladores;

import grupo1.supershop.beans.Compra;
import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.interfaces.controladores.IControladorPayPal;
import grupo1.supershop.persistencia.manejadores.ManejadorCompra;
import grupo1.supershop.servicios.ServicioPayPal;
import grupo1.supershop.servicios.ServicioPermiso;
import grupo1.supershop.servicios.ServicioSesion;
import grupo1.supershop.validadores.ValidadorCompra;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

public class ControladorPayPal implements IControladorPayPal {

    @Override
    public String crearOrden(Long compraId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return null;
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
                return null;
            }

            //OBTIENE COMPRA
            Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
            if (compra == null) {
                return null;
            }

            //VALIDA
            Comprador comprador = (Comprador) usuario;
            DtMensajeSistema dtMensajeSistema = ValidadorCompra.getInstance().validarCrearOrdenPayPal(compra, comprador);
            if (!dtMensajeSistema.isExitoso()) {
                return null;
            }

            //OBTENGO RESPUESTA DE PAYPAL
            String stringJSONObject = ServicioPayPal.getInstance().createOrder(compra.getTotal().toString());
            JSONObject jSONObject = new JSONObject(stringJSONObject);
            String paypalOrderId = jSONObject.getString("id");

            //MODIFICO LA COMPRA
            compra.setPaypalOrdenId(paypalOrderId);
            ManejadorCompra.getInstance().modificarCompra(compra);

            return stringJSONObject;
        } catch (Exception ex) {
            Logger.getLogger(ControladorPayPal.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public String capturarPago(Long compraId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return null;
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
                return null;
            }

            //OBTIENE COMPRA
            Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
            if (compra == null) {
                return null;
            }

            //VALIDA
            Comprador comprador = (Comprador) usuario;
            DtMensajeSistema dtMensajeSistema = ValidadorCompra.getInstance().validarCapturarPagoPayPal(compra, comprador);
            if (!dtMensajeSistema.isExitoso()) {
                return null;
            }

            //OBTENGO RESPUESTA DE PAYPAL
            String stringJSONObject = ServicioPayPal.getInstance().capturePaymentOrder(compra.getPaypalOrdenId());
            try {
                JSONObject jSONObject = new JSONObject(stringJSONObject);
                String paypalOrderStatus = jSONObject.getString("status");
                if (paypalOrderStatus.equals("COMPLETED")) {
                    //MODIFICO LA COMPRA
                    compra.setPago(true);
                    ManejadorCompra.getInstance().modificarCompra(compra);
                }
            } catch (Exception e) {
                return stringJSONObject;
            }

            return stringJSONObject;

        } catch (Exception ex) {
            Logger.getLogger(ControladorPayPal.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
