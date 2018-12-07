package com.lsm.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.HibernateValidator;

import com.lsm.dto.CreatePrivilegeInput;
import com.lsm.dto.RestRespBody;

public class ValidateUtils {
 
	/**
	 * 验证bean字段的限制
	 * @param object
	 * @return
	 */
	public static RestRespBody validatePOJO(Object object) {
		Validator validator = Validation.byProvider(HibernateValidator.class).configure().buildValidatorFactory()
				.getValidator();
		Set<ConstraintViolation<Object>> validatorSet = validator.validate(object);
		if (!validatorSet.isEmpty()) {
			String message = validatorSet.iterator().next().getMessage();
			return new RestRespBody(false, message);
		}
		return null;
	}
}
