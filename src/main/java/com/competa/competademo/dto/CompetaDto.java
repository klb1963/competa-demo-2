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
    private String ctype;
    private String title;
    private Long selectedIndustryId; // добавил индустрию

    @Getter
    private String description;

    private boolean status;
    private int views;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOut;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String timeOut;

    public CompetaDto(Competa competa) {
        this.id = competa.getId();
        this.ctype = competa.getCtype().getName();
        this.title = competa.getTitle();
        this.description = competa.getDescription();
        this.selectedIndustryId = competa.getIndustry().getId(); // добавил индустрию
        this.status = competa.isStatus();
        this.views = competa.getViews();
        this.dateOut = competa.getDateOut();
        this.timeOut = competa.getTimeOut();
    }

    public com.competa.competademo.entity.Competa toEntity() {
        return com.competa.competademo.entity.Competa.builder()
                .id(this.id)
                // TODO - как добавить тип компеты для сохранения в БД
                // .ctype(this.ctype)
                .title(this.title)
                .description(this.description)
                // TODO - как добавить индустрию для сохранения в БД
                //.industry(this.selectedIndustryId) // добавил индустрию
                .status(this.status)
                .views(this.views)
                .dateOut(this.dateOut)
                .timeOut(this.timeOut)
                .build();
    }
}
