package cz.muni.fi.pb162.hw03.read;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;

/**
 * Attribute     reader interface
 * @author Jakub Cechacek
 */
public interface AttributeReader {
    /**
     * Loads character's attributes from file
     * @param path file path
     * @param charset file charset/encoding
     * @return an instance of itself
     * @throws IOException in case of IO error
     */
    AttributeReader readAttributes(Path path, Charset charset) throws IOException;
}