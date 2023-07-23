package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtModificable;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@MappedSuperclass
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class Modificable extends Identificable {

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @Getter
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Actividad> actividades = new HashSet<Actividad>();

    public boolean agregarActividad(Actividad actividad) {
        return actividades.add(actividad);
    }

    public abstract void modificar(DtModificable dtModificable);

}
