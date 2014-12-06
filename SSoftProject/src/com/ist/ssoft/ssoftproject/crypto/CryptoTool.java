package com.ist.ssoft.ssoftproject.crypto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

public class CryptoTool {
	private static final String cryptoSpec = "AES";
	private static final String defaultB64KeyString = "rB5eoceHKk0yrM8HXbcXSSezFauGvK5Fp8k4PlLIjHQ=";
	
	public CryptoTool() {
		
	}
	
	public void encrypt(String pathTofile, String pathToEncryptedFile) {
		SecretKeySpec keySpec = null;
		try {
			// Here you read the file.
		    FileInputStream fis = new FileInputStream(pathTofile);
		    // This stream write the encrypted text. This stream will be wrapped by another stream.
		    FileOutputStream fos = new FileOutputStream(pathToEncryptedFile);
		    
			byte[] keyBytes = Base64.decode(defaultB64KeyString.getBytes("utf-8"),Base64.DEFAULT);
			keySpec = new SecretKeySpec(keyBytes, "AES");
			Cipher cipher = Cipher.getInstance(cryptoSpec);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			CipherOutputStream cos = new CipherOutputStream(fos, cipher);
			
			// Write bytes
		    int b;
		    byte[] d = new byte[8];
		    while((b = fis.read(d)) != -1) {
		        cos.write(d, 0, b);
		    }
		    // Flush and close streams.
		    cos.flush();
		    cos.close();
		    fis.close();
			
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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void decrypt(String pathToEncryptedFile, String pathTofile) {
		SecretKeySpec keySpec = null;
		try {
			
			// Here you read the file.
		    FileInputStream fis = new FileInputStream(pathToEncryptedFile);
		    // This stream write the encrypted text. This stream will be wrapped by another stream.
		    FileOutputStream fos = new FileOutputStream(pathTofile);
		    
			byte[] keyBytes = Base64.decode(defaultB64KeyString.getBytes("utf-8"),Base64.DEFAULT);
			keySpec = new SecretKeySpec(keyBytes, "AES");
			Cipher cipher = Cipher.getInstance(cryptoSpec);
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			CipherInputStream cis = new CipherInputStream(fis, cipher);
			
			// Write bytes
			int b;
		    byte[] d = new byte[8];
		    while((b = cis.read(d)) != -1) {
		        fos.write(d, 0, b);
		    }
		    fos.flush();
		    fos.close();
		    cis.close();
			
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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
