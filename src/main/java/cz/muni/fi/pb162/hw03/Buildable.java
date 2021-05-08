package cz.muni.fi.pb162.hw03;

/**
 * A base interface for builder implementations
 * @author Jakub Cechacek
 * @param <T> type of built object
 */
public interface Buildable<T> {
    /**
     * Build an instance of {@link T}
     * @return instance of {@link T}
     */
    T build();
}
