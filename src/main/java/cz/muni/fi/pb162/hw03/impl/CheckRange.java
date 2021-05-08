package cz.muni.fi.pb162.hw03.impl;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * Class for check is argument in range 1-10
 * @author Alisia Lyzhina
 */
public class CheckRange implements IParameterValidator {
    @Override
    public void validate(String name, String val) {
        try {
            int n = Integer.parseInt(val);
            if (n > 10 || n < 1) {
                throw new ParameterException("Parameter " + name + " should be an integer from 1 to 10");
            }
        } catch (NumberFormatException e) {
            throw new ParameterException("Parameter " + name + " should be an integer from 1 to 10");
        }

    }
}
