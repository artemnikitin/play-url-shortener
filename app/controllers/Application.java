package controllers;

import models.Link;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller{

    public static Result index(){
        return ok("This is sample URL shortener application");
    }

}
