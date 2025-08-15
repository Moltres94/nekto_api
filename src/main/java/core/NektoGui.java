package core;

import core.utils.LOG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

public class NektoGui extends JFrame implements ActionListener {
    private NektoAPI api;
    private boolean inChat = false;
    private long dialogId = 0;
    private int state=0;
    private JTextArea jta = new JTextArea(
            "");
    private JLabel statuslabel = new JLabel("Status: ");
    private JLabel userIDLabel = new JLabel("UserID: ");
    private JLabel userCountLabel = new JLabel("InChat: ");
    private JLabel errorLabel = new JLabel("");
    private JButton searchButton = new JButton("Start search");

    public NektoGui() {
        Container c = getContentPane(); // клиентская область окна
        c.setLayout(new BorderLayout()); // выбираем компоновщик
        JPanel jpTop = new JPanel();
        jpTop.setLayout(new GridLayout(3, 2));
        jpTop.add(statuslabel);
        jpTop.add(userIDLabel);
        jpTop.add(userCountLabel);
        jpTop.add(errorLabel);
        jpTop.add(errorLabel);


        c.add(jpTop, BorderLayout.NORTH);

        JPanel jpBottom = new JPanel();
        //jpBottom.setLayout(new GridLayout(1, 2));
        JTextField messageField = new JTextField(15);
        messageField.addActionListener(e -> {
            if (state==4)
                api.send(new NektoRequest.SendMessage(messageField.getText(),dialogId));
            messageField.setText("");
        });
        jpBottom.add(messageField);
        //JButton jbt = new JButton("Send");
        //jbt.addActionListener(this); // назначаем обработчик события
        //jpBottom.add(jbt);

        searchButton.addActionListener(this); // назначаем обработчик события
        jpBottom.add(searchButton);
        searchButton.setEnabled(false);

        //jbt = new JButton("Clear text");
        //jbt.addActionListener(this); // назначаем обработчик события
        //jpBottom.add(jbt);
        c.add(jpBottom, BorderLayout.SOUTH);
        c.add(new JScrollPane(jta));
        jta.setLineWrap(true);
        // -------------------------------------------
        // настройка окна
        setTitle("NektoMe"); // заголовок окна
        // желательные размеры окна
        setPreferredSize(new Dimension(640, 480));
        // завершить приложение при закрытии окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack(); // устанавливаем желательные размеры
        setVisible(true); // отображаем окно
    }

    public void setApi(NektoAPI api) {
        this.api = api;
    }

    public void addText(String str) {
        jta.append(str + System.lineSeparator());
    }

    public void setText(String str) {
        jta.setText(str + "\\n");
    }

    public void setStatus(int state) {
        this.state=state;
        switch (state) {
            case 1:
                statuslabel.setText("Status: Connected");
                searchButton.setText("Start search");
                searchButton.setEnabled(true);
                inChat = false;
                break;

            case 2:
                statuslabel.setText("Status: Captcha required");
                break;
            case 3:
                statuslabel.setText("Status: Search in progress");
                searchButton.setEnabled(true);
                searchButton.setText("Cancel search");
                break;
            case 4:
                statuslabel.setText("Status: inChat");
                searchButton.setText("Exit chat");
                userCountLabel.setText("");
                inChat = true;
                break;
            default:
                statuslabel.setText("Status: Unknown");
        }

    }

    public void setUserID(String str) {
        userIDLabel.setText(str);
    }

    public void setInterlocutor(long id) {
        errorLabel.setText("Opponent: "+id);
    }
    public void hideInterlocutor() {
        errorLabel.setText("");
    }

    public void setUserCount(String str) {
        userCountLabel.setText(str);
    }

    public void setDialogId(long id){
        this.dialogId=id;
    }
    // обработчик события, метод интерфейса ActionListener
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getActionCommand().equals("Start search")){
            api.send(new NektoRequest.OnlineTrack(false));
            api.send(new NektoRequest.SearchRun(new NektoSearchFilter(
                    false,
                    false,
                    true,
                    22,
                    25,
                    "M",
                    22,
                    25,
                    "F"
            )));
            searchButton.setEnabled(false);
        }
        if (arg0.getActionCommand().equals("Clear text"))
            jta.setText("");
        if (arg0.getActionCommand().equals("Send"))
            jta.setText("");
        if (arg0.getActionCommand().equals("Cancel search")) {
            api.send(new NektoRequest.SearchStop());
            api.send(new NektoRequest.OnlineTrack(true));
            searchButton.setEnabled(false);
        }
        if (arg0.getActionCommand().equals("Exit chat")) {
            api.send(new NektoRequest.ExitChat(dialogId));

            searchButton.setEnabled(false);
        }

        // если ссылки на объекты сохранены можно сравнивать
        // по объектам, например для JButton jbOK= new JBUtton("Ok");
        // то сравнение будет таким
        //    if(arg0.getSource().equals(jbOK))
    }
}
