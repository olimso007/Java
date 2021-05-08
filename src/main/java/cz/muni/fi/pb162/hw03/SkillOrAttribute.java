package cz.muni.fi.pb162.hw03;

import java.util.Objects;

/**
 * Representation of either Skill or Attribute
 * @author Jakub Cechacek
 */
public class SkillOrAttribute {

    private String name;
    private int level;

    /**
     * Constructor for this class
     * @param name name of the skill/attribute
     * @param level level of the skill/attribute
     */
    public SkillOrAttribute(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SkillOrAttribute that = (SkillOrAttribute) o;
        return level == that.level && Objects.equals(name, that.name);
    }

    @Override public int hashCode() {
        return Objects.hash(name, level);
    }
}
