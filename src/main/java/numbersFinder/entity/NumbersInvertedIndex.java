package numbersFinder.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(indexes = {
        @Index(name = "inverted_index_numbers", columnList = "number"),
        @Index(name = "inverted_index_filenames", columnList = "filename"),
})
@Data
public class NumbersInvertedIndex {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public Integer number;

    public String filename;


}
