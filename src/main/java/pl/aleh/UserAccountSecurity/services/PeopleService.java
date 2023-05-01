package pl.aleh.UserAccountSecurity.services;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.aleh.UserAccountSecurity.models.Person;
import pl.aleh.UserAccountSecurity.repositories.PeopleRepository;

@RequiredArgsConstructor
@Service
public class PeopleService {

  private final PeopleRepository peopleRepository;

  public Optional<Person> getPersonByUsername(String username) {
    return peopleRepository.findByUsername(username);
  }

}
