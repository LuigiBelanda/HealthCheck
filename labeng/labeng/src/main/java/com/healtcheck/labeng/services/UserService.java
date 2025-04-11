package com.healtcheck.labeng.services;

import com.healtcheck.labeng.dto.UserLoginDTO;
import com.healtcheck.labeng.dto.UserRegisterDTO;
import com.healtcheck.labeng.entities.User;

public interface UserService {
    User register(UserRegisterDTO userRegisterDTO);
    User login(UserLoginDTO userLoginDTO);
}
