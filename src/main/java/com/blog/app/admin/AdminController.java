package com.blog.app.admin;

import com.blog.app.model.forms.SettingsForm;
import com.blog.app.service.AppSetting;
import com.blog.app.support.MessageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AppSetting appSetting;

    public ResponseEntity<Boolean> updateSettings(@Valid SettingsForm settingsForm, Errors errors){
       boolean apps = false;
        if (errors.hasErrors()){
            apps = true;
        }else {
            appSetting.setSiteName(settingsForm.getSiteName());
            appSetting.setSiteSlogan(settingsForm.getSiteSlogan());
            appSetting.setPageSize(settingsForm.getPageSize());
            log.info("Update settings succesfully.");
        }
        return ResponseEntity.ok(apps);
    }
}
