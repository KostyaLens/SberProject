package org.example.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements
        ConstraintValidator<UniqueEmail, String> {
    @Autowired
    public final UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UniqueEmail uniqueEmail) {
    }


    @Override
    public boolean isValid(String contactField,
                           ConstraintValidatorContext cxt) {
        return (userService.findByEmail(contactField) != null && contactField != null);
    }

}