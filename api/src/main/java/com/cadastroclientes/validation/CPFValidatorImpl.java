package com.cadastroclientes.validation;

import br.com.caelum.stella.validation.CPFValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidatorImpl implements ConstraintValidator<CPF, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null) {
            return false;
        }
        CPFValidator validator = new CPFValidator();
        return validator.isEligible(cpf);
    }
}
