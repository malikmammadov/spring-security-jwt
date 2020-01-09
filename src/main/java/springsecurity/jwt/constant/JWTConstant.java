package springsecurity.jwt.constant;

public class JWTConstant {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_SECRET = "jwt-secret";
    public static final int EXPIRATION_TIME = 864_000_00;
}
