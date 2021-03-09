package ru.itis.tyshenko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.tyshenko.entity.CmsPage;

public interface CmsRepository extends JpaRepository<CmsPage, Long> {
}
