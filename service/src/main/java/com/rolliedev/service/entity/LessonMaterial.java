package com.rolliedev.service.entity;

import com.rolliedev.service.entity.enums.LessonMaterialType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class LessonMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contentUrl;
    private LessonMaterialType type;

    @Column(name = "\"order\"", nullable = false)
    private Integer order;
    private Long lessonId;
}
