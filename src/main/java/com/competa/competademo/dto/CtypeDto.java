package com.competa.competademo.dto;

import com.competa.competademo.entity.Ctype;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CtypeDto {
    private Long id;
    @NotEmpty
    private String name;

    public CtypeDto(Ctype ctype) {
        this.id = ctype.getId();
        this.name = ctype.getName();
    }

    public com.competa.competademo.entity.Ctype toEntity() {
        return com.competa.competademo.entity.Ctype.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
