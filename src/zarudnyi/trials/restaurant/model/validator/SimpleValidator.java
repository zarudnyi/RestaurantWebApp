package zarudnyi.trials.restaurant.model.validator;

import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class SimpleValidator implements Validator {
    private Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
    private Pattern pricePattern = Pattern.compile("[^0-9 ]", Pattern.CASE_INSENSITIVE);

    protected boolean checkStringHasSpecialChar(String s){
        Matcher m = p.matcher(s);
        return m.find();
    }

    protected boolean checkCurrency(String s){
        Matcher m  = pricePattern.matcher(s);
        return m.find();
    }

    protected boolean checkString(String s){
        if (s==null)
            return false;
        return !checkStringHasSpecialChar(s);
    }

    protected boolean isEmptyOrNull(String s){
        return s==null||s.isEmpty();
    }


}
