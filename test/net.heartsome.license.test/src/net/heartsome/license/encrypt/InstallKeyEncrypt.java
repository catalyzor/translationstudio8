package net.heartsome.license.encrypt;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class InstallKeyEncrypt {
	private static final String algorithm = "RSA";
	
	private final static byte[] publicKey = new byte[] {48,-127,-97,48,13,6,9,42,-122,72,-122,-9,13,1,1,1,5,0,3,
			-127,-115,0,48,-127,-119,2,-127,-127,0,-126,84,87,97,-103,60,114,-92,-21,23,111,34,-45,61,8,
			-39,-18,-95,96,-99,-4,-15,-51,52,-82,91,-108,-102,87,-37,-8,34,23,-16,98,-100,38,-7,82,-13,
			67,47,96,-102,-128,-48,21,-106,-94,23,119,-77,-46,72,-89,-44,64,-11,81,127,26,-38,-59,26,102,
			-58,-89,104,-117,88,87,-93,-9,110,-21,-102,-24,-72,-23,-41,45,7,-35,-72,-105,-98,-27,-114,-74,
			-49,-35,-93,-85,66,111,-72,-4,58,-116,-93,-102,27,-49,-25,32,75,4,-2,-95,112,-60,-103,32,43,31,
			60,18,-89,24,-85,-63,-95,73,-115,13,22,-102,-25,2,3,1,0,1};
	
	private final static byte[] privateKey = new byte[] {48,-126,2,118,2,1,0,48,13,6,9,42,-122,72,-122,-9,13,1,1,
			1,5,0,4,-126,2,96,48,-126,2,92,2,1,0,2,-127,-127,0,-126,84,87,97,-103,60,114,-92,-21,23,111,
			34,-45,61,8,-39,-18,-95,96,-99,-4,-15,-51,52,-82,91,-108,-102,87,-37,-8,34,23,-16,98,-100,38,
			-7,82,-13,67,47,96,-102,-128,-48,21,-106,-94,23,119,-77,-46,72,-89,-44,64,-11,81,127,26,-38,
			-59,26,102,-58,-89,104,-117,88,87,-93,-9,110,-21,-102,-24,-72,-23,-41,45,7,-35,-72,-105,-98,
			-27,-114,-74,-49,-35,-93,-85,66,111,-72,-4,58,-116,-93,-102,27,-49,-25,32,75,4,-2,-95,112,
			-60,-103,32,43,31,60,18,-89,24,-85,-63,-95,73,-115,13,22,-102,-25,2,3,1,0,1,2,-127,-128,78,
			45,-21,-48,-25,12,4,-75,123,52,-82,125,20,124,11,96,55,-82,100,119,-63,49,7,104,-81,-94,-50,
			12,-101,-31,-97,2,76,-101,-19,123,64,65,-43,-119,-119,102,-43,43,57,84,25,111,39,-62,-82,18,
			-105,116,-17,-1,-6,11,110,-100,-79,110,68,71,66,-54,27,-119,-66,25,-60,-57,27,-36,44,48,95,
			-49,-34,97,85,22,116,88,-109,-73,-55,-106,56,-6,-45,16,-85,72,-84,-120,126,-41,-26,10,58,61,
			-83,-21,-86,51,44,-51,-65,-82,-51,-69,124,105,-86,11,-69,83,-82,-70,78,116,13,-13,-82,111,
			-121,33,2,65,0,-13,-81,-101,117,47,-74,84,88,-98,49,-117,99,-75,-82,-122,-19,-86,-11,-118,
			-19,103,-118,-94,-39,-9,87,18,-39,12,7,126,9,-16,-67,-104,77,-80,-91,112,-125,-88,-59,32,
			120,-96,-120,-18,-123,36,-25,-105,-99,12,96,91,90,0,0,-108,67,95,-18,-15,-79,2,65,0,-120,
			-22,82,59,-32,75,-106,-88,120,99,-5,-10,87,104,89,-38,85,27,-45,-17,-77,4,81,78,55,30,-65,
			2,35,-63,-54,-33,65,-113,83,57,-82,105,-29,107,-98,114,1,103,106,12,120,26,-110,14,-72,-26,
			116,-4,-22,24,72,27,-94,-44,21,66,36,23,2,64,14,-47,65,-63,-87,106,11,-52,-21,-87,-107,-111,
			53,-115,-20,52,109,109,-54,-55,-50,-66,-10,82,12,-95,37,-53,62,-39,1,-57,-10,-12,-128,91,80,
			-1,110,77,3,41,33,101,84,85,-97,51,71,96,49,-82,-31,-2,-59,-17,62,10,-54,-7,33,-49,52,-79,2,
			65,0,-120,88,80,-20,47,-63,22,-105,78,-52,-62,-19,-34,-67,-100,61,64,-28,37,122,94,-37,-67,
			-36,22,40,24,47,25,76,-77,-85,0,-82,-86,66,-106,-35,-4,12,0,13,-88,56,67,-13,-122,-25,110,
			-20,26,85,-27,-54,-73,-108,-42,-70,46,-68,-27,46,-88,-95,2,64,72,69,76,-106,-91,82,-34,-14,
			-15,-115,119,-51,-7,-12,46,34,79,44,-89,32,123,46,-100,-5,-35,-72,96,-33,-2,93,31,-94,-3,15,
			-56,-109,-8,112,-116,67,68,-125,63,73,-78,-93,65,-110,29,5,-23,-15,80,9,-52,-125,-67,-81,-40,
			76,70,104,51,-59};
	
	public static byte[] encrypt(byte[] srcBytes) throws Exception {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
		KeyFactory kf = KeyFactory.getInstance(algorithm);
		PublicKey keyPublic = kf.generatePublic(keySpec);

		Cipher cipher;
		cipher = Cipher.getInstance(algorithm,
				new org.bouncycastle.jce.provider.BouncyCastleProvider());

		cipher.init(Cipher.ENCRYPT_MODE, keyPublic);
		int blockSize = cipher.getBlockSize();
		int outputSize = cipher.getOutputSize(srcBytes.length);
		int leavedSize = srcBytes.length % blockSize;
		int blocksSize = leavedSize != 0 ? srcBytes.length / blockSize + 1
				: srcBytes.length / blockSize;
		byte[] raw = new byte[outputSize * blocksSize];
		int i = 0;
		while (srcBytes.length - i * blockSize > 0) {
			if (srcBytes.length - i * blockSize > blockSize)
				cipher.doFinal(srcBytes, i * blockSize, blockSize, raw, i
						* outputSize);
			else
				cipher.doFinal(srcBytes, i * blockSize, srcBytes.length - i
						* blockSize, raw, i * outputSize);
			i++;
		}
		return raw;
	}
	
	public static byte[] decrypt(byte[] srcBytes) throws Exception {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory kf = KeyFactory.getInstance(algorithm);
		PrivateKey keyPrivate = kf.generatePrivate(keySpec);

		Cipher cipher = Cipher.getInstance(algorithm,
				new org.bouncycastle.jce.provider.BouncyCastleProvider());
		cipher.init(Cipher.DECRYPT_MODE, keyPrivate);

		int blockSize = cipher.getBlockSize();
		ByteArrayOutputStream bout = new ByteArrayOutputStream(blockSize);
		int j = 0;
		while (srcBytes.length - j * blockSize > 0) {
			byte[] temp = cipher.doFinal(srcBytes, j * blockSize, blockSize);
			bout.write(temp);
			j++;
		}
		return bout.toByteArray();
	}
}
