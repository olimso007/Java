package cz.muni.fi.pb162.hw03.impl;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * Class for check is argument positive integer
 * @author Alisia Lyzhina
 */
public class PositiveInteger implements IParameterValidator {
    @Override
    public void validate(String name, String val) {
        try {
            int n = Integer.parseInt(val);
            if (n <= 0) {
                throw new ParameterException("Parameter " + name + " should be a positive integer");
            }
        } catch (NumberFormatException e) {
            throw new ParameterException("Parameter " + name + " should be a positive integer");
        }

    }
}
