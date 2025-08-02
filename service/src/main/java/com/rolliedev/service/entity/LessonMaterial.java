package com.rolliedev.service.entity;

import com.rolliedev.service.entity.enums.LessonMaterialType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"lesson", "order"})
@Builder
@Entity
public class LessonMaterial implements Comparable<LessonMaterial> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contentUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LessonMaterialType type;

    @Column(name = "\"order\"", nullable = false)
    private Integer order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Override
    public int compareTo(LessonMaterial o) {
        return Integer.compare(order, o.order);
    }
}
