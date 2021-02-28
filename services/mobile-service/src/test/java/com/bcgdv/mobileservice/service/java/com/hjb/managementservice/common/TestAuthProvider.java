package com.bcgdv.mobileservice.service.java.com.hjb.managementservice.common;//package com.bcgdv.mobileservice.service.java.com.hjb.managementservice.common;
//
//import com.hjb.sharedservice.security.UserDetailsIdentifiable;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.OAuth2Request;
//
//import java.util.Map;
//import java.util.Set;
//
//import static org.springframework.http.HttpHeaders.AUTHORIZATION;
//import static utils.functions.Value.with;
//
//public class TestAuthProvider {
//
//    public static OAuth2Authentication authenticate(UserDetailsIdentifiable user) {
//        String userId = user.getId();
//        String email = user.getUsername();
//        OAuth2Request request = new OAuth2Request(
//                Map.of("grant_type", "password", "username", email),
//                "browser",
//                user.getAuthorities(),
//                true,
//                Set.of("ui"),
//                Set.of(),
//                null,
//                Set.of(),
//                Map.of()
//        );
//
//        UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(
//                user, null, user.getAuthorities()
//        );
//        userAuthentication.setDetails(
//                Map.of("grant_type", "password", "username", email, "principal", Map.of("id", userId))
//        );
//        return new OAuth2Authentication(request, userAuthentication);
//    }
//
//
////    public static StompHeaders stompAuthorizationHeader(String accessToken) {
////        return with(
////                new StompHeaders(),
////                stompHeaders -> stompHeaders.addAll(new LinkedMultiValueMap<>(authorizationHeader(accessToken)))
////        );
////    }
//
//    public static HttpHeaders authorizationHeader(String accessToken) {
//        return with(
//                new HttpHeaders(),
//                tokenHeaders -> tokenHeaders.add(AUTHORIZATION, "Bearer " + accessToken)
//        );
//    }
//}
