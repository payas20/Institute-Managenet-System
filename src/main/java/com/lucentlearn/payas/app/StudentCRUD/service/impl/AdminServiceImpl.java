package com.lucentlearn.payas.app.StudentCRUD.service.impl;

import com.lucentlearn.payas.app.StudentCRUD.exceptions.AdminServiceException;
import com.lucentlearn.payas.app.StudentCRUD.io.entity.AdminEntity;
import com.lucentlearn.payas.app.StudentCRUD.io.entity.Role;
import com.lucentlearn.payas.app.StudentCRUD.io.entity.UserEntity;
import com.lucentlearn.payas.app.StudentCRUD.model.response.ErrorMessages;
import com.lucentlearn.payas.app.StudentCRUD.repository.AdminRepository;
import com.lucentlearn.payas.app.StudentCRUD.repository.UserRepository;
import com.lucentlearn.payas.app.StudentCRUD.service.AdminService;
import com.lucentlearn.payas.app.StudentCRUD.shared.dto.AdminDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public AdminDto createAdmin(AdminDto adminDto) {

        AdminEntity storedAdmin = adminRepository.findByEmail(adminDto.getEmail()) ;

        if(storedAdmin !=  null ) throw new AdminServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());


        adminDto.setEncryptedPassword(passwordEncoder.encode(adminDto.getPassword()));

        AdminEntity admin = new AdminEntity();
        BeanUtils.copyProperties(adminDto, admin);


        AdminEntity savedAdmin = adminRepository.save(admin);

        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(adminDto, user);

        userRepository.save(user);

        AdminDto returnValue = new AdminDto();
        BeanUtils.copyProperties(savedAdmin, returnValue);

        return returnValue;
    }

    @Override
    public AdminDto getAdmin(String username) {

        AdminEntity admin = adminRepository.findByEmail(username);

        if(admin==null) throw new AdminServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        AdminDto returnValue = new AdminDto();
        BeanUtils.copyProperties(admin,returnValue);

        return returnValue;
    }

    @Override
    public AdminDto updateUser(String username, AdminDto adminDto) {

        AdminEntity admin = adminRepository.findByEmail(username);

        if(admin==null) throw new AdminServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());

        adminRepository.save(admin);

        AdminDto updatedUser = new AdminDto();
        BeanUtils.copyProperties(admin, updatedUser);

        return updatedUser;
    }

    @Override
    public void deleteAdmin(String username) {
        AdminEntity admin = adminRepository.findByEmail(username);

        if(admin==null) throw new AdminServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        adminRepository.delete(admin);
    }
}
