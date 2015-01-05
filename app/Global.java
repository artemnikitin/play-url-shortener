import play.GlobalSettings;
import play.libs.F;
import play.mvc.Http;
import play.mvc.SimpleResult;

import static play.mvc.Results.*;

public class Global extends GlobalSettings {

    @Override
    public F.Promise<SimpleResult> onHandlerNotFound(Http.RequestHeader request) {
        return F.Promise.pure(notFound("Requested URL not found!"));
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
