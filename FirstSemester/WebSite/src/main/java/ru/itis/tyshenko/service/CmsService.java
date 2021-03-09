package ru.itis.tyshenko.service;

import ru.itis.tyshenko.dto.CmsPageDto;
import ru.itis.tyshenko.form.CmsPageForm;
import java.util.List;
import java.util.Optional;

public interface CmsService {

    void save(CmsPageForm pageForm);

    List<CmsPageDto> getAll();

    Optional<CmsPageDto> getById(Long id);
}
