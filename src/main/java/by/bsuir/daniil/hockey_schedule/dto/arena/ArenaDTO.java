package by.bsuir.daniil.hockey_schedule.dto.arena;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Сущность арены")
public class ArenaDTO {
    private Integer id;
    private String city;
    private Integer capacity;
}
