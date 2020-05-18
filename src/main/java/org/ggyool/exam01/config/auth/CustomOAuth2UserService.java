package org.ggyool.exam01.config.auth;

import lombok.RequiredArgsConstructor;
import org.ggyool.exam01.config.auth.dto.OAuthAttributes;
import org.ggyool.exam01.config.auth.dto.SessionUser;
import org.ggyool.exam01.domain.user.User;
import org.ggyool.exam01.domain.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    // 많은 것들이 생략되어 있어서, 흐름을 이해하기 힘들었다. (아직 잘 모르지만..)
    // loadUser는 user가 로그인 한 이후 실행되는 메소드이다.
    // accessToken과 값을 받아온느데 필요한 데이터는 userRequest에 모두 들어있다.
    // 1. custom OAuthAttribute를 만든다. (OAuthAttributes.of)
    // delegete.loaduser 는 Exception과 attributes를 받아온다.
    // 생성자 같은걸 안쓰고 static 메소드로 하는지는 잘 모르겠다.
    // 2. db에 save, update를 한다.
    // saveOrUpdate 메소드 찾아서 있으면 혹시 달라졌나 update도 해보고
    // 없으면 toEntity가 실행되어 Role도 GUEST로 설정되며 객체 생성
    // 3. session에도 save, update 를 한다. (Serialize 때문에 SessionUser 클래스를 만들어 사용)
    // 4. DefaultOAuth2User의 생성자를 실행하여 OAuth2User를 return 한다.
    // 어디에 쓰이는지는 모름
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 로그인 진행중인 서비스를 구분하는 코드
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // oauth2 로그인 진행 시 키가 되는 필드 값을 의미, 구글은 sub, na-,ka-는 기본지원x
        // 어떤 소셜서비스든 그 서비스에서 각 계정마다의 유니크한 id값을 전달해주겠다는 의미
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                                        .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity->entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }




}
