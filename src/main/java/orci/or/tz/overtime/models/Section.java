package orci.or.tz.overtime.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import orci.or.tz.overtime.utilities.Auditable;


import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name="sections")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Section extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "section_id_seq")
    @SequenceGenerator(name = "section_id_seq", sequenceName = "SECTIONS_ID_SEQ", initialValue = 1, allocationSize = 1)
    @Column(name = "section_id")
    private Long id;


    @Column(name = "section_name", nullable = false)
    private String sectionName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    @JsonBackReference(value = "department-sections")
    @EqualsAndHashCode.Exclude
    @lombok.ToString.Exclude
    private Department department;
}
