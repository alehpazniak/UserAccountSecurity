package pl.aleh.UserAccountSecurity.controllers;

import java.util.Map;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.aleh.UserAccountSecurity.dto.AuthenticationDTO;
import pl.aleh.UserAccountSecurity.dto.PersonDTO;
import pl.aleh.UserAccountSecurity.models.Person;
import pl.aleh.UserAccountSecurity.security.JWTUtil;
import pl.aleh.UserAccountSecurity.services.RegistrationService;
import pl.aleh.UserAccountSecurity.validators.PersonValidator;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final PersonValidator personValidator;
  private final RegistrationService registrationService;
  private final JWTUtil jwtUtil;
  private final ModelMapper modelMapper;
  private final AuthenticationManager authenticationManager;

  @Autowired
  public AuthController(
      final PersonValidator personValidator, final RegistrationService registrationService, final JWTUtil jwtUtil,
      final ModelMapper modelMapper,
      final AuthenticationManager authenticationManager
  ) {
    this.personValidator = personValidator;
    this.registrationService = registrationService;
    this.jwtUtil = jwtUtil;
    this.modelMapper = modelMapper;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/registration")
  public Map<String, String> performRegistration(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
    var person = mapToPerson(personDTO);
    personValidator.validate(person, bindingResult);
    if (bindingResult.hasErrors()) {
      return Map.of("message", "Error");
    }
    registrationService.register(person);
    var token = jwtUtil.generateToken(person.getUsername());
    return Map.of("jwt-token", token);
  }

  @PostMapping("/login")
  public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
    var authInputToken = new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(), authenticationDTO.getPassword());
    try {
      authenticationManager.authenticate(authInputToken);
    } catch (BadCredentialsException e) {
      return Map.of("message", "Incorrect credentials");
    }
    var token = jwtUtil.generateToken(authenticationDTO.getUsername());
    return Map.of("jwt-token", token);
  }

  public Person mapToPerson(PersonDTO personDTO) {
    return this.modelMapper.map(personDTO, Person.class);
  }

}
