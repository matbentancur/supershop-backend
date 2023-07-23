package grupo1.supershop.servicios;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import grupo1.supershop.beans.Archivo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.multipart.MultipartFile;

public class ServicioCloudinary {

    private static ServicioCloudinary instance = null;
    private static String tempFileSystemDirectory = "/tmp/tmp";

    private ServicioCloudinary() {
        cloudName = ServicioProperties.getInstance().getAppProperties("cloudinary.cloud_name");
        apiKey = ServicioProperties.getInstance().getAppProperties("cloudinary.api_key");
        apiSecret = ServicioProperties.getInstance().getAppProperties("cloudinary.api_secret");
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }

    public static ServicioCloudinary getInstance() {
        if (instance == null) {
            instance = new ServicioCloudinary();
        }
        return instance;
    }

    private Cloudinary cloudinary;
    private String cloudName;
    private String apiKey;
    private String apiSecret;

    private Archivo uploadFile(File file, Archivo archivo) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            archivo.setUrl(uploadResult.get("url").toString());
            archivo.setPublicId(uploadResult.get("public_id").toString());
            return archivo;
        } catch (IOException ex) {
            Logger.getLogger(ServicioCloudinary.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Archivo subirArchivo(MultipartFile archivoSubir, String archivoSubirNombre) {
        try {
            //CREA EL DIRECTORIO TEMPORAL
            this.crearDirectorio();

            //SUBE EL ARCHIVO AL SISTEMA DE ARCHIVOS TEMPORAL
            Path path = Paths.get(tempFileSystemDirectory + archivoSubirNombre);

            InputStream input = archivoSubir.getInputStream();
            File nuevoArchivo = new File(path.toString());

            OutputStream outputStream = new FileOutputStream(nuevoArchivo);

            byte[] b = new byte[100000];
            int bytesRead = 0;
            while ((bytesRead = input.read(b)) != -1) {
                outputStream.write(b, 0, bytesRead);
            }
            outputStream.close();

            //SUBE EL ARCHIVO A CLOUDINARY
            Archivo archivo = new Archivo();
            archivo.setNombre(archivoSubirNombre);
            archivo = this.uploadFile(nuevoArchivo, archivo);
            archivo = this.obtenerMetadatos(archivo);

            return archivo;

        } catch (IOException ex) {
            Logger.getLogger(ServicioArchivo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean eliminarArchivo(Archivo archivo) {
        try {
            Map destroyResult = cloudinary.uploader().destroy(archivo.getPublicId(), ObjectUtils.emptyMap());
            if (destroyResult.get("result").toString().equals("ok")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServicioCloudinary.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private Archivo obtenerMetadatos(Archivo archivo) {
        try {
            Path path = Paths.get(tempFileSystemDirectory + archivo.getNombre());

            File archivoSubido = new File(path.toString());

            String mimeType = Files.probeContentType(path);
            if (mimeType == null) {
                return null;
            }
            archivo.setMime(mimeType);

            String extension = ServicioArchivo.getInstance().obtenerExtension(archivo.getMime());
            if (extension == null) {
                return null;
            }
            archivo.setExtension(extension);

            archivo.setPeso(archivoSubido.length());
            archivo.setUbicacion(tempFileSystemDirectory + archivo.getNombre());

            return archivo;

        } catch (Exception ex) {
            Logger.getLogger(ServicioArchivo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private void crearDirectorio() {
        try {
            File directorioTemporal = new File(tempFileSystemDirectory);
            if (!directorioTemporal.exists()) {
                directorioTemporal.mkdirs();
            }
        } catch (Exception ex) {
            Logger.getLogger(ServicioArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
