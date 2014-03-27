package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Link extends Model{

    private static final long serialVersionUID = 1L;

    @Id
    @Constraints.Required
    public String token;

    @Constraints.Required
    public String url_target;

    @Constraints.Required
    public String url_short;

    public static Model.Finder<String, Link> find = new Model.Finder<String, Link>(String.class, Link.class);

}
