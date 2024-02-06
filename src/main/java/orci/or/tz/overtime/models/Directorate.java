package orci.or.tz.overtime.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import orci.or.tz.overtime.utilities.Auditable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Table(name = "directorates")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Directorate extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "directorate_id_seq")
    @SequenceGenerator(name = "directorate_id_seq", sequenceName = "DIRECTORATE_ID_SEQ", initialValue = 1, allocationSize = 1)
    @Column(name = "directorate_id")
    private Long id;

    @Column(name = "directorate_name", nullable = false)
    private String directorateName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "directorate")
    @JsonManagedReference(value = "directorate-departments")
    @EqualsAndHashCode.Exclude
    @lombok.ToString.Exclude
    @JsonIgnore
    private List<Department> departments;
}
