package grupo1.supershop.instalar.cargar;

import grupo1.supershop.beans.AdministradorSucursal;
import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Reclamo;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.enums.EstadoReclamo;
import grupo1.supershop.persistencia.manejadores.ManejadorBase;
import grupo1.supershop.persistencia.manejadores.ManejadorReclamo;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import grupo1.supershop.persistencia.manejadores.ManejadorUsuario;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioFechaHora;

public class CargarReclamo {

    public void cargarReclamos() throws Exception {
        reclamos();
        cambiarFechas();
    }

    private void reclamos() throws Exception {
        AdministradorSucursal administradorSucursal = ManejadorUsuario.getInstance().obtenerAdministradorSucursal(3L);
        String[][] datos = {
                {"11","1","El producto no ha llegado","","PENDIENTE"},
                {"12","1","No se si confirme la compra","","PENDIENTE"},
                {"13","1","Pedí 3 botellas de agua salus y solo quería dos","","PENDIENTE"},
                {"14","1","No tienen mas detergente, cuando van a reponer?","","PENDIENTE"},
                {"15","1","Hice el pedido para una dirección que no es la que quería","","CONFIRMADO"},
                {"16","1","Realice un pedido hace 4 dias y todavía no ha llegado","","CONFIRMADO"},
                {"11","1","Se equivocaron en mi pedidio, pedí un vino faisan y me trajeron un santa teresa","Se realizo un envío con el producto correcto","CONCLUIDO"},
                {"12","1","Llegaron todos los productos rotos, una vergüenza","Se realizó un reenvío del pedido, con las disculpas correspondientes","CONCLUIDO"},
                {"19","1","Mi pedido no ha llegado y ya pasaron dos dias","Se constato que el pedido no habia sido enviado y se realizo el envío","CONCLUIDO"},
                {"13","1","Me trajeron un agua salus con gas y yo había pedido sin gas","Se revisó el pedido y el cliente recibió lo que había pedido","CONCLUIDO"},
                {"15","1","El repartidor no me ayudo a entrar los productos a la casa","Se le comunicó al repartidor el reclamo y se le realizó un pedido de disculpas al cliente","CONCLUIDO"},
                {"16","1","Realice el pedido pero no se si ya lo pague o no","Se revisó en el sistema y se le comunicó al cliente que el pago había sido realizado con éxito","CONCLUIDO"},
                {"18","1","No he recibido mis productos","Se le comunicó al cliente que su pedido llegara entre la 15 y 18 horas de hoy","CONCLUIDO"},
                {"11","1","El pedido que realice no es el que me llego, se confundieron","Se llamo al repartidor y se arreglo la confusión","CONCLUIDO"},
                {"17","1","El producto que pedi no es lo que esperaba quiero una devolucion","Al recibir el producto se le dio un vale al cliente con el precio del producto devuelto","CONCLUIDO"},
                {"20","1","El pedido que hice no ha llegado","Se le comunico al cliente el horario de reparto","CONCLUIDO"},
                {"21","1","Hace 5hs que me quiero comunicar con la sucursal y no atienden","Se le aclaro al cliente que el horario de atencion es de 9 a 17hs ya que estaba intentando comunicarse fuera de ese horario","CONCLUIDO"},
                {"22","1","Mi pedido no ha llegado","Se le comunicó al cliente que al hacer el pedido selecciono la opción de retirar en el local","CONCLUIDO"},
                {"23","1","No se si mi pedido quedo realizado","Se revisó en el sistema y se le comunicó al cliente que el pedido había sido realizado con éxito","CONCLUIDO"},
                {"11","2","El producto no ha llegado","","PENDIENTE"},
                {"12","2","No se si confirme la compra","","PENDIENTE"},
                {"15","2","Hice el pedido para una dirección que no es la que quería","","CONFIRMADO"},
                {"19","2","Mi pedido no ha llegado y ya pasaron dos dias","Se constato que el pedido no habia sido enviado y se realizo el envío","CONCLUIDO"},
                {"13","2","Me trajeron un agua salus con gas y yo había pedido sin gas","Se revisó el pedido y el cliente recibió lo que había pedido","CONCLUIDO"},
                {"15","2","El repartidor no me ayudo a entrar los productos a la casa","Se le comunicó al repartidor el reclamo y se le realizó un pedido de disculpas al cliente","CONCLUIDO"},
                {"16","2","Realice el pedido pero no se si ya lo pague o no","Se revisó en el sistema y se le comunicó al cliente que el pago había sido realizado con éxito","CONCLUIDO"},
                {"18","2","No he recibido mis productos","Se le comunicó al cliente que su pedido llegara entre la 15 y 18 horas de hoy","CONCLUIDO"},
                {"11","2","El pedido que realice no es el que me llego, se confundieron","Se llamo al repartidor y se arreglo la confusión","CONCLUIDO"},
                {"17","2","El producto que pedi no es lo que esperaba quiero una devolucion","Al recibir el producto se le dio un vale al cliente con el precio del producto devuelto","CONCLUIDO"},
                {"20","2","El pedido que hice no ha llegado","Se le comunico al cliente el horario de reparto","CONCLUIDO"},
                {"14","3","No tienen mas detergente, cuando van a reponer?","","PENDIENTE"},
                {"17","3","El producto que pedi no es lo que esperaba quiero una devolucion","Al recibir el producto se le dio un vale al cliente con el precio del producto devuelto","CONCLUIDO"},
                {"20","3","El pedido que hice no ha llegado","Se le comunico al cliente el horario de reparto","CONCLUIDO"},
                {"21","3","Hace 5hs que me quiero comunicar con la sucursal y no atienden","Se le aclaro al cliente que el horario de atencion es de 9 a 17hs ya que estaba intentando comunicarse fuera de ese horario","CONCLUIDO"},
                {"22","3","Mi pedido no ha llegado","Se le comunicó al cliente que al hacer el pedido selecciono la opción de retirar en el local","CONCLUIDO"},
                {"23","3","No se si mi pedido quedo realizado","Se revisó en el sistema y se le comunicó al cliente que el pedido había sido realizado con éxito","CONCLUIDO"},
                {"15","4","Hice el pedido para una dirección que no es la que quería","","CONFIRMADO"},
                {"19","4","Mi pedido no ha llegado y ya pasaron dos dias","Se constato que el pedido no habia sido enviado y se realizo el envío","CONCLUIDO"},
                {"13","4","Me trajeron un agua salus con gas y yo había pedido sin gas","Se revisó el pedido y el cliente recibió lo que había pedido","CONCLUIDO"},
                {"15","4","El repartidor no me ayudo a entrar los productos a la casa","Se le comunicó al repartidor el reclamo y se le realizó un pedido de disculpas al cliente","CONCLUIDO"}
        };

        for (String[] val : datos) {
            Comprador comprador = ManejadorUsuario.getInstance().obtenerComprador(Long.parseLong(val[0]));
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(Long.parseLong(val[1]));

            Reclamo reclamo = new Reclamo();
            reclamo.setComprador(comprador);
            reclamo.setSucursal(sucursal);
            reclamo.setDescripcion(val[2]);

            if (!val[3].isEmpty()) {
                reclamo.setConclusion(val[3]);
            }

            switch (val[4]) {
                case "PENDIENTE" -> reclamo.setEstado(EstadoReclamo.PENDIENTE);
                case "CONFIRMADO" -> reclamo.setEstado(EstadoReclamo.CONFIRMADO);
                case "CONCLUIDO" -> reclamo.setEstado(EstadoReclamo.CONCLUIDO);
            }

            ManejadorReclamo.getInstance().crearReclamo(reclamo);
            ServicioActividad.getInstance().registrarCreacion(reclamo, comprador);
        }

        for (Reclamo reclamo : ManejadorReclamo.getInstance().listarReclamos("estado", EstadoReclamo.CONFIRMADO)) {
            ServicioActividad.getInstance().registrarConfirmacion(reclamo, administradorSucursal);
        }

        for (Reclamo reclamo : ManejadorReclamo.getInstance().listarReclamos("estado", EstadoReclamo.CONCLUIDO)) {
            ServicioActividad.getInstance().registrarConfirmacion(reclamo, administradorSucursal);
        }

        for (Reclamo reclamo : ManejadorReclamo.getInstance().listarReclamos("estado", EstadoReclamo.CONCLUIDO)) {
            ServicioActividad.getInstance().registrarConclusion(reclamo, administradorSucursal);
        }

    }

    public void cambiarFechas() throws Exception {
        String[][] fechas = {
                {"1", "01/07/2023 10:00"}, {"2", "01/07/2023 10:00"}, {"3", "01/07/2023 10:00"}, {"4", "02/07/2023 10:00"}, {"5", "02/07/2023 10:00"},
                {"6", "03/07/2023 10:00"}, {"7", "04/07/2023 10:00"}, {"8", "05/07/2023 10:00"}, {"9", "05/07/2023 10:00"}, {"10", "06/07/2023 10:00"},
                {"11", "06/07/2023 10:00"}, {"12", "06/07/2023 10:00"}, {"13", "06/07/2023 10:00"}, {"14", "06/07/2023 10:00"}, {"15", "06/07/2023 10:00"},
                {"16", "07/07/2023 10:00"}, {"17", "07/07/2023 10:00"}, {"18", "07/07/2023 10:00"}, {"19", "08/07/2023 10:00"}, {"20", "08/07/2023 10:00"},
                {"21", "08/07/2023 10:00"}, {"22", "09/07/2023 10:00"}, {"23", "09/07/2023 10:00"}, {"24", "10/07/2023 10:00"}, {"25", "10/07/2023 10:00"},
                {"26", "10/07/2023 10:00"}, {"27", "10/07/2023 10:00"}, {"28", "10/07/2023 10:00"}, {"29", "10/07/2023 10:00"}, {"30", "11/07/2023 10:00"},
                {"31", "11/07/2023 10:00"}, {"32", "11/07/2023 10:00"}, {"33", "11/07/2023 10:00"}, {"34", "11/07/2023 10:00"}, {"35", "12/07/2023 10:00"},
                {"36", "12/07/2023 10:00"}, {"37", "13/07/2023 10:00"}, {"38", "13/07/2023 10:00"}, {"39", "13/07/2023 10:00"}, {"40", "14/07/2023 10:00"},
        };

        for (String[] fecha : fechas) {
            String id = fecha[0];
            String fechaNueva = fecha[1];

            Reclamo reclamo = ManejadorReclamo.getInstance().obtenerReclamo(Long.parseLong(id));
            reclamo.setCreacion(ServicioFechaHora.getInstance().stringToDate(fechaNueva));

            ManejadorBase.getInstance().modificarBean(reclamo);
        }
    }
}
