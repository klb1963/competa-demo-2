package com.competa.competademo.dto;

import com.competa.competademo.entity.*;
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
    private Long ctypeId;
    private String ctypeName;
    private String title;
    private Long selectedIndustryId; // добавил индустрию

    @Getter
    private String description;

    private boolean status; // публичная или приватная

    // TODO - добавить статус подтверждения и индекс доверия

    private int views;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOut;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String timeOut;

    private ImageInfo competaImage; // ссылка на изображение

    // конвертация из Dto в Entity
    // конструктор
    public CompetaDto(Competa competa) {
        this.id = competa.getId();
        this.ctypeId = competa.getCtype().getId(); // добавил ID типа компеты
        this.ctypeName = competa.getCtype().getName(); // добавил Имя типа компеты
        this.title = competa.getTitle();
        this.description = competa.getDescription();
        this.selectedIndustryId = competa.getIndustry().getId(); // добавил индустрию
        this.status = competa.isStatus();
        this.views = competa.getViews();
        this.dateOut = competa.getDateOut();
        this.timeOut = competa.getTimeOut();
        this.competaImage = competa.getCompetaImage(); // добавил competaImage
    }

    // конструктор для competaImage
    public CompetaDto(Competa competa, ImageInfo competaImage) {
        this(competa);
        this.competaImage = competaImage;
    }


    public com.competa.competademo.entity.Competa toEntity(Industry industry, Ctype ctype) {
        return com.competa.competademo.entity.Competa.builder()
                .id(this.id)
                .ctype(ctype) // добавил тип компеты
                .title(this.title)
                .description(this.description)
                .industry(industry) // добавил индустрию
                .status(this.status)
                .views(this.views)
                .dateOut(this.dateOut)
                .timeOut(this.timeOut)
                .competaImage(competaImage) // добавил competaImage
                .build();
    }
}
