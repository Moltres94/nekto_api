package core;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class NektoCaptchaFrame extends JFrame {
    private NektoAPI api;
    private JLabel captchaLabel=new JLabel("Captcha:");
    private JTextField captchaField = new JTextField(15);
    public NektoCaptchaFrame(String imageUrl, NektoAPI api) {
        super("Display Image from URL");
        this.api=api;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);

        JPanel jpTop = new JPanel();
        jpTop.setLayout(new GridLayout(1, 2));
        jpTop.add(captchaLabel);
        jpTop.add(captchaField);
        add(jpTop,BorderLayout.NORTH);

        captchaField.addActionListener(e -> {
            api.send(new NektoRequest.SendCaptcha(captchaField.getText()));
            dispose();
        });

        try {
            URL url = new URL(imageUrl);
            ImageIcon imageIcon = new ImageIcon(url);
            JLabel imageLabel = new JLabel(imageIcon);
            add(imageLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
            JLabel errorLabel = new JLabel("Error loading image");
            add(errorLabel, BorderLayout.CENTER);
        }

        setVisible(true);
    }
}