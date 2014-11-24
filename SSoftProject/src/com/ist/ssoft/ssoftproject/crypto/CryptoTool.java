package com.ist.ssoft.ssoftproject.crypto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

public class CryptoTool {
	private static final String cryptoSpec = "AES/CBC/PKCS5Padding";
	private static final String defaultB64KeyString = "rB5eoceHKk0yrM8HXbcXSSezFauGvK5Fp8k4PlLIjHQ=";
	
	public CryptoTool() {
		
	}
	
	public byte[] encrypt(byte[] dataToEncrypt) {
		SecretKeySpec keySpec = null;
		byte[] output = null;
		try {
			byte[] keyBytes = Base64.decode(defaultB64KeyString.getBytes("utf-8"),Base64.DEFAULT);
			keySpec = new SecretKeySpec(keyBytes, "AES");
			Cipher cipher = Cipher.getInstance(cryptoSpec);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			output = cipher.doFinal(dataToEncrypt);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
	}
	
	
	public byte[] decrypt(byte[] dataToDecrypt) {
		SecretKeySpec keySpec = null;
		byte[] output = null;
		
		try {
			byte[] keyBytes = Base64.decode(defaultB64KeyString.getBytes("utf-8"),Base64.DEFAULT);
			keySpec = new SecretKeySpec(keyBytes, "AES");
			Cipher cipher = Cipher.getInstance(cryptoSpec);
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			output = cipher.doFinal(dataToDecrypt);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return output;
	}

}
