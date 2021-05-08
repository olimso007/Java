package cz.muni.fi.pb162.hw03.impl;

import cz.muni.fi.pb162.hw03.SkillOrAttribute;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jakub Cechacek
 */

public class CharacterTest {

    private Character.Builder builder;

    private final SkillOrAttribute TEST_ATTR = new SkillOrAttribute("Intelligence", 4);
    private final SkillOrAttribute TEST_SKILL = new SkillOrAttribute("App Destroying", 5);
    private final List<SkillOrAttribute> TEST_ATTRS =  List.of(
            new SkillOrAttribute("Strength", 1), new SkillOrAttribute("Dexterity", 2), new SkillOrAttribute("Intelligence", 4)
    );
    private final List<SkillOrAttribute> TEST_SKILLS =  List.of(
            new SkillOrAttribute("App destroying", 5), new SkillOrAttribute("Hw evaluation", 4)
    );

    @Before
    public void setup() {
        builder = Character.newBuilder();
        Assertions.assertThat(builder).isNotNull();
    }

    @Test
    public void shouldCorrectlyCreateCharacterWithoutSkillsAndAttributes() {
        Character character = builder
                .setName("Tom Tester")
                .setAge(42)
                .setHealth(8)
                .setSanity(7)
                .setInventorySize(42)
                .setOccupation("App Destroyer")
                .setAttributes(new ArrayList<>(Collections.singleton(TEST_ATTR)))
                .setSkills(new ArrayList<>(Collections.singleton(TEST_SKILL)))
                .build();

        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(character.getName()).isEqualTo("Tom Tester");
        assertions.assertThat(character.getAge()).isEqualTo(42);
        assertions.assertThat(character.getHealth()).isEqualTo(8);
        assertions.assertThat(character.getSanity()).isEqualTo(7);
        assertions.assertThat(character.getOccupation()).isEqualTo("App Destroyer");
        assertions.assertThat(character.getAttributes()).containsExactly(TEST_ATTR);
        assertions.assertThat(character.getSkills()).containsExactly(TEST_SKILL);
        assertions.assertThatThrownBy(() -> character.getAttributes().add(null))
                .withFailMessage("Returned attributes should be in unmodifiable list")
                .isInstanceOf(UnsupportedOperationException.class);
        assertions.assertThatThrownBy(() -> character.getSkills().add(null))
                .withFailMessage("Returned attributes should be in unmodifiable list")
                .isInstanceOf(UnsupportedOperationException.class);
        assertions.assertAll();;
    }

    @Test
    public void shouldCorrectlyParseAttributes() throws URISyntaxException, IOException {
        Character character = builder
                .readAttributes( Paths.get(getClass().getResource("/attributes_small.txt").toURI()), StandardCharsets.UTF_8)
                .build();
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(character.getAttributes()).containsExactlyElementsOf(TEST_ATTRS);
        assertions.assertAll();
    }

    @Test
    public void shouldCorrectlyParseSkills() throws URISyntaxException, IOException {
        Character character = builder
                .readSkills( Paths.get(getClass().getResource("/skills_small.txt").toURI()), StandardCharsets.UTF_8)
                .build();
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(character.getSkills()).hasSize(TEST_SKILLS.size() + 1);
        assertions.assertThat(character.getSkills()).containsAnyElementsOf(TEST_SKILLS);
        assertions.assertThat(character.getSkills()).anySatisfy(s -> assertions.assertThat(s.getName()).isEqualTo("Exam failing"));
        assertions.assertAll();
    }
}
