package cz.muni.fi.pb162.hw03.write;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import cz.muni.fi.pb162.hw03.impl.Character;

/**
 * Interface defining the ability to write character information into file
 * @author Jakub Cechacek
 */
public interface CharacterFileWriter {
    /**
     * Writes character information into file
     * @param character character to be written
     * @param path file path
     * @param charset file encoding to be used
     * @throws IOException in case of IO error
     */
    void write(Character character, Path path, Charset charset) throws IOException;
}
