package za.co.ai.experiment.libcoreauth.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import za.co.ai.experiment.libcoreauth.JwtValidator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the JwtValidator class.
 */
public class JwtValidatorTest {
    
    private static final String VALID_JWKS_URI = "https://auth.gedmarc.co.za/realms/ai-experiment/protocol/openid-connect/certs";
    
    @Test
    @DisplayName("Constructor should accept valid JWKS URI")
    void testConstructorWithValidUri() {
        JwtValidator validator = new JwtValidator(VALID_JWKS_URI);
        assertEquals(VALID_JWKS_URI, validator.getJwksUri(), "JWKS URI should be stored correctly");
    }
    
    @Test
    @DisplayName("Constructor should reject null JWKS URI")
    void testConstructorWithNullUri() {
        assertThrows(IllegalArgumentException.class, () -> new JwtValidator(null),
                "Constructor should throw IllegalArgumentException for null JWKS URI");
    }
    
    @Test
    @DisplayName("Constructor should reject blank JWKS URI")
    void testConstructorWithBlankUri() {
        assertThrows(IllegalArgumentException.class, () -> new JwtValidator(""),
                "Constructor should throw IllegalArgumentException for blank JWKS URI");
    }
    
    @Test
    @DisplayName("validateToken should reject null token")
    void testValidateNullToken() {
        JwtValidator validator = new JwtValidator(VALID_JWKS_URI);
        assertFalse(validator.validateToken(null), "Null token should be invalid");
    }
    
    @Test
    @DisplayName("validateToken should reject blank token")
    void testValidateBlankToken() {
        JwtValidator validator = new JwtValidator(VALID_JWKS_URI);
        assertFalse(validator.validateToken(""), "Blank token should be invalid");
    }
    
    @Test
    @DisplayName("validateToken should accept token with at least one dot")
    void testValidateTokenWithDot() {
        JwtValidator validator = new JwtValidator(VALID_JWKS_URI);
        assertTrue(validator.validateToken("header.payload.signature"), 
                "Token with dots should be considered valid in this placeholder implementation");
    }
}