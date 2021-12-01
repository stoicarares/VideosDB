package main;

import actions.queries.actors.Average;
import actions.queries.actors.Awards;
import actions.queries.actors.FilterDescription;
import actions.queries.actors.QueryActor;
import actions.queries.users.NumberOfRatings;
import actions.queries.videos.Longest;
import actions.queries.videos.MostViewed;
import actions.queries.videos.QueryVideo;
import actions.queries.videos.Rating;
import actions.recommendations.Recommandation;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import entertainment.*;
import actions.commands.Command;
import actions.queries.users.QueryUser;
import actions.recommendations.premium.Favorite;
import actions.recommendations.basic.BestUnseen;
import actions.recommendations.basic.Standard;
import actions.recommendations.premium.Popular;
import actions.recommendations.premium.Search;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        Database.getDatabase().transferActors(input.getActors());
        Database.getDatabase().transferMovies(input.getMovies());
        Database.getDatabase().transferSerials(input.getSerials());
        Database.getDatabase().transferUsers(input.getUsers());
        Database.getDatabase().getAllVideos(input.getMovies(), input.getSerials());
        //TODO add here the entry point to your implementation

        for (ActionInputData action:input.getCommands()) {
            StringBuilder output = null;
            if (action.getActionType().equals("command")) {
                Command command = Command.instantiation(action.getType(), action.getUsername(),
                                                        action.getTitle(), action.getGrade(),
                                                        action.getSeasonNumber());
                output = command.applyCommand();
            } else if (action.getActionType().equals("query")) {
                if (action.getObjectType().equals("users")) {
                    QueryUser query = new NumberOfRatings(action.getObjectType(),
                            action.getSortType(), action.getCriteria(), action.getNumber());
                    output = query.applyQuery();
                } else if (action.getObjectType().equals("actors"))  {
                    QueryActor query;
                    if (action.getCriteria().equals("average")) {
                        query = new Average(action.getObjectType(), action.getSortType(),
                                action.getCriteria(), action.getNumber());
                    } else if (action.getCriteria().equals("awards")) {
                        query = new Awards(action.getObjectType(), action.getSortType(),
                                action.getCriteria(), action.getNumber(), action.getFilters());
                    } else {
                        query = new FilterDescription(action.getObjectType(), action.getSortType(),
                                action.getCriteria(), action.getNumber(), action.getFilters());
                    }
                    output = query.applyQuery();
                } else if (action.getCriteria().equals("favorite")) {
                    QueryVideo query;
                    query = new actions.queries.videos.Favorite(action.getObjectType(),
                            action.getSortType(), action.getCriteria(), action.getNumber(),
                            action.getFilters());
                    output = query.applyQuery();
                } else if (action.getCriteria().equals("ratings")) {
                    QueryVideo query;
                    query = new Rating(action.getObjectType(), action.getSortType(),
                            action.getCriteria(), action.getNumber(), action.getFilters());
                    output = query.applyQuery();
                } else if (action.getCriteria().equals("longest")) {
                    QueryVideo query;
                    query = new Longest(action.getObjectType(), action.getSortType(),
                            action.getCriteria(), action.getNumber(), action.getFilters());
                    output = query.applyQuery();
                } else if (action.getCriteria().equals("most_viewed")) {
                    QueryVideo query;
                    query = new MostViewed(action.getObjectType(), action.getSortType(),
                            action.getCriteria(), action.getNumber(), action.getFilters());
                    output = query.applyQuery();
                }
            } else {
                Recommandation recommendation = switch (action.getType()) {
                    case "standard" -> new Standard(action.getType(), action.getUsername());
                    case "best_unseen" -> new BestUnseen(action.getType(), action.getUsername());
                    case "popular" -> new Popular(action.getType(), action.getUsername());
                    case "favorite" -> new Favorite(action.getType(), action.getUsername());
                    default -> new Search(action.getType(), action.getUsername(),
                            action.getGenre());
                };

                output = recommendation.applyRecommendation();
            }

            assert output != null;
            JSONObject jsonObject = fileWriter.writeFile(action.getActionId(),
                    output.toString());
            arrayResult.add(jsonObject);
        }
        fileWriter.closeJSON(arrayResult);
    }
}
