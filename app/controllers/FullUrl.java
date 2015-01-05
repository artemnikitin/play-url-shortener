package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import utility.DataBase;

public class FullUrl extends Controller {

    public static Result redirectTo(String code){
        String url = DataBase.getFullUrlByShort(code);
        if(url.equals("")){
            return badRequest("URL not find! Please check that your submit.");
        } else {
            response().setHeader("Location", url);
            return movedPermanently("GO TO: " + url);
        }
    }

}
