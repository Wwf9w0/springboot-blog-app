package com.blog.app.repository;

import com.blog.app.model.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {
        Setting findByKey(String key);
}
