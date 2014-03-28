package controllers;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import utility.DataBase;
import utility.RandomGeneration;
import utility.Signature;
import views.html.defaultpages.error;

import java.util.Arrays;

public class ShortUrl extends Controller {

    public static Result makeShort(){
        response().setContentType("application/json");
        String url = getUrlFromBody(request());
        Logger.info("URL: " + url);
        if(url.equals("")){
            Logger.error("Error happened when trying parse body as JSON or JSON malformed");
            return badRequest("{\"url_short\":\"\",\"error\":\"1\"}");
        } else{
            Signature signature = new Signature(url);
            String token = signature.calculate();
            Logger.info("Token: " + token);
            String shortUrl;
            if (DataBase.checkTargetUrlExist(url)) {
                shortUrl = DataBase.getShortUrlByToken(token);
                Logger.info("URL already exist, shortUrl: " + shortUrl);
            } else {
                shortUrl = getShortUrl(token);
                Logger.info("URL not exist, shortUrl: " + shortUrl);
                DataBase.insertUrl(token, url, shortUrl);
            }
            return ok("{\"url_short\":\"" + shortUrl + "\",\"error\":\"0\"}");
        }
    }

    private static String getUrlFromBody(Http.Request request) {
        try {
            return request.body().asJson().findPath("url").toString().replace("\"", "");
        } catch(Exception e) {
            e.printStackTrace();
            Logger.error("Malformed JSON detected!");
            throw new NullPointerException();
        }
    }

    private static String getShortUrl(String token){
        String result;
        result = DataBase.getShortUrlByToken(token);
        if(result.equals("")) {
            result = RandomGeneration.randomString(5);
            while( !DataBase.checkUnique(result) ){
                result = RandomGeneration.randomString(5);
            }
            return result;
        } else{
            return result;
        }
    }

}
