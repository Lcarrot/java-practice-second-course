package ru.itis.Tyshenko.services;

import ru.itis.Tyshenko.dto.AdDTO;

import java.util.List;
import java.util.Optional;

public interface AdService {

    void add(AdDTO adDTO, Long useId);
    List<AdDTO> getAll();
    Optional<AdDTO> getById(Long id);
    List<AdDTO> getAllByUserID(Long user_id);
    Optional<List<AdDTO>> getAllByResumeId(Long resume_id);
}
