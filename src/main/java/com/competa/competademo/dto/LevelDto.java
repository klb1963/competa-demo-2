package com.competa.competademo.dto;

import com.competa.competademo.entity.Ctype;
import com.competa.competademo.entity.Level;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LevelDto {
    private Long id;
    @NotEmpty
    private String name;

    public LevelDto(Level level) {
        this.id = level.getId();
        this.name = level.getName();
    }

    public com.competa.competademo.entity.Level toEntity() {
        return com.competa.competademo.entity.Level.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }

}
