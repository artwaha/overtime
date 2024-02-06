package orci.or.tz.overtime.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Data
@Table(name="departments")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Department extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dempartment_id_seq")
    @SequenceGenerator(name = "department_id_seq", sequenceName = "DEPARTMENT_ID_SEQ", initialValue = 1, allocationSize = 1)
    @Column(name = "department_id")
    private Long id;

    @Column(name = "department_name", nullable = false)
    private String departmentName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "directorate_id", referencedColumnName = "directorate_id")
    @JsonBackReference(value = "directorate-departments")
    @EqualsAndHashCode.Exclude
    @lombok.ToString.Exclude
    private Directorate directorate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "department")
    @JsonManagedReference(value = "department-sections")
    @EqualsAndHashCode.Exclude
    @lombok.ToString.Exclude
    @JsonIgnore
    private List<Section> sections;
}
