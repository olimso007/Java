package cz.muni.fi.pb162.hw03.impl;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * Class for check is argument a positive multiplier of 3
 * @author Alisia Lyzhina
 */
public class DivByThree implements IParameterValidator {
    @Override
    public void validate(String name, String val) {
        try {
            int n = Integer.parseInt(val);
            if (n % 3 != 0 || n < 3) {
                throw new ParameterException("Parameter " + name + " should be a positive multiplier of 3");
            }
        } catch (NumberFormatException e) {
            throw new ParameterException("Parameter " + name + " should be a positive multiplier of 3");
        }

    }
}
