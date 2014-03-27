package controllers;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import utility.DataBase;
import utility.RandomGeneration;
import utility.Signature;

public class ShortUrl extends Controller {

    public static Result makeShort(){
        String url = getUrlFromBody(request());
        Logger.info("URL: " + url);
        Signature signature = new Signature(url);
        String token = signature.calculate();
        Logger.info("Token: " + token);
        String shortUrl;
        if(DataBase.checkTargetUrlExist(url)){
            shortUrl = DataBase.getShortUrlByToken(token);
            Logger.info("URL already exist, shortUrl: " + shortUrl);
        } else {
            shortUrl = getShortUrl(token);
            Logger.info("URL not exist, shortUrl: " + shortUrl);
            DataBase.insertUrl(token, url, shortUrl);
        }
        response().setContentType("application/json");
        return ok("{\"url_short\":\""+shortUrl+"\"}");
    }

    private static String getUrlFromBody(Http.Request request){
        return request.body().asJson().findPath("url").toString().replace("\"", "");
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
