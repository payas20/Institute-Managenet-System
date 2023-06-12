package com.lucentlearn.payas.app.StudentCRUD.controller;

import com.lucentlearn.payas.app.StudentCRUD.exceptions.AdminServiceException;
import com.lucentlearn.payas.app.StudentCRUD.exceptions.UserServiceException;
import com.lucentlearn.payas.app.StudentCRUD.io.entity.AdminEntity;
import com.lucentlearn.payas.app.StudentCRUD.io.entity.UserEntity;
import com.lucentlearn.payas.app.StudentCRUD.model.request.AdminDetailsRequestModel;
import com.lucentlearn.payas.app.StudentCRUD.model.request.UserLoginRequest;
import com.lucentlearn.payas.app.StudentCRUD.model.response.AdminRest;
import com.lucentlearn.payas.app.StudentCRUD.model.response.ErrorMessages;
import com.lucentlearn.payas.app.StudentCRUD.repository.UserRepository;
import com.lucentlearn.payas.app.StudentCRUD.service.AdminService;
import com.lucentlearn.payas.app.StudentCRUD.service.impl.JwtServiceImpl;
import com.lucentlearn.payas.app.StudentCRUD.shared.dto.AdminDto;
import com.lucentlearn.payas.app.StudentCRUD.shared.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    JwtServiceImpl jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @GetMapping(path = "/{username}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AdminRest> getAdmin(@PathVariable String username, HttpServletRequest request)
    {
        String user = utils.getUserName(request);
        if(!user.equals(username)) throw new AdminServiceException(ErrorMessages.PERMISSION_NOT_GRANTED.getErrorMessage());

        AdminDto storedAdmin = adminService.getAdmin(username);
        AdminRest responseBody = new AdminRest();
        BeanUtils.copyProperties(storedAdmin, responseBody);
        return ResponseEntity.ok()
                .body(responseBody);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<AdminRest> createAdmin(@RequestBody AdminDetailsRequestModel admin){
        AdminDto adminDto = new AdminDto();
        BeanUtils.copyProperties(admin, adminDto);

        AdminDto createdAdmin = adminService.createAdmin(adminDto);

        AdminRest returnValue = new AdminRest();
        BeanUtils.copyProperties(createdAdmin, returnValue);

        return ResponseEntity.ok().body(returnValue);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> loginAdmin(@RequestBody UserLoginRequest user){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(), user.getPassword()
                )
        );

        HttpHeaders responseHeader = new HttpHeaders();
        String result="";

        UserEntity userEntity = userRepository.findByEmail(user.getEmail());
        if(userEntity == null ) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        if(userEntity!=null && !userEntity.getRole().equals("ROLE_ADMIN")){
            throw new AdminServiceException(ErrorMessages.PERMISSION_NOT_GRANTED.getErrorMessage());
        }

        UserEntity userDetails = new UserEntity();
        BeanUtils.copyProperties(user, userDetails);

        var jwtToken = jwtService.generateToken(userDetails);
        StringBuilder jwt = new StringBuilder("Bearer ");
        jwt.append(jwtToken);
        responseHeader.set("Authorization", new String(jwt));
        result = "Login Successful";


        return ResponseEntity.ok()
                .headers(responseHeader)
                .body(result);



    }

    @PutMapping("/{username}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AdminRest> updateAdmin(
            @PathVariable String username,
            @RequestBody AdminDetailsRequestModel admin,
            HttpServletRequest request){

        String user = utils.getUserName(request);
        if(!user.equals(username)) throw new AdminServiceException(ErrorMessages.PERMISSION_NOT_GRANTED.getErrorMessage());

        AdminDto adminDto = new AdminDto();
        BeanUtils.copyProperties(admin, adminDto);

        AdminDto updatedUser = adminService.updateUser(username, adminDto);

        AdminRest returnValue = new AdminRest();
        BeanUtils.copyProperties(updatedUser, returnValue);

        return ResponseEntity.ok()
                .body(returnValue);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteAdmin(@PathVariable String username, HttpServletRequest request){

        String user = utils.getUserName(request);
        if(!user.equals(username)) throw new AdminServiceException(ErrorMessages.PERMISSION_NOT_GRANTED.getErrorMessage());

        adminService.deleteAdmin(username);

        UserEntity userToDelete = userRepository.findByEmail(username);
        if(userToDelete==null) throw new AdminServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        userRepository.delete(userToDelete);

        return "Admin Deleted Successfully";
    }

}
