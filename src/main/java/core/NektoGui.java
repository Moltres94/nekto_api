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
    private JTextArea jta = new JTextArea(
            "Scroll bar will appear, when much text");
    private JLabel statuslabel =new JLabel("Status: ");
    private JLabel userIDLabel =new JLabel("UserID: ");
    private JLabel userCountLabel =new JLabel("InChat: ");
    private JLabel errorLabel =new JLabel("");
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


        c.add(jpTop,BorderLayout.NORTH);

        JPanel jp = new JPanel();
        searchButton.addActionListener(this); // назначаем обработчик события
        jp.add(searchButton);
        searchButton.setEnabled(false);
        JButton jbt = new JButton("Clear text");
        jbt.addActionListener(this); // назначаем обработчик события
        jp.add(jbt);
        jbt = new JButton("Send");
        jbt.addActionListener(this); // назначаем обработчик события
        jp.add(jbt);
        c.add(jp, BorderLayout.SOUTH);
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
    public void setApi(NektoAPI api)
    {
        this.api=api;
    }
    public void addText(String str)
    {
        jta.append(str+"\\n");
    }
    public void setText(String str)
    {
        jta.setText(str+"\\n");
    }
    public void setStatus(int state)
    {
        switch (state){
            case 1:
                statuslabel.setText("Status: Connected");
                searchButton.setText("Start search");
                searchButton.setEnabled(true);
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
                break;
            default:
                statuslabel.setText("Status: Unknown");
        }

    }
    public void setUserID(String str)
    {
        userIDLabel.setText(str);
    }
    public void setUserCount(String str)
    {
        userCountLabel.setText(str);
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

        // если ссылки на объекты сохранены можно сравнивать
        // по объектам, например для JButton jbOK= new JBUtton("Ok");
        // то сравнение будет таким
        //    if(arg0.getSource().equals(jbOK))
    }
}
