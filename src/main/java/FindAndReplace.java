import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class FindAndReplace {
  public static void main(String[] args) {
  String layout = "templates/layout.vtl";

  get("/", (request, response) -> {
     Map<String, Object> model = new HashMap<String, Object>();
     model.put("template", "templates/home.vtl");
     return new ModelAndView(model, layout);
   }, new VelocityTemplateEngine());

   get("/results", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/results.vtl");

      String input = request.queryParams("text");
      String toFind = request.queryParams("toFind");
      String toReplace = request.queryParams("toReplace");
      String finished = findReplace(input, toFind, toReplace);

      model.put("finished", finished);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
  public static String findReplace(String input, String toFind, String toReplace) {
    String[] phraseSplit = input.split(" ");
    String lowerCaseFind = toFind.toLowerCase();
    for ( Integer i = 0; i < phraseSplit.length; i++) {
      String lowerCaseIndex = phraseSplit[i].toLowerCase();
      if (lowerCaseIndex.equals(lowerCaseFind)) {
      phraseSplit[i] = lowerCaseIndex.replace(toFind, toReplace);
      }
    }
    String finishedPhrase = "";
    for ( String phraseWord : phraseSplit) {
      finishedPhrase = finishedPhrase + " " + phraseWord;
    }
    finishedPhrase = finishedPhrase.trim();
    return finishedPhrase;
  }

}
