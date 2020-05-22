package org.ggyool.exam01.web;

import lombok.RequiredArgsConstructor;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final Environment env;

    @GetMapping("/profile")
    public String profile(){
        // 실행중인 active profile을 모두 가져옵니다.
        // real, oauth, real-db 등이 활성화 되어 있다면, 3개가 담겨있다.
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        List<String> realProfiles = Arrays.asList("real", "real1", "real2");
        String defaultProfile = profiles.isEmpty()? "default" : profiles.get(0);

        return profiles.stream()
                .filter(realProfiles::contains)
                .findAny()
                .orElse(defaultProfile);
    }


}
