package com.rolliedev.service.service;

import com.rolliedev.service.repository.CourseRepository;
import com.rolliedev.service.dto.CourseCreateDto;
import com.rolliedev.service.dto.CourseFilter;
import com.rolliedev.service.dto.CourseReadDto;
import com.rolliedev.service.dto.CourseUpdateDto;
import com.rolliedev.service.mapper.CourseCreateMapper;
import com.rolliedev.service.mapper.CourseReadMapper;
import com.rolliedev.service.mapper.CourseUpdateMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseReadMapper courseReadMapper;
    private final CourseCreateMapper courseCreateMapper;
    private final CourseUpdateMapper courseUpdateMapper;
    private final Validator validator;

    @Transactional
    public List<CourseReadDto> findAllByInstructor(CourseFilter filter) {
        doValidation(filter);
        return courseRepository.findAllByInstructor(filter).stream()
                .map(courseReadMapper::mapFrom)
                .toList();
    }

    @Transactional
    public List<CourseReadDto> findAllByTitle(String title) {
        return courseRepository.findAllByTitle(title).stream()
                .map(courseReadMapper::mapFrom)
                .toList();
    }

    @Transactional
    public List<CourseReadDto> findAllByStudentEmail(String studentEmail) {
        return courseRepository.findAllByStudentEmail(studentEmail).stream()
                .map(courseReadMapper::mapFrom)
                .toList();
    }

    @Transactional
    public List<CourseReadDto> findAll() {
        return courseRepository.findAll().stream()
                .map(courseReadMapper::mapFrom)
                .toList();
    }

    @Transactional
    public void update(CourseUpdateDto courseDto) {
        doValidation(courseDto);
        var courseEntity = courseUpdateMapper.mapFrom(courseDto);
        courseRepository.update(courseEntity);
    }

    @Transactional
    public Long create(CourseCreateDto courseDto) {
        doValidation(courseDto);
        var courseEntity = courseCreateMapper.mapFrom(courseDto);
        return courseRepository.save(courseEntity).getId();
    }

    @Transactional
    public Optional<CourseReadDto> findById(Long id) {
        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJakartaHintName(), courseRepository.getEntityManager().getEntityGraph("WithInstructor")
        );
        return courseRepository.findById(id, properties)
                .map(courseReadMapper::mapFrom);
    }

    @Transactional
    public boolean delete(Long id) {
        try {
            courseRepository.delete(id);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private <T> void doValidation(T dto) {
        var validationResult = validator.validate(dto);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }
    }
}
