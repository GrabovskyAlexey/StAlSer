package ru.gb.stalser.api.dto.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Список активностей", name = "ActivityList")
public class ActivityListResponse {

    @Schema(description = "Активности")
    private List<ActivityDto> activities;
}
