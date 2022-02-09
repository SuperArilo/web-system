package online.superarilo.myblog.realm;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 *
 * @author
 */
public class UserToken implements AuthenticationToken {
    private String token;

    public UserToken(String token){
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
