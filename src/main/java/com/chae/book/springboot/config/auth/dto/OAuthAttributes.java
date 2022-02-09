package com.chae.book.springboot.config.auth.dto;

import com.chae.book.springboot.domain.user.Role;
import com.chae.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
@Getter
public class OAuthAttributes {
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String name;
    private final String email;
    private final String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,String name, String email, String picture){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String usernameAttributeName, Map<String, Object> attributes){
        OAuthAttributes oAuthAttributes = null;
        switch(registrationId){
            case "naver":
                oAuthAttributes = ofNaver("id", attributes);
                break;
            case "google":
                oAuthAttributes = ofGoogle(usernameAttributeName, attributes);
                break;
        }
        return oAuthAttributes;
    }

    private static OAuthAttributes ofNaver(String usernameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .name((String)response.get("name"))
                .email((String)response.get("email"))
                .picture((String)response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(usernameAttributeName)
                .build();
    }

    public static OAuthAttributes ofGoogle(String usernameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .picture((String)attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(usernameAttributeName)
                .build();
    }
    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
