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
    private Long selectedIndustryId; // добавил индустрию
    private boolean status;
    private int views;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOut;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String timeOut;

    public CompetaDto(Competa competa) {
        this.id = competa.getId();
        this.competaType = competa.getCompetaType();
        this.title = competa.getTitle();
        this.description = competa.getDescription();
        this.selectedIndustryId = competa.getIndustry().getId(); // добавил индустрию
        this.status = competa.isStatus();
        this.views = competa.getViews();
        this.dateOut = competa.getDateOut();
        this.timeOut = competa.getTimeOut();
    }

    public com.competa.competademo.entity.Competa toEntity(Industry industry) {
        return com.competa.competademo.entity.Competa.builder()
                .id(this.id)
                .competaType(this.competaType)
                .title(this.title)
                .description(this.description)
                .industry(industry) // добавил индустрию
                .status(this.status)
                .views(this.views)
                .dateOut(this.dateOut)
                .timeOut(this.timeOut)
                .build();
    }
}
