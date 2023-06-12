package com.lucentlearn.payas.app.StudentCRUD.service;

import com.lucentlearn.payas.app.StudentCRUD.shared.dto.AdminDto;

public interface AdminService {
    AdminDto createAdmin(AdminDto adminDto);

    AdminDto getAdmin(String username);

    AdminDto updateUser(String username, AdminDto adminDto);

    void deleteAdmin(String username);
}
