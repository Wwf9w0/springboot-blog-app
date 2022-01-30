package com.blog.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppSetting {
    public static final String SITE_NAME = "site_name";
    public static final String SITE_SLOGAN = "site_slogan";
    public static final String PAGE_SIZE = "page_size";


}
