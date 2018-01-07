package com.farm.app.web.rest;

import com.farm.app.config.ApplicationProperties;
import com.farm.app.config.DefaultProfileUtil;



import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ProfileInfoResource {

    private final Environment env;

    private final ApplicationProperties applicationProperties;

    public ProfileInfoResource(Environment env, ApplicationProperties applicationProperties) {
        this.env = env;
        this.applicationProperties = applicationProperties;
    }

    @GetMapping("/profile-info")
    public ProfileInfoVM getActiveProfiles() {
        String[] activeProfiles = DefaultProfileUtil.getActiveProfiles(env);
        return new ProfileInfoVM(activeProfiles, "dev");
    }

    class ProfileInfoVM {

        private String[] activeProfiles;

        private String ribbonEnv;

        ProfileInfoVM(String[] activeProfiles, String ribbonEnv) {
            this.activeProfiles = activeProfiles;
            this.ribbonEnv = ribbonEnv;
        }

        public String[] getActiveProfiles() {
            return activeProfiles;
        }

        public String getRibbonEnv() {
            return ribbonEnv;
        }
    }
}
