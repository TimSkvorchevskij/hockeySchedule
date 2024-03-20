package by.bsuir.daniil.hockey_schedule.dto.team;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Сущность матча")
public class TeamDTO {
    private Integer id;
    private String teamName;
}
