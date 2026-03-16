package com.chirayu.ecommerce.keycloak;

import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class KeycloakService {

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.admin-username}")
    private String adminUsername;

    @Value("${keycloak.admin-password}")
    private String adminPassword;

    private Keycloak getKeycloakInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm("master")
                .clientId("admin-cli")
                .username(adminUsername)
                .password(adminPassword)
                .build();
    }

    public void createUser(
            String username,
            String password,
            String email,
            String firstname,
            String lastname) {

        Keycloak keycloak = getKeycloakInstance();

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);

        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEnabled(true);
        user.setCredentials(Collections.singletonList(credential));

        Response response = keycloak.realm(realm).users().create(user);

        if (response.getStatus() == 201) {
            log.info("User created in Keycloak: {}", username);


            String userId = response.getLocation().getPath()
                    .replaceAll(".*/([^/]+)$", "$1");

            RoleRepresentation userRole = keycloak.realm(realm)
                    .roles()
                    .get("USER")
                    .toRepresentation();

            keycloak.realm(realm)
                    .users()
                    .get(userId)
                    .roles()
                    .realmLevel()
                    .add(List.of(userRole));

            log.info("USER role assigned to: {}", username);
        } else if (response.getStatus() == 409) {
            log.error("User already exists in Keycloak: {}", username);
            throw new RuntimeException(
                    "Username already exists: " + username);
        } else {
            log.error("Failed to create user in Keycloak status: {}",
                    response.getStatus());
            throw new RuntimeException(
                    "Failed to create user in Keycloak");
        }
    }
    public void deleteUser(String username){
        Keycloak keycloak=getKeycloakInstance();
        var user=keycloak.realm(realm)
                .users()
                .search(username);
        if (!user.isEmpty()){
            keycloak.realm(realm)
                    .users()
                    .get(user.get(0).getId())
                    .remove();
            log.info("User deleted from Keycloak: {}", username);
        }else {
            log.warn("User not found in Keycloak: {}", username);
        }
    }
}
