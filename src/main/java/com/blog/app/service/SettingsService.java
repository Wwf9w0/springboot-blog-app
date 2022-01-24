package com.blog.app.service;

import com.blog.app.model.Setting;
import com.blog.app.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Objects;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class SettingsService {
    private final SettingRepository settingRepository;

    @Cacheable(value = "settingsCache", key = "#key", unless = "#result == null")
    public Serializable get(String key){
        Setting setting = settingRepository.findByKey(key);
        Serializable value = null;
        try{
            value = Objects.isNull(setting) ? null : setting.getValue();
        }catch (Exception ex){
            log.info("Connot deserialize setting value with key = {} {}", key ,ex);
        }

        log.info("Get setting {} from database value {}", key, value);
        return value;
    }

    @Cacheable(value = "settingCache", key = "#key", unless = "#result == null ")
    public Serializable get(String key, Serializable defaultValue){
        Serializable value = get(key);
        return Objects.isNull(value) ? defaultValue : value;
    }

    @CacheEvict(value = "settingsCache", key = "#key")
    public void put(String key, Serializable value){
        log.info("Update setting {} to database value {}", key, value);

        Setting setting = settingRepository.findByKey(key);
        if (Objects.isNull(setting)){
            setting = new Setting();
            setting.setKey(key);
        }
        try{
            setting.setValue(value);
            settingRepository.save(setting);
        }catch (Exception ex){
            log.info("Connot save setting value with type: {} key = {}-{}", value.getClass(), key, ex);
        }
    }
}
