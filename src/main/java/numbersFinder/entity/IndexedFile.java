package numbersFinder.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table
@Data
public class IndexedFile {
    @Id
    public String filename;

    @NotNull
    public LocalDateTime lastUpdate;
}
