import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Animals;
import models.Location;
import models.Ranger;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.*;
public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");

        String connectionString = "jdbc:postgresql://localhost:5432/wildlife";
        Sql2o sql2o = new Sql2o(connectionString, "dhosio", "MaFaD@niel2019");

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Location> allLocations = Location.getAll();
            List<Ranger> allRangers = Ranger.all();

            model.put("rangers", allRangers);
            model.put("locations", allLocations);
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "login.hbs");
        }, new HandlebarsTemplateEngine());

        post("/rangers/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Ranger> allRangers = Ranger.all();
            int rangersNumber = Ranger.all().size();

            Ranger.deleteAll();

            model.put("username", req.session().attribute("username"));
            model.put("rangersNumber", rangersNumber);
            model.put("rangers", allRangers);
            res.redirect("/rangers");
            return null;
        }, new HandlebarsTemplateEngine());

        post("/homepage", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String enteredUsername = req.queryParams("username");
            req.session().attribute("username", enteredUsername);
            List<Location> allLocations = Location.getAll();
            List<Ranger> allRangers = Ranger.all();

            model.put("rangers", allRangers);
            model.put("locations", allLocations);
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "homepage.hbs");
        }, new HandlebarsTemplateEngine());

        get("/homepage", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Location> allLocations = Location.getAll();
            List<Ranger> allRangers = Ranger.all();

            model.put("rangers", allRangers);
            model.put("locations", allLocations);
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "homepage.hbs");
        }, new HandlebarsTemplateEngine());

        post("/animals/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Animals> allAnimals = Animals.all();

            Animals.deleteAll();

            model.put("username", req.session().attribute("username"));
            model.put("animals", allAnimals);
            res.redirect("/homepage");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/animals", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Animals> allAnimals = Animals.all();


            model.put("username", req.session().attribute("username"));
            model.put("animals", allAnimals);
            return new ModelAndView(model, "animals.hbs");
        }, new HandlebarsTemplateEngine());

        post("/animals", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
                String species = req.queryParams("species");
                int age = Integer.parseInt(req.queryParams("age"));
                String location = req.queryParams("location");
                int rangerId = Integer.parseInt(req.queryParams("rangerId"));

                Animals newAnimal = new Animals(species, age, location, rangerId);
                newAnimal.save();

                List<Animals> allAnimals = Animals.all();

            model.put("animals", allAnimals);
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "animals.hbs");
        }, new HandlebarsTemplateEngine());

        get("/rangers", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Ranger> allRangers = Ranger.all();
            int rangersNumber = Ranger.all().size();


            model.put("username", req.session().attribute("username"));
            model.put("rangersNumber", rangersNumber);
            model.put("rangers", allRangers);
            return new ModelAndView(model, "rangers.hbs");
        }, new HandlebarsTemplateEngine());

        post("/rangers", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
                String name = req.queryParams("name");
                int badgeNumber = Integer.parseInt(req.queryParams("badgeNumber"));
                int radioNumber = Integer.parseInt(req.queryParams("radioNumber"));
                String rank = req.queryParams("rank");

                Ranger newRanger = new Ranger(name, badgeNumber, radioNumber, rank);
                    newRanger.save();
                List<Ranger> allRangers = Ranger.all();

            int rangersNumber = Ranger.all().size();

            model.put("rangersNumber", rangersNumber);
            model.put("rangers", allRangers);
            model.put("username", req.session().attribute("username"));
            res.redirect("/rangers");
            return null;
        }, new HandlebarsTemplateEngine());

        get("/rangers/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Ranger> allRangers = Ranger.all();
            int rangersNumber = Ranger.all().size();

            model.put("rangersNumber", rangersNumber);
            model.put("rangers", allRangers);
            model.put("username", req.session().attribute("username"));
            return new ModelAndView(model, "newranger.hbs");
        }, new HandlebarsTemplateEngine());

        get
    }
}
