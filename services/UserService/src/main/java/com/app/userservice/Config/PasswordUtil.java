package com.app.userservice.Config;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    /** Hash a raw password for storage. */
    public static String hash(String plain) {
        // gensalt’s default log_rounds is 10; raise it for more work factor
        return BCrypt.hashpw(plain, BCrypt.gensalt());
    }

    /** Check a plaintext against a stored hash. */
    public static boolean verify(String plain, String hashed) {
        return BCrypt.checkpw(plain, hashed);
    }
}
