package app.security;

import app.endpoints.BuildingSalesEndpoint;
import app.exception.BuildingSalesAppException;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AppIdentityStore implements IdentityStore {

    @Inject
    BuildingSalesEndpoint endpoint;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
            try {
                Account account = endpoint.findAccountByLogin(usernamePasswordCredential.getCaller());
                String role = account.getRole();
                //if(account.isActivate()){TODO potem usuń komentarz na bloku if
                if (usernamePasswordCredential.compareTo(account.getLogin(), account.getPassword())) {
                    return new CredentialValidationResult(account.getLogin(), new HashSet<>(Arrays.asList(role)));
                }
                //}
            }catch (BuildingSalesAppException e){
                return CredentialValidationResult.INVALID_RESULT;
            }
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;

    }

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        return IdentityStore.super.getCallerGroups(validationResult);
    }
}
