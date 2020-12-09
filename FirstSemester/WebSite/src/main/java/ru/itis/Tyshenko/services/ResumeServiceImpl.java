package ru.itis.Tyshenko.services;

import ru.itis.Tyshenko.dto.ResumeDTO;
import ru.itis.Tyshenko.entity.Resume;
import ru.itis.Tyshenko.repositories.posts.ResumeRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ResumeServiceImpl implements ResumeService {

    ResumeRepository repository;

    public ResumeServiceImpl(ResumeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ResumeDTO> getAll() {
        return convertToResumeDTO(repository.findAll());
    }

    @Override
    public void add(ResumeDTO resumeDTO, Long useId) {
        Resume resume = Resume.builder().id(null).header(resumeDTO.getHeader()).
                description(resumeDTO.description).contact(resumeDTO.contact).
                user_id(useId).build();
        repository.save(resume);
        resumeDTO.id = resume.getId();
    }

    @Override
    public Optional<ResumeDTO> getByUserId(Long userId) {
        Optional<Resume> o_resume = repository.findByUserId(userId);
        ResumeDTO resumeDTO = null;
        if (o_resume.isPresent()) {
            Resume resume = o_resume.get();
            resumeDTO = ResumeDTO.builder().header(resume.getHeader()).
                    description(resume.getDescription()).
                    contact(resume.getContact()).id(resume.getId()).build();
        }
        return Optional.ofNullable(resumeDTO);
    }

    private List<ResumeDTO> convertToResumeDTO(List<Resume> resumes) {
        List<ResumeDTO> resumeDTOS = new LinkedList<>();
        for (Resume resume: resumes) {
            ResumeDTO resumeDTO = ResumeDTO.builder().id(resume.getId()).description(resume.getDescription()).
                    contact(resume.getContact()).header(resume.getHeader()).build();
            resumeDTOS.add(resumeDTO);
        }
        return resumeDTOS;
    }
}
