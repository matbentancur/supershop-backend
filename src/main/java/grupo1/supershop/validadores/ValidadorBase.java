package grupo1.supershop.validadores;

import grupo1.supershop.beans.Base;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ValidadorBase {

    private static ValidadorBase instance = null;

    private ValidadorBase() {
    }

    public static ValidadorBase getInstance() {
        if (instance == null) {
            instance = new ValidadorBase();
        }
        return instance;
    }

    public List<String> validarBean(Base base) {
        List<String> listaErrores = new ArrayList<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Base>> violations = validator.validate(base);

        for (ConstraintViolation<Base> violation : violations) {
            listaErrores.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
        }
        return listaErrores;
    }

}
