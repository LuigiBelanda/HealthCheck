package com.healtcheck.labeng.services;

import com.healtcheck.labeng.dtos.UserLoginDTO;
import com.healtcheck.labeng.dtos.UserRegisterDTO;
import com.healtcheck.labeng.entities.User;

public interface UserService {
    User register(UserRegisterDTO userRegisterDTO);
    User login(UserLoginDTO userLoginDTO);
}
