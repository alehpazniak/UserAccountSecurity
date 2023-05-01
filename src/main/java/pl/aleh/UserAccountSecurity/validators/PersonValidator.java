package pl.aleh.UserAccountSecurity.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.aleh.UserAccountSecurity.models.Person;
import pl.aleh.UserAccountSecurity.services.PeopleService;

@RequiredArgsConstructor
@Component
public class PersonValidator implements Validator {

  private final PeopleService peopleService;


  @Override
  public boolean supports(final Class<?> clazz) {
    return Person.class.equals(clazz);
  }

  @Override
  public void validate(final Object object, final Errors errors) {
    Person person = (Person) object;
    if (peopleService.getPersonByUsername(person.getUsername()).isPresent()) {
      errors.rejectValue("username", "", "A person with that username already exists");
    }
  }
}
