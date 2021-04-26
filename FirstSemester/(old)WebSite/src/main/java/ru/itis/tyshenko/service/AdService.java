package ru.itis.tyshenko.service;

import ru.itis.tyshenko.dto.AdDto;

import java.util.List;
import java.util.Optional;

public interface AdService {

    void add(AdDto adDTO, Long useId);
    List<AdDto> getAll();
    Optional<AdDto> getById(Long id);
    List<AdDto> getAllByUserID(Long user_id);
    Optional<List<AdDto>> getAllByResumeId(Long resume_id);
}
