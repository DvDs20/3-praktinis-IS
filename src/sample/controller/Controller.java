package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.backend.RSAEncryptionAndDecryption;
import sample.backend.RSAKeyPairGenerator;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    @FXML
    private TextField keyTextArea;

    @FXML
    private TextArea firstTextArea;

    @FXML
    private TextArea secondTextArea;

    @FXML
    private Button encryptButton;

    @FXML
    private Button decryptButton;

    private String privateKey;


    public void encryptButtonPressed(ActionEvent actionEvent) throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException, IOException {
        RSAKeyPairGenerator rsaKeyPairGenerator = new RSAKeyPairGenerator();
        String publicKey = Base64.getEncoder().encodeToString(rsaKeyPairGenerator.getPublicKey().getEncoded());
        privateKey = Base64.getEncoder().encodeToString(rsaKeyPairGenerator.getPrivateKey().getEncoded());
        String encryptedString = Base64.getEncoder().encodeToString(RSAEncryptionAndDecryption.encrypt(firstTextArea.getText(), publicKey));
        secondTextArea.setText(encryptedString);
        FileWriter fileWriter = null;
        try {
            File file = new File("C:/Users/deivi/Documents/Informacijos saugumas/3 praktinis darbas/src/sample/encrypted.txt");
            fileWriter = new FileWriter(file);
            fileWriter.write("Encrypted text: " + encryptedString + "\n");
            fileWriter.write("Privatus raktas: " + privateKey);
            fileWriter.close();
        }
        catch (IOException ioException) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ioException);
        }
        finally {
            try {
                fileWriter.close();
            }
            catch (IOException ioException) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ioException);
            }
        }
    }


    public void decryptButtonPressed(ActionEvent actionEvent) throws InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
            String decryptedString = RSAEncryptionAndDecryption.decrypt(firstTextArea.getText(), keyTextArea.getText());
            secondTextArea.clear();
            secondTextArea.setText(decryptedString);
            FileWriter fileWriter = null;
            try {
                File file = new File("C:/Users/deivi/Documents/Informacijos saugumas/3 praktinis darbas/src/sample/decrypted.txt");
                fileWriter = new FileWriter(file);
                fileWriter.write("Decrypted text: " + decryptedString);
                fileWriter.close();
            }
            catch (IOException ioException) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ioException);
            }
            finally {
                try {
                    fileWriter.close();
                }
                catch (IOException ioException) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ioException);
                }
            }
    }
}
