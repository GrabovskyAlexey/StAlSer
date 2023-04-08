package ru.gb.stalser.api.dto.invite;

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
@Schema(description = "Список приглашений", name = "InviteList")
public class InviteListResponse {
    @Schema(description = "Список приглашения")
    private List<InviteDto> invites;
}
