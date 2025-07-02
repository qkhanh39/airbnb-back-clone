package com.khanh.airbnb.services;

import com.khanh.airbnb.dto.login.LoginRequestDto;

public interface LoginService {
    String loginUser(LoginRequestDto loginRequestDto);
}
