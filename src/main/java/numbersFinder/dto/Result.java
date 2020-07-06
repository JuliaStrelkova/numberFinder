package numbersFinder.dto;

import lombok.Data;

import java.util.List;

@Data
public class Result {
    public String code;
    public List<String> fileNames;
    public String error;
}
