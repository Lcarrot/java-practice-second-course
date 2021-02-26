package ru.itis.Tyshenko.service;

import ru.itis.Tyshenko.dto.ResumeDTO;

import java.util.List;
import java.util.Optional;

public interface ResumeService {

    List<ResumeDTO> getAll();
    void add(ResumeDTO resumeDTO, Long useId);
    Optional<ResumeDTO> getByUserId(Long UserId);
}
