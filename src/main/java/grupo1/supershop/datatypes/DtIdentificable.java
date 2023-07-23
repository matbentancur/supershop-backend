package grupo1.supershop.datatypes;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public abstract class DtIdentificable extends DtBase {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Date creacion;

}
