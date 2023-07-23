package grupo1.supershop.servicios;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServicioFechaHora {

    private static ServicioFechaHora instance = null;

    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static DateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private ServicioFechaHora() {
    }

    public static ServicioFechaHora getInstance() {
        if (instance == null) {
            instance = new ServicioFechaHora();
        }
        return instance;
    }

    public Date stringToDate(String stringDate) {
        try {
            return dateFormat.parse(stringDate);
        } catch (Exception exception) {
            return null;
        }
    }

    public Date stringToDateTime(String stringDateTime) {
        try {
            return dateTimeFormat.parse(stringDateTime);
        } catch (Exception exception) {
            return null;
        }
    }

    public String dateToString(Date date) {
        try {
            return dateFormat.format(date);
        } catch (Exception exception) {
            return null;
        }
    }

    public String dateTimeToString(Date dateTime) {
        try {
            return dateTimeFormat.format(dateTime);
        } catch (Exception exception) {
            return null;
        }
    }
    
    public String getStringDate(){
        Date date = new Date();
        return this.dateToString(date);
    }
    
    public String getStringDateTime(){
        Date date = new Date();
        return this.dateTimeToString(date);
    }

}
