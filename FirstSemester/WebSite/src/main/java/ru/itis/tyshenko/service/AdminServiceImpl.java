package ru.itis.tyshenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.tyshenko.dto.AdminDto;
import ru.itis.tyshenko.entity.Admin;
import ru.itis.tyshenko.form.AdminForm;
import ru.itis.tyshenko.repository.AdminRepository;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Optional<AdminDto> authenticate(AdminForm form) {
        Optional<Admin> admin = adminRepository.getAdminByLoginAndPassword(form.getLogin(), form.getPassword());
        return admin.map(value -> AdminDto.builder().login(value.getLogin()).build());
    }
}
