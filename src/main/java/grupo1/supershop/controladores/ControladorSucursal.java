package grupo1.supershop.controladores;

import grupo1.supershop.beans.Archivo;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtSucursal;
import grupo1.supershop.interfaces.controladores.IControladorSucursal;
import grupo1.supershop.persistencia.manejadores.ManejadorArchivo;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioArchivo;
import grupo1.supershop.servicios.ServicioCloudinary;
import grupo1.supershop.servicios.ServicioDataType;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import grupo1.supershop.servicios.ServicioPermiso;
import grupo1.supershop.servicios.ServicioSesion;
import grupo1.supershop.validadores.ValidadorSucursal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.multipart.MultipartFile;

public class ControladorSucursal implements IControladorSucursal {

    @Override
    public DtSucursal obtenerSucursal(Long sucursalId) {
        Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
        if (sucursal == null) {
            return null;
        }
        return (DtSucursal) sucursal.getDataType();
    }

    @Override
    public DtSucursal obtenerSucursalNombre(String sucursalNombre, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal("nombre", sucursalNombre);
        if (sucursal == null) {
            return null;
        }
        return (DtSucursal) sucursal.getDataType();
    }

    @Override
    public List<DtSucursal> listarSucursales() {
        List<Sucursal> lista = ManejadorSucursal.getInstance().listarSucursales("borrado", false);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtSucursal> listarSucursalesBorradas(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        List<Sucursal> lista = ManejadorSucursal.getInstance().listarSucursales("borrado", true);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtActividad> listarActividadSucursal(Long sucursalId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        //OBTIENE SUCURSAL
        Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
        if (sucursal == null) {
            return null;
        }

        return ServicioDataType.getInstance().beanSetToDataTypeList(sucursal.getActividades());
    }

    @Override
    public DtMensajeSistema crearSucursal(DtSucursal dtSucursal, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE SUCURSAL
            Sucursal sucursal = new Sucursal(dtSucursal);

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorSucursal.getInstance().validarCrear(sucursal, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //CREA SUCURSAL
            ManejadorSucursal.getInstance().crearSucursal(sucursal);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarCreacion(sucursal, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Sucursal.create.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.create.error");
        }
    }

    @Override
    public DtMensajeSistema modificarSucursal(DtSucursal dtSucursal, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE SUCURSAL
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(dtSucursal.getId());
            if (sucursal == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.notexists.error");
            }

            sucursal.modificar(dtSucursal);

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorSucursal.getInstance().validarModificar(sucursal, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA SUCURSAL
            ManejadorSucursal.getInstance().modificarSucursal(sucursal);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(sucursal, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Sucursal.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.update.error");
        }
    }

    @Override
    public DtMensajeSistema borrarSucursal(Long sucursalId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE SUCURSAL
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
            if (sucursal == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorSucursal.getInstance().validarBorrar(sucursal, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BORRA SUCURSAL
            ManejadorSucursal.getInstance().borrarSucursal(sucursal);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarEliminacion(sucursal, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Sucursal.delete.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.delete.error");
        }
    }

    @Override
    public DtMensajeSistema restaurarSucursal(Long sucursalId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE SUCURSAL
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
            if (sucursal == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorSucursal.getInstance().validarRestaurar(sucursal, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //RESTAURA SUCURSAL
            ManejadorSucursal.getInstance().restaurarSucursal(sucursal);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarRestauracion(sucursal, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Sucursal.restore.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.restore.error");
        }
    }

    @Override
    public DtMensajeSistema agregarImagen(Long sucursalId, MultipartFile imagen, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE SUCURSAL
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
            if (sucursal == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorSucursal.getInstance().validarAgregarImagen(sucursal, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //GENERA NOMBRE CON EXTENSION
            String archivoNombre = sucursal.generarNombreImagen();
            archivoNombre = archivoNombre + ServicioArchivo.getInstance().agregarExtension(imagen.getContentType());

            //SUBE ARCHIVO A SISTEMA DE ARCHIVOS EN DIRECTORIO TEMPORAL
            dtMensajeSistema = ServicioArchivo.getInstance().subirArchivoTemporal(imagen, archivoNombre);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            Archivo archivo = new Archivo();
            archivo.setNombre(archivoNombre);

            //OBTIENE METADOTOS DEL ARCHIVO
            archivo = ServicioArchivo.getInstance().obtenerMetadatos(archivo);
            if (archivo == null) {
                ServicioArchivo.getInstance().borrarArchivoTemporal(archivoNombre);
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Archivo.create.error");
            }

            //MUEVE ARCHIVO EN EL SISTEMA DE ARCHIVOS A DIRECTORIO FINAL 
            dtMensajeSistema = ServicioArchivo.getInstance().moverArchivoTemporal(archivo.getNombre());
            if (!dtMensajeSistema.isExitoso()) {
                ServicioArchivo.getInstance().borrarArchivoTemporal(archivoNombre);
                return dtMensajeSistema;
            }

            //CREA ARCHIVO
            ManejadorArchivo.getInstance().crearArchivo(archivo);

            //MODIFICA SUCURSAL
            sucursal.setImagen(archivo);
            ManejadorSucursal.getInstance().modificarSucursal(sucursal);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(sucursal, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Sucursal.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.update.error");
        }

    }

    @Override
    public DtMensajeSistema quitarImagen(Long sucursalId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE SUCURSAL
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
            if (sucursal == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorSucursal.getInstance().validarQuitarImagen(sucursal, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BORRA EN SISTEMA DE ARCHIVOS
            dtMensajeSistema = ServicioArchivo.getInstance().borrarArchivo(sucursal.getImagen().getId());
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            Archivo archivo = ManejadorArchivo.getInstance().obtenerArchivo(sucursal.getImagen().getId());

            //MODIFICA SUCURSAL
            sucursal.setImagen(null);
            ManejadorSucursal.getInstance().modificarSucursal(sucursal);

            //BORRA ARCHIVO
            ManejadorArchivo.getInstance().borrarArchivo(archivo);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(sucursal, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Sucursal.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.update.error");
        }
    }

    @Override
    public DtMensajeSistema agregarImagenRemota(Long sucursalId, String url, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE SUCURSAL
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
            if (sucursal == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorSucursal.getInstance().validarAgregarImagenRemota(sucursal, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA SUCURSAL
            sucursal.setImagenRemota(url);
            ManejadorSucursal.getInstance().modificarSucursal(sucursal);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(sucursal, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Sucursal.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.update.error");
        }
    }

    @Override
    public DtMensajeSistema quitarImagenRemota(Long sucursalId, String url, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE SUCURSAL
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
            if (sucursal == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorSucursal.getInstance().validarQuitarImagenRemota(sucursal, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA SUCURSAL
            sucursal.setImagenRemota(null);
            ManejadorSucursal.getInstance().modificarSucursal(sucursal);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(sucursal, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Sucursal.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.update.error");
        }
    }

    @Override
    public DtMensajeSistema agregarImagenCloudinary(Long sucursalId, MultipartFile imagen, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE SUCURSAL
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
            if (sucursal == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorSucursal.getInstance().validarAgregarImagen(sucursal, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //GENERA NOMBRE CON EXTENSION
            String archivoNombre = sucursal.generarNombreImagen();
            archivoNombre = archivoNombre + ServicioArchivo.getInstance().agregarExtension(imagen.getContentType());

            //SUBE ARCHIVO A CLOUDINARY
            Archivo archivo = ServicioCloudinary.getInstance().subirArchivo(imagen, archivoNombre);
            if (archivo == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.update.error");
            }

            //CREA ARCHIVO
            ManejadorArchivo.getInstance().crearArchivo(archivo);

            //MODIFICA SUCURSAL
            sucursal.setImagen(archivo);
            ManejadorSucursal.getInstance().modificarSucursal(sucursal);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(sucursal, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Sucursal.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.update.error");
        }
    }

    @Override
    public DtMensajeSistema quitarImagenCloudinary(Long sucursalId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE SUCURSAL
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
            if (sucursal == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorSucursal.getInstance().validarQuitarImagen(sucursal, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BORRAR ARCHIVO EN CLOUDINARY
            Archivo archivo = ManejadorArchivo.getInstance().obtenerArchivo(sucursal.getImagen().getId());
            if (!ServicioCloudinary.getInstance().eliminarArchivo(archivo)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.update.error");
            }

            //BORRA EN SISTEMA DE ARCHIVOS
            dtMensajeSistema = ServicioArchivo.getInstance().borrarArchivo(sucursal.getImagen().getId());
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA SUCURSAL
            sucursal.setImagen(null);
            ManejadorSucursal.getInstance().modificarSucursal(sucursal);

            //BORRA ARCHIVO
            ManejadorArchivo.getInstance().borrarArchivo(archivo);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(sucursal, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Sucursal.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorSucursal.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.update.error");
        }
    }

}
