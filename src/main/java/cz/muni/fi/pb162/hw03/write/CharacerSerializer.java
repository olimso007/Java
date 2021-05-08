package cz.muni.fi.pb162.hw03.write;

import cz.muni.fi.pb162.hw03.impl.Character;

/**
 * Interface defining the ability to serialise character information into {@link T}
 * @author Jakub Cechacek
 * @param  <T> output type
 */
public interface CharacerSerializer<T> {
    /**
     * Serializes character into {@link T}
     * @param character character to be serialised
     * @return character representation as {@link T}
     */
    T serialize(Character character);
}
