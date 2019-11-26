package com.toutiao.model;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
public class LoginCase {
    private int id;
    private String device;
    private String device_Id;
    private String version;
    private String account;
    private String password;


}

