package ru.itis.Tyshenko.repository.posts;

import ru.itis.Tyshenko.entity.Ad;
import ru.itis.Tyshenko.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AdRepository extends CrudRepository<Ad> {

    List<Ad> getAllByUserID(Long userID);
    Optional<Ad> getById(Long id);

    Optional<List<Ad>> getByResumeID(Long resumeID);
}
