package cz.muni.fi.pb162.hw03.impl;

import cz.muni.fi.pb162.hw03.write.CharacerSerializer;
import cz.muni.fi.pb162.hw03.write.CharacterFileWriter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Base class capable of serialising character into HTML string
 * @author Jakub Cechacek and Alisia Lyzhina
 */
public class AbstractHtmlWriter implements CharacerSerializer<String>, CharacterFileWriter {
    public static final String TEMPLATE = "/templates/character.html";

    @Override
    public String serialize(Character character) {
        ClassLoaderTemplateResolver tr = new ClassLoaderTemplateResolver();
        TemplateEngine templates = new TemplateEngine();
        templates.setTemplateResolver(tr);
        Context context = new Context();
        context.setVariable("character", character);
        return templates.process(TEMPLATE, context);
    }

    @Override
    public void write(Character character, Path path, Charset charset) throws IOException {
        String info = serialize(character);
        if (!path.toString().endsWith(".html")) {
            path = Paths.get(path.toString() + ".html");
        }
        Path result = Files.write(path, info.getBytes(charset));
        System.out.println("Written content in file:\n"+ result.getFileName());
    }
}
