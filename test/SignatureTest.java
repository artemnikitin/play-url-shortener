import org.junit.Test;
import utility.Signature;

import static org.junit.Assert.assertEquals;

public class SignatureTest {

    @Test
    public void positiveCheck() {
        Signature signature = new Signature("http://www.linkedin.com/in/artemnikitin");
        assertEquals("Signature generation fail", "/2cLW94a0pSGT/TYOBW9fM49+KE=", signature.calculate());
    }

    @Test
    public void signEmptyString() {
        Signature signature = new Signature("");
        assertEquals("Signature generation fail", "SsaHnpr0A+abBeLPWyoBCJlgNPA=", signature.calculate());
    }




}
