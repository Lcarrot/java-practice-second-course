package ru.itis.tyshenko.service;

import ru.itis.tyshenko.dto.ResumeDto;

import java.util.List;
import java.util.Optional;

public interface ResumeService {

    List<ResumeDto> getAll();
    void add(ResumeDto resumeDTO, Long useId);
    Optional<ResumeDto> getByUserId(Long UserId);
}
