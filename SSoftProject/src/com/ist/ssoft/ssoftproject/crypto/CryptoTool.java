package com.ist.ssoft.ssoftproject.crypto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class CryptoTool {
	private static final String defaultB64KeyString = "3qQJoumR";
	
	public CryptoTool() {
		
	}
	public void encrypt(String pathTofile, String  pathToEncryptedFile){
		FileInputStream is;
		try {
			is = new FileInputStream(pathTofile);
			FileOutputStream os;
			os = new FileOutputStream(pathToEncryptedFile);
			encryptOrDecrypt(defaultB64KeyString, Cipher.ENCRYPT_MODE, is, os);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void decrypt(String pathToEncryptedFile, String pathTofile){

		try {
			FileInputStream is = new FileInputStream(pathToEncryptedFile);
			FileOutputStream os = new FileOutputStream(pathTofile);
			encryptOrDecrypt(defaultB64KeyString, Cipher.DECRYPT_MODE, is, os);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    // This stream write the encrypted text. This stream will be wrapped by another stream.
	}

	public void encryptOrDecrypt(String key, int mode, InputStream is, OutputStream os) {
		try{
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			SecretKey desKey = skf.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for SunJCE
	
			if (mode == Cipher.ENCRYPT_MODE) {
				cipher.init(Cipher.ENCRYPT_MODE, desKey);
				CipherInputStream cis = new CipherInputStream(is, cipher);
				doCopy(cis, os);
			} else if (mode == Cipher.DECRYPT_MODE) {
				cipher.init(Cipher.DECRYPT_MODE, desKey);
				CipherOutputStream cos = new CipherOutputStream(os, cipher);
				doCopy(is, cos);
			}
		}catch(InvalidKeyException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void doCopy(InputStream is, OutputStream os) throws IOException {
		byte[] bytes = new byte[64];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {
			os.write(bytes, 0, numBytes);
		}
		os.flush();
		os.close();
		is.close();
	}

}