package grupo1.supershop.config.properties;

import grupo1.supershop.config.servicios.ServicioVerificacion;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertyReader {

    //region [Variables]
    private static PropertyReader instance = null;
    private static final Properties properties = new Properties();
    private static FileReader fileReader;
    private static String path;
    private static String valueReturn;
    //endregion

    //region [Constructores]
    private PropertyReader() {
    }
    //endregion

    //region [Metodos publicos]
    public static PropertyReader getInstance() {
        if (instance == null) {
            instance = new PropertyReader();
        }
        return instance;
    }
    /**
     * Obtenemos valores de 'test_value.properties' dado una key.
     *
     * @param key Clave para obtener valor.
     * @return Valor dado una key.
     */
    public  String getTestValue(String key) {
        path = "src/test/resources/test_value.properties";
        try {
            returnProperty(key);
        } catch (IOException f) {
            f.printStackTrace();
        }
        return valueReturn;
    }

    /**
     * Obtenemos valores de 'environment.properties' dado una key.
     *
     * @param key Clave para obtener valor.
     * @return Valor dado una key.
     */
    public  String getEnvironment(String key) {
        path = "src/test/resources/environment.properties";
        try {
            returnProperty(key);
        } catch (IOException f) {
            f.printStackTrace();
        }
        return valueReturn;
    }

    public String getTestMessage(String key){
        path = "src/test/resources/test_message.properties";
        try {
            returnProperty(key);
        } catch (IOException f) {
            f.printStackTrace();
        }
        return valueReturn;
    }
    //endregion

    //region [Metodos privados]

    /**
     * Dado un path, carga el archivo .propertie para obtener su contenido.
     *
     * @param path Ruta del archivo .propertie.
     */
    private static void fileUpload(String path) throws IOException {
        File file = new File(path);
        fileReader = new FileReader(file, StandardCharsets.UTF_8);
    }

    /**
     * Retorna valor dado una clave.
     *
     * @param key Clave para obtener valor.
     * @throws IOException Error al leer archivo. Error con parametro. Parametro null.
     */
    private static void returnProperty(String key) throws IOException {
        fileUpload(path);
        properties.load(fileReader);
        valueReturn = properties.getProperty(key);
    }
    //endregion
}
