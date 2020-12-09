package ru.itis.Tyshenko.repositories.posts;

import ru.itis.Tyshenko.entity.Resume;
import ru.itis.Tyshenko.repositories.CrudRepository;

import java.util.Optional;

public interface ResumeRepository extends CrudRepository<Resume> {

    Optional<Resume> findByUserId(Long userId);
}
