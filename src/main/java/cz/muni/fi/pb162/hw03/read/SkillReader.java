package cz.muni.fi.pb162.hw03.read;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;

/**
 * Skill reader interface
 * @author Jakub Cechacek
 */
public interface SkillReader {

    /**
     * Loads character's skills from file
     * @param path file path
     * @param charset file charset/encoding
     * @return an instance of itself
     * @throws IOException in case of IO error
     */
    SkillReader readSkills(Path path, Charset charset) throws IOException;
}
