package zarudnyi.trials.restaurant.model.validator;


import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import zarudnyi.trials.restaurant.model.entity.Group;

@Service
public class GroupValidator extends SimpleValidator {
    public boolean supports(Class<?> aClass) {
        return Group.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {

        Group group = (Group) o;
        if(!checkString(group.getName())) {
            errors.rejectValue("name", "bad");
        }
        if(isEmptyOrNull(group.getName())) {
            errors.rejectValue("name", "empty");
        }

    }
}
