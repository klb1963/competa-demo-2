package com.competa.competademo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Competa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)

    private Long id; // идентификатор

    private String competaType; // тип = edCompeta, jobCompeta, hsCompeta, ssCompeta

    private String title;

    private String description;

    private boolean status;

    @ManyToOne
    private Industry industry; // индустрия

    private int views;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOut;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String timeOut;

    @ManyToOne
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Competa competa = (Competa) o;
        return Objects.equals(id, competa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Competa{");
        sb.append("id=").append(id);
        sb.append(", competaType='").append(competaType).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", industry='").append(industry).append('\'');
        sb.append(", status=").append(status);
        sb.append(", views=").append(views);
        sb.append(", dateOut=").append(dateOut);
        sb.append(", timeOut='").append(timeOut).append('\'');
        sb.append(", userId=").append(user.getId());
        sb.append('}');
        return sb.toString();
    }
}
