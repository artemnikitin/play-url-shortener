package utility;

import com.avaje.ebean.Ebean;
import models.Link;

import java.util.List;

public final class DataBase {

    public static String getFullUrlByShort(String code){
        List<Link> link = Link.find.where().eq("url_short", code).findList();
        if(link.size() == 1) {
            return link.get(0).url_target;
        }else{
            return "";
        }
    }

    public static String getShortUrlByToken(String token){
        List<Link> link = Link.find.where().eq("token", token).findList();
        if(link.size() == 1) {
            return link.get(0).url_short;
        }else{
            return "";
        }
    }

    public static boolean checkTargetUrlExist(String url){
        List<Link> link = Link.find.where().eq("url_target", url).findList();
        return link.size() > 0;
    }

    public static void insertUrl(String token, String url_target, String url_short){
        Link link = new Link();
        link.token = token;
        link.url_short = url_short;
        link.url_target = url_target;
        Ebean.save(link);
    }

    public static boolean checkUnique(String code){
        List<Link> link = Link.find.where().eq("url_short", code).findList();
        return link.size() == 0;
    }

}
