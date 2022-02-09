package online.superarilo.myblog.generator;

import java.util.UUID;

public final class TokenGenerator {


    public static String generateToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
