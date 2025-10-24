package com.rolliedev.service.mapper;

import com.rolliedev.service.dto.LessonReadDto;
import com.rolliedev.service.entity.Lesson;

public class LessonReadMapper implements Mapper<Lesson, LessonReadDto> {

    @Override
    public LessonReadDto mapFrom(Lesson object) {
        return new LessonReadDto(
                object.getId(),
                object.getTitle(),
                object.getDescription(),
                object.getOrder(),
                object.getCourse().getId()
        );
    }
}
