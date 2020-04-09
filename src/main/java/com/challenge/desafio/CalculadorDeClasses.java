package com.challenge.desafio;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import com.challenge.annotation.Somar;
import com.challenge.annotation.Subtrair;
import com.challenge.interfaces.Calculavel;


public class CalculadorDeClasses implements Calculavel {

	@Override
	public BigDecimal somar(Object obj) {
		return valor(obj, Somar.class);
	}

	@Override
	public BigDecimal subtrair(Object obj) {
		return valor(obj, Subtrair.class);
	}
	
	private BigDecimal valor(Object obj, Class annotation) {
		if (obj.equals(null)) throw new NullPointerException("Null Object.");
		BigDecimal sumValue = BigDecimal.ZERO;
		try {
			Class clazz = obj.getClass();
			
			for (Field field : clazz.getDeclaredFields()) {
				if((field.isAnnotationPresent(annotation)) && (field.getType().equals(BigDecimal.class))) {
					field.setAccessible(true);
					sumValue = sumValue.add((BigDecimal) field.get(obj));
				}
			}
			
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		}
		
		
		return sumValue;
		
	}
	
	@Override
	public BigDecimal totalizar(Object obj) {
//		if (obj.getClass().getDeclaredAnnotations().length == 0) {
//			return BigDecimal.ZERO;
//		}
		BigDecimal valor = somar(obj).subtract(subtrair(obj));
		return valor;
	}



}
