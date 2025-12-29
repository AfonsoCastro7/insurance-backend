package com.mediadortrack.api.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class teste {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }
}

