package com.competa.competademo.dto;

import com.competa.competademo.entity.Competa;
import com.competa.competademo.entity.Industry;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Andrej Reutow
 * created on 03.08.2023
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompetaDto {

    private Long id;
    private String competaType;
    private String title;
    @Getter
    private String description;
    private Industry industry; // добавил индустрию
    private boolean status;
    private int views;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOut;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String timeOut;

    public CompetaDto(Competa competa) {
        this.id = id;
        this.competaType = competaType;
        this.title = title;
        this.description = description;
        this.industry = industry; // добавил индустрию
        this.status = status;
        this.views = views;
        this.dateOut = dateOut;
        this.timeOut = timeOut;
    }

    public com.competa.competademo.entity.Competa toEntity() {
        return com.competa.competademo.entity.Competa.builder()
                .id(this.id)
                .competaType(this.competaType)
                .title(this.title)
                .description(this.description)
                .industry(this.industry) // добавил индустрию
                .status(this.status)
                .views(this.views)
                .dateOut(this.dateOut)
                .timeOut(this.timeOut)
                .build();
    }
}
