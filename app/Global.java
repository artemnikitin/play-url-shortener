import play.GlobalSettings;
import play.libs.F;
import play.mvc.Http;
import play.mvc.SimpleResult;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.internalServerError;
import static play.mvc.Results.redirect;

public class Global extends GlobalSettings {

    @Override
    public F.Promise<SimpleResult> onHandlerNotFound(Http.RequestHeader request) {
        return F.Promise.pure(redirect("/app/about"));
    }

    @Override
    public F.Promise<SimpleResult> onBadRequest(Http.RequestHeader requestHeader, String s) {
        return F.Promise.<SimpleResult>pure(badRequest("We have a problem with you request: " + s));
    }

    @Override
    public F.Promise<SimpleResult> onError(Http.RequestHeader requestHeader, Throwable throwable) {
        return F.Promise.<SimpleResult>pure(internalServerError("We have error, please send request later."));
    }

}
