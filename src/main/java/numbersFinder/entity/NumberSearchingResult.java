package numbersFinder.entity;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;


@Entity
@Table
@Data
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class NumberSearchingResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public Integer number;
    public String code;

    @Type(type = "list-array")
    @Column(
            name = "filenames",
            columnDefinition = "text[]"
    )
    public List<String> filenames;
    public String error;
}
