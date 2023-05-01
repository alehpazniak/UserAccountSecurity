package pl.aleh.UserAccountSecurity.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.aleh.UserAccountSecurity.models.Person;
import pl.aleh.UserAccountSecurity.repositories.PeopleRepository;

@RequiredArgsConstructor
@Service
public class RegistrationService {

  private final PeopleRepository peopleRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void register(Person person) {
    person.setPassword(passwordEncoder.encode(person.getPassword()));
    person.setRole("ROLE_USER");
    peopleRepository.save(person);
  }
}
