package cz.muni.fi.pb162.hw03.impl;

import cz.muni.fi.pb162.hw03.Buildable;
import cz.muni.fi.pb162.hw03.SkillOrAttribute;
import cz.muni.fi.pb162.hw03.read.AttributeReader;
import cz.muni.fi.pb162.hw03.read.SkillReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representation of game character
 * @author Jakub Cechacek and Alisia Lyzhina
 */
public class Character {
    private String name;
    private int age;
    private String occupation;
    private int health;
    private int sanity;
    private int inventorySize;
    private List<SkillOrAttribute> attributes = new ArrayList<>();
    private List<SkillOrAttribute> skills = new ArrayList<>();

    /**
     * @param name of character
     * @param age of character
     * @param occupation of character
     * @param health of character
     * @param sanity of character
     * @param inventorySize of character
     * @param attributes of character
     * @param skills of character
     */
    public Character(String name, int age, String occupation, int health, int sanity,
                     int inventorySize, List<SkillOrAttribute> attributes, List<SkillOrAttribute> skills) {
        this.name = name;
        this.age = age;
        this.occupation = occupation;
        this.health = health;
        this.sanity = sanity;
        this.inventorySize = inventorySize;
        this.attributes.addAll(attributes);
        this.skills.addAll(skills);
    }

    /**
     * @return age of character
     */
    public int getAge() {
        return age;
    }

    /**
     * @return health of character
     */
    public int getHealth() {
        return health;
    }

    /**
     * @return Inventory Size of character
     */
    public int getInventorySize() {
        return inventorySize;
    }

    /**
     * @return sanity of character
     */
    public int getSanity() {
        return sanity;
    }

    /**
     * @return name of character
     */
    public String getName() {
        return name;
    }

    /**
     * @return attributes of character
     */
    public List<SkillOrAttribute> getAttributes() {
        return Collections.unmodifiableList(attributes);
    }

    /**
     * @return occupation of character
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * @return skills of character
     */
    public List<SkillOrAttribute> getSkills() {
        return Collections.unmodifiableList(skills);
    }

    /**
     * Builder class fro {@link Character}
     */
    public static class Builder implements Buildable, AttributeReader, SkillReader {
        private String name;
        private int age;
        private String occupation = "Citizen";
        private int health;
        private int sanity;
        private int inventorySize = 42;
        private List<SkillOrAttribute> attributes = new ArrayList<>();
        private List<SkillOrAttribute> skills = new ArrayList<>();

        @Override
        public Character build() {
            return new Character(name, age, occupation, health, sanity, inventorySize, attributes, skills);
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            if (age <= 0) {
                throw new IllegalArgumentException("Age should be positive");
            }
            this.age = age;
            return this;
        }

        public Builder setHealth(int health) {
            if (health < 1 || health > 10) {
                throw new IllegalArgumentException("Health should be in range 1-10");
            }
            this.health = health;
            return this;
        }

        public Builder setOccupation(String occupation) {
            this.occupation = occupation;
            return this;
        }

        public Builder setSanity(int sanity) {
            if (sanity < 1 || sanity > 10) {
                throw new IllegalArgumentException("Sanity should be in range 1-10");
            }
            this.sanity = sanity;
            return this;
        }

        public Builder setAttributes(List<SkillOrAttribute> attributes) {
            this.attributes = attributes;
            return this;
        }

        public Builder setInventorySize(int inventorySize) {
            if (inventorySize < 3 || inventorySize % 3 != 0){
                throw new IllegalArgumentException("Inventory size should be positive and " +
                        " divisible by three without leaving a remainder");
            }
            this.inventorySize = inventorySize;
            return this;
        }

        public Builder setSkills(List<SkillOrAttribute> skills) {
            this.skills = skills;
            return this;
        }

        /**
         * @param path to file
         * @param charset of reading file
         * @return list of skills or attribute
         * @throws IOException
         */
        private List<SkillOrAttribute> readHelper(Path path, Charset charset) throws IOException {
            List<String> lines = Files.readAllLines(path, charset);
            List<SkillOrAttribute> result = new ArrayList<>();
            for (String line: lines) {
                String[] skillOrAttribute = line.split(" : ");

                if (skillOrAttribute.length != 2) {
                    throw new IllegalArgumentException("Invalid string format: " + line);
                }
                if (skillOrAttribute[1].equals("R")) {
                    int lvl = (int) (Math.random() * 5);
                    result.add(new SkillOrAttribute(skillOrAttribute[0], lvl));
                } else {
                    try {
                        int lvl = Integer.parseInt(skillOrAttribute[1]);
                        if (lvl > 5 || lvl < 0) {
                            throw new IllegalArgumentException("Invalid level in line: " + line);
                        }
                        result.add(new SkillOrAttribute(skillOrAttribute[0], lvl));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid string format: " + line);
                    }
                }
            }
            return result;
        }

        @Override
        public Builder readAttributes(Path path, Charset charset) throws IOException {
            List<SkillOrAttribute> result = readHelper(path, charset);
            this.setAttributes(result);
            return this;
        }

        /**
         * @param fileName file path
         * @param charset of file
         * @return an instance of itself
         * @throws IOException in case of IO error
         */
        public Builder readAttributes(String fileName, Charset charset) throws IOException {
            Path path = Paths.get(fileName);
            return readAttributes(path, charset);
        }

        @Override
        public Builder readSkills(Path path, Charset charset) throws IOException {
            List<SkillOrAttribute> result = readHelper(path, charset);
            this.setSkills(result);
            return this;
        }
        /**
         * @param fileName file path
         * @param charset of file
         * @return an instance of itself
         * @throws IOException in case of IO error
         */
        public Builder readSkills(String fileName, Charset charset) throws IOException {
            Path path = Paths.get(fileName);
            return readSkills(path, charset);
        }
    }

    /**
     * Returns instance of Builder
     * @return builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }
}
