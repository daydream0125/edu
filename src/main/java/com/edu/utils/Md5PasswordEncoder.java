package com.edu.utils;

/**
 * 
 */

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author koala
 * 
 */
@Service
public class Md5PasswordEncoder implements PasswordEncoder {

	/**
	 * 获取加密后的字符�?
	 * 
	 * @param input
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public  String encode(CharSequence password) {
		// 4F9039413CDA541572B7941C46A5EC97
		StringBuffer passwordappend = new StringBuffer().append(password)
				.append("E01420D39C1342C69359DAF81F7A0EBC");
		byte[] bytes;
		try {
			bytes = passwordappend.toString().getBytes("utf-8");
			return toMD5Code(bytes);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		if (encode(rawPassword).equals(encodedPassword))
			return true;
		else
			return false;
	}

	public String toMD5Code(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.reset();
			md5.update(bytes);
			byte[] after = md5.digest();

			for (int i = 0; i < after.length; i++) {
				String hex = Integer.toHexString(0xff & after[i]).toUpperCase();
				if (hex.length() == 1)
					hex = "0" + hex;
				sb.append(hex);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sb.toString();
	}

	public byte[] toUnicodeMC(String s) {
		byte[] bytes = new byte[s.length() * 2];
		for (int i = 0; i < s.length(); i++) {
			int code = s.charAt(i) & 0xffff;
			bytes[i * 2] = (byte) (code & 0x00ff);
			bytes[i * 2 + 1] = (byte) (code >> 8);
		}
		return bytes;
	}
}
