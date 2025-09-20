package com.tracker.leetcode.service.impl;

import com.tracker.leetcode.repo.AuthSessionRepo;
import com.tracker.leetcode.service.AuthSessionService;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthSessionServiceImpl implements AuthSessionService {

    @Autowired
    private AuthSessionRepo authSessionRepo;


}
