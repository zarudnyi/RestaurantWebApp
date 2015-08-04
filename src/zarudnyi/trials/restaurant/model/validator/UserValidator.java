package zarudnyi.trials.restaurant.model.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import zarudnyi.trials.restaurant.model.entity.User;

@Service
public class UserValidator extends SimpleValidator {
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if(!checkString(user.getLogin()) || "anonymousUser".equals(user.getLogin())) {
            errors.rejectValue("login", "bad");
        }
        if(isEmptyOrNull(user.getLogin())) {
            errors.rejectValue("login", "empty");
        }
        if(checkStringHasSpecialChar(user.getFname())) {
            errors.rejectValue("fname", "bad");
        }

        if(isEmptyOrNull(user.getLname())) {
            errors.rejectValue("lname", "empty");
        }
        if(checkStringHasSpecialChar(user.getLname())) {
            errors.rejectValue("lname", "bad");
        }
        if(user.getPassword()==null || user.getPassword().isEmpty()) {
            errors.rejectValue("password", "empty");
        }

    }
}
