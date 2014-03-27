package utility;

import com.ning.http.util.Base64;
import play.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Signature {

    private String url;
    private static final String KEY = "B^Fsvx5$!tf%$$5654";
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    public Signature(String url){
        this.url = url;
    }

    public String calculate(){
        String result;
        try {
            SecretKeySpec signingKey = new SecretKeySpec(KEY.getBytes(), HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(url.getBytes());
            result = Base64.encode(rawHmac);
        } catch (Exception e) {
            result = "";
            Logger.error("Error on calculating HmacSHA1 signature: " + e.getStackTrace());
        }
        return result;
    }

}
