package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtBase;
import java.io.Serializable;
import jakarta.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
public abstract class Base implements Serializable{
   
    public abstract DtBase getDataType();
    
    public abstract DtBase getSimpleDataType();

}
