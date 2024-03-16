package com.techpheonix.shashwat.model;

import com.techpheonix.shashwat.service.GetConnection;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class UserDAO {

    private static SecretKeySpec secretKey;
    private static byte[] key;

    //-----------Registration or insertdata--------------
    public boolean insert(UserDTO udto) {
        Connection con = GetConnection.getConnection();
        String query = "insert into usersinfo (fullname,username, email,mobile,gender, password,dob) values(?,?,?,?,?,?,?)";
        boolean result = false;
        try {
            String encryptPassword = encrypt(udto.getPassword());

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, udto.getFullname());
            ps.setString(2, udto.getUsername());
            ps.setString(3, udto.getEmail());
            ps.setString(4, udto.getMobile());
            ps.setString(5, udto.getGender());
            ps.setString(6, encryptPassword);
            ps.setString(7, udto.getDob());

            if (ps.executeUpdate() > 0) {

                result = true;
            }

        } catch (SQLException e) {

            System.out.println("some Exception");
            System.out.println(e);
            result = false;
        }

        return result;
    }

    //    -------login----
    public UserDTO login(UserDTO udto) {
        Connection con = GetConnection.getConnection();
        String query = "SELECT * FROM usersinfo WHERE userName = ?";
        ResultSet rs;
        try {

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, udto.getUsername());

            rs = ps.executeQuery();
            if (rs.next()) {

                String encryptedPasswordFromDB = rs.getString("password"); // Get the encrypted password from the database
                String decryptedPassword = decrypt(encryptedPasswordFromDB); // Decrypt the password from the database
                if (decryptedPassword.equals(udto.getPassword())) { // Compare the decrypted password with the input password
                    udto.setId(rs.getInt("id"));
                    udto.setFullname(rs.getString("fullname"));
                    udto.setUsername(rs.getString("userName"));
                    udto.setEmail(rs.getString("email"));
                    udto.setMobile(rs.getString("mobile"));
                    udto.setGender(rs.getString("gender"));
                    udto.setPassword(decryptedPassword);
                    udto.setBlock(rs.getBoolean("block"));
                    udto.setDob(rs.getString("dob"));

                }

            }

        } catch (SQLException ex) {

            System.out.println("some Exception");

            System.out.println("" + ex);
        } finally {

            try {
                con.close();
            } catch (SQLException ex) {

            }
        }

        return udto;
    }

    //    ------------------password encription--------------------
    public static void setKey(String myKey) {
        try {
            key = myKey.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // use only first 128 bit
            secretKey = new SecretKeySpec(key, "AES");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
        }
    }

    public static String encrypt(String strToEncrypt) {
        try {
            setKey("encryptionKey"); // You can modify the encryption key here
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        try {
            setKey("encryptionKey"); // You can modify the encryption key here
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
//            System.out.println("Input length: " + strToDecrypt.length()); // Log the length of the input string
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            System.out.println("Error while decrypting: " + e.toString());
        }

        return null;
    }

//    ===========UPDATE PROFILE======================
    public UserDTO updateProfile(UserDTO udto) {
        Connection con = GetConnection.getConnection();
        String query = "UPDATE usersinfo SET fullname = ?, username = ?, email = ?, mobile = ?,gender = ?, dob = ? WHERE id = ?";
        boolean result = false;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, udto.getFullname());
            ps.setString(2, udto.getUsername());
            ps.setString(3, udto.getEmail());
            ps.setString(4, udto.getMobile());
            ps.setString(5, udto.getGender());
            ps.setString(6, udto.getDob());
            ps.setInt(7, udto.getId());

            if (ps.executeUpdate() > 0) {

                result = true;
            }

        } catch (SQLException e) {

            System.out.println("some Exception");
            System.out.println(e);
            result = false;
        }

        return udto;
    }

    
    
    
}
