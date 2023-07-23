package grupo1.supershop.servicios;

import grupo1.supershop.beans.Actividad;
import grupo1.supershop.beans.AdministradorSistema;
import grupo1.supershop.beans.AdministradorSucursal;
import grupo1.supershop.beans.Archivo;
import grupo1.supershop.beans.AtencionCliente;
import grupo1.supershop.beans.Base;
import grupo1.supershop.beans.Borrable;
import grupo1.supershop.beans.Carrito;
import grupo1.supershop.beans.CarritoProducto;
import grupo1.supershop.beans.Categoria;
import grupo1.supershop.beans.CompraDomicilio;
import grupo1.supershop.beans.CompraSucursal;
import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Conversacion;
import grupo1.supershop.beans.Domicilio;
import grupo1.supershop.beans.Mensaje;
import grupo1.supershop.beans.MetodoEnvio;
import grupo1.supershop.beans.Producto;
import grupo1.supershop.beans.Promocion;
import grupo1.supershop.beans.Reclamo;
import grupo1.supershop.beans.Stock;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Vale;
import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtAdministradorSistema;
import grupo1.supershop.datatypes.DtAdministradorSucursal;
import grupo1.supershop.datatypes.DtArchivo;
import grupo1.supershop.datatypes.DtAtencionCliente;
import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtCarrito;
import grupo1.supershop.datatypes.DtCarritoProducto;
import grupo1.supershop.datatypes.DtCategoria;
import grupo1.supershop.datatypes.DtCompraDomicilio;
import grupo1.supershop.datatypes.DtCompraSucursal;
import grupo1.supershop.datatypes.DtComprador;
import grupo1.supershop.datatypes.DtConversacion;
import grupo1.supershop.datatypes.DtDomicilio;
import grupo1.supershop.datatypes.DtMensaje;
import grupo1.supershop.datatypes.DtMetodoEnvio;
import grupo1.supershop.datatypes.DtProducto;
import grupo1.supershop.datatypes.DtPromocion;
import grupo1.supershop.datatypes.DtReclamo;
import grupo1.supershop.datatypes.DtStock;
import grupo1.supershop.datatypes.DtSucursal;
import grupo1.supershop.datatypes.DtVale;

public class ServicioBeans {

    private static ServicioBeans instance = null;

    private ServicioBeans() {
    }

    public static ServicioBeans getInstance() {
        if (instance == null) {
            instance = new ServicioBeans();
        }
        return instance;
    }

    public boolean estaBorrado(Borrable bean) {
        return !bean.isBorrado();
    }

    public Base parseDtBase(DtBase dtBase) {
        if (dtBase instanceof DtActividad) {
            return this.getNewBean((DtActividad) dtBase);
        } else if (dtBase instanceof DtAdministradorSistema) {
            return this.getNewBean((DtAdministradorSistema) dtBase);
        } else if (dtBase instanceof DtAdministradorSucursal) {
            return this.getNewBean((DtAdministradorSucursal) dtBase);
        } else if (dtBase instanceof DtArchivo) {
            return this.getNewBean((DtArchivo) dtBase);
        } else if (dtBase instanceof DtAtencionCliente) {
            return this.getNewBean((DtAtencionCliente) dtBase);
        } else if (dtBase instanceof DtCarrito) {
            return this.getNewBean((DtCarrito) dtBase);
        } else if (dtBase instanceof DtCarritoProducto) {
            return this.getNewBean((DtCarritoProducto) dtBase);
        } else if (dtBase instanceof DtCategoria) {
            return this.getNewBean((DtCategoria) dtBase);
        } else if (dtBase instanceof DtCompraDomicilio) {
            return this.getNewBean((DtCompraDomicilio) dtBase);
        } else if (dtBase instanceof DtCompraSucursal) {
            return this.getNewBean((DtCompraSucursal) dtBase);
        } else if (dtBase instanceof DtComprador) {
            return this.getNewBean((DtComprador) dtBase);
        } else if (dtBase instanceof DtConversacion) {
            return this.getNewBean((DtConversacion) dtBase);
        } else if (dtBase instanceof DtDomicilio) {
            return this.getNewBean((DtDomicilio) dtBase);
        } else if (dtBase instanceof DtMensaje) {
            return this.getNewBean((DtMensaje) dtBase);
        } else if (dtBase instanceof DtMetodoEnvio) {
            return this.getNewBean((DtMetodoEnvio) dtBase);
        } else if (dtBase instanceof DtProducto) {
            return this.getNewBean((DtProducto) dtBase);
        } else if (dtBase instanceof DtPromocion) {
            return this.getNewBean((DtPromocion) dtBase);
        } else if (dtBase instanceof DtReclamo) {
            return this.getNewBean((DtReclamo) dtBase);
        } else if (dtBase instanceof DtStock) {
            return this.getNewBean((DtStock) dtBase);
        } else if (dtBase instanceof DtSucursal) {
            return this.getNewBean((DtSucursal) dtBase);
        } else if (dtBase instanceof DtVale) {
            return this.getNewBean((DtVale) dtBase);
        }
        return null;
    }

    public Actividad getNewBean(DtActividad dtActividad) {
        return new Actividad();
    }

    public AdministradorSistema getNewBean(DtAdministradorSistema dtAdministradorSistema) {
        return new AdministradorSistema();
    }

    public AdministradorSucursal getNewBean(DtAdministradorSucursal dtAdministradorSucursal) {
        return new AdministradorSucursal();
    }

    public Archivo getNewBean(DtArchivo dtArchivo) {
        return new Archivo();
    }

    public AtencionCliente getNewBean(DtAtencionCliente dtAtencionCliente) {
        return new AtencionCliente();
    }

    public Carrito getNewBean(DtCarrito dtCarrito) {
        return new Carrito();
    }

    public CarritoProducto getNewBean(DtCarritoProducto dtCarritoProducto) {
        return new CarritoProducto();
    }

    public Categoria getNewBean(DtCategoria dtCategoria) {
        return new Categoria();
    }

    public CompraDomicilio getNewBean(DtCompraDomicilio dtCompraDomicilio) {
        return new CompraDomicilio();
    }

    public CompraSucursal getNewBean(DtCompraSucursal dtCompraSucursal) {
        return new CompraSucursal();
    }

    public Comprador getNewBean(DtComprador dtComprador) {
        return new Comprador();
    }

    public Conversacion getNewBean(DtConversacion dtConversacion) {
        return new Conversacion();
    }

    public Domicilio getNewBean(DtDomicilio dtDomicilio) {
        return new Domicilio();
    }

    public Mensaje getNewBean(DtMensaje dtMensaje) {
        return new Mensaje();
    }

    public MetodoEnvio getNewBean(DtMetodoEnvio dtMetodoEnvio) {
        return new MetodoEnvio();
    }

    public Producto getNewBean(DtProducto dtProducto) {
        return new Producto();
    }

    public Promocion getNewBean(DtPromocion dtPromocion) {
        return new Promocion();
    }

    public Reclamo getNewBean(DtReclamo dtReclamo) {
        return new Reclamo();
    }

    public Stock getNewBean(DtStock dtStock) {
        return new Stock();
    }

    public Sucursal getNewBean(DtSucursal dtSucursal) {
        return new Sucursal();
    }

    public Vale getNewBean(DtVale dtVale) {
        return new Vale();
    }

}
