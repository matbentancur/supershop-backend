package grupo1.supershop.config.utils;

import com.aventstack.extentreports.Status;

/**
 * Enumerado utilizado para dar estados al reporte HTML
 */
public enum StatusTest {
    //region [Enumerados]
    INFO(Status.INFO),
    WARN(Status.WARNING),
    SKIP(Status.SKIP),
    FAIL(Status.FAIL),
    PASS(Status.PASS);
    //endregion

    //region [Variables]
    private final Status status;
    //endregion

    //region [Constructores]
    StatusTest(Status status) {
        this.status = status;
    }
    //endregion

    //region [Metodos de la clase]
    public Status getStatus() {
        return status;
    }
    //endregion
}
