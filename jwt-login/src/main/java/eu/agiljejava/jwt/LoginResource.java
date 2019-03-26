package eu.agiljejava.jwt;

import com.ibm.websphere.security.jwt.Claims;
import com.ibm.websphere.security.jwt.JwtBuilder;

import javax.ws.rs.BeanParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;

@Path("/authenticate")
public class LoginResource {

    @POST
    public Response authenticate(@BeanParam UserCredentials userCredentials) throws Exception {

        Set<String> roles = new HashSet<>();
        roles.add("senior");
        String token = buildJwt("duke", roles);

        return Response.ok(token).build();
    }


    private String buildJwt(String userName, Set<String> roles) throws Exception {
        return JwtBuilder.create("jwtFrontEndBuilder")
                .claim(Claims.SUBJECT, userName)
                .claim("upn", userName) // MP-JWT defined subject claim
                .claim("groups", roles.toArray(new String[roles.size()])) // MP-JWT builds an array from this
                .claim("custom-value", "Duke Rocks")
                .buildJwt()
                .compact();
    }
}
