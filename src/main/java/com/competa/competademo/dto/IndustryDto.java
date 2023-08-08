package com.competa.competademo.dto;

import com.competa.competademo.entity.Competa;
import com.competa.competademo.entity.Industry;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Optional;

import static com.competa.competademo.dto.UserDtoHelper.parseUserName;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndustryDto {
    private Long id;
    @NotEmpty
    private String name;

    public IndustryDto(Industry industry) {
        this.id = industry.getId();
        this.name = industry.getName();
    }

    public com.competa.competademo.entity.Industry toEntity() {
        return com.competa.competademo.entity.Industry.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }

}
