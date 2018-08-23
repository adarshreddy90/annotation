package com.home.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import com.home.encryption.EncryptionUtility;

public class BaseModel {

	@Override
	public final String toString() {

		StringBuilder toString = new StringBuilder();

//		toString.append(this.getClass().getName()).append("[");

		Field[] fields = this.getClass().getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			
			Field field = fields[i];
			
			try {
				String name = field.getName();
				Object value;

				if (!Modifier.isPublic(field.getModifiers())) {
					field.setAccessible(true);
				}
				
				value = field.isAnnotationPresent(com.home.annotation.Sensitive.class) 
						? getMaskedValue(field.get(this)) : field.get(this);
						
					/*	KeyGenerator keyGen;
						try {
							keyGen = KeyGenerator.getInstance("AES");
							keyGen.init(128);
							SecretKey secKey = keyGen.generateKey();
							
							Cipher aesCipher = Cipher.getInstance("AES");
						} catch (NoSuchAlgorithmException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoSuchPaddingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
*/
			
				toString.append(name).append("=").append(value).append(", ");
			}
			catch (IllegalArgumentException | IllegalAccessException e) {
			}
		}
		toString = new StringBuilder(toString.toString().replaceAll(",\\s*$", ""));

//		toString.append("]");

		return toString.toString();
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	private String getMaskedValue(Object input) {

		final String secretKey = "ssshhhhhhhhhhh!!!!";
		
//		char[] value = input.toString().toCharArray();
//
//		StringBuilder output = new StringBuilder();
		
		String encryptedString = EncryptionUtility.encrypt(input.toString(), secretKey) ;

//		for (int i = 0; i < value.length; i++) {
//
//			output.append("+");
//		}

//		return output.toString();
		return encryptedString;
	}
}
