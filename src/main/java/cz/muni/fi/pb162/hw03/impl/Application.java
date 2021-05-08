package cz.muni.fi.pb162.hw03.impl;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Character Generator application
 * @author Jakub Cechacek and Alisa Lyzhina
 */
public class Application {

    @Parameter(names = "--help", help = true, order = 0)
    private boolean help = false;

    public boolean isHelp() {
        return help;
    }

    @Parameter(names = {"--encoding"}, description = "Encoding of input files")
    private String encoding = "UTF-8";


    @Parameter(names = {"--name"}, required = true, description = "Character's  name")
    private  String name;


    @Parameter(names = {"--occupation"}, description = "Character's occupation")
    private String occupation = "Citizen";


    @Parameter(names = {"--inventory"}, validateWith = DivByThree.class,
            description = "Character's inventory size. Should should be a positive multiplier of 3")
    private int inventory = 42;


    @Parameter(names = {"--attrs"}, description = "Attribute file descriptor")
    private String attributes = "attributes.txt";


    @Parameter(names = {"--skills"}, description = "Skills file descriptor")
    private String skills = "skills.txt";


    @Parameter(names = {"--out"}, description = "Output file name")
    private String out = "character.html";


    @Parameter(names = {"--sanity"}, required = true, validateWith =  CheckRange.class,
            description = "Character's sanity, should be an integer from 1 to 10")
    private int sanity;


    @Parameter(names = {"--age"}, required = true, validateWith = PositiveInteger.class,
            description = "Character's age, should be a positive integer")
    private int age;


    @Parameter(names = {"--health"}, required = true, validateWith = CheckRange.class,
            description = "Character's health, should be an integer from 1 to 10")
    private int health;


    /**
     * Program entry point.
     * You should parse the command line arguments and produce output html file according to them.
     * @param args raw command line arguments
     */
    public static void main(String[] args) {
        Application app = new Application();
        JCommander commander = JCommander.newBuilder().addObject(app).build();
        commander.setProgramName("char-gen");

        try {
            commander.parse(args);
        } catch (ParameterException e) {
            System.err.println(e.getMessage());
            return;
        }
        if (app.isHelp()) {
            commander.usage();
        }
        Charset charset = Charset.forName(app.encoding);
        AbstractHtmlWriter html = new AbstractHtmlWriter();
        try {
            Character character = new Character.Builder()
                    .setName(app.name)
                    .readSkills(app.skills, charset)
                    .readAttributes(app.attributes, charset)
                    .setAge(app.age)
                    .setHealth(app.health)
                    .setInventorySize(app.inventory)
                    .setOccupation(app.occupation)
                    .setSanity(app.sanity)
                    .build();
            Path path = Paths.get(app.out);
            html.write(character, path, charset);
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
