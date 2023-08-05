package com.competa.competademo.dto;

import com.competa.competademo.entity.Industry;
import com.competa.competademo.entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import static com.competa.competademo.dto.UserDtoHelper.parseUserName;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndustryDto {
    protected Long id;
    @NotEmpty
    protected String name;

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
