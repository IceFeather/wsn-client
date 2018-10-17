package fr.icefeather.wsn.client.application;

import org.apache.batik.util.gui.xmleditor.XMLEditorKit;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.*;
import java.util.List;

public class App implements NotificationListener {
    private JTextField TextUrl;
    private JButton ButtonStartStop;
    private JTextField TextTopic;
    private JList ListNotifications;
    private ListModel<String> ListModelMessagesRecus;
    private JPanel PanelApp;
    private JEditorPane EditorPaneNotification;
    private JScrollPane ScrollPaneTextPaneNotification;
    private JScrollPane ScrollPaneListNotifications;
    private JTextField TextPort;
    private JTextField TextFiltre;
    private JTable TableNotificationHeaders;
    private JSplitPane SplitPaneNotificationDetail;
    private JSplitPane SplitPaneNotificationPanel;

    private Abonnement abonnement;
    private Serveur serveur;
    private final List<Notification> listeNotifications;


    public App() {
        // DATA
        listeNotifications = new ArrayList<Notification>();

        // ELEMENTS
//        ButtonStartStop.requestFocusInWindow();

        final AppForm formulaire = new AppForm();

        final AppTextField urlTextField = new AppTextField(TextUrl, "http:// url broker wsn", null, true);
        formulaire.formFields.add(urlTextField);
        final AppTextField topicTextField = new AppTextField(TextTopic, "Topic", null, true);
        formulaire.formFields.add(topicTextField);
        final AppTextField filtreTextField = new AppTextField(TextFiltre, "Filtre", null, false);
        formulaire.formFields.add(filtreTextField);
        final AppTextField portTextField = new AppTextField(TextPort, "8011", "8011", true);
        formulaire.formFields.add(portTextField);

        // VIEWS

        ListModelMessagesRecus = new DefaultListModel();
        ListNotifications.setModel(ListModelMessagesRecus);

        EditorPaneNotification.setEditorKit(new XMLEditorKit());

        ScrollPaneTextPaneNotification.setBorder(null);
        ScrollPaneListNotifications.setBorder(null);

        DefaultTableModel TableNotificationHeadersModel = new DefaultTableModel();
        TableNotificationHeadersModel.setColumnIdentifiers(new String[] { "header", "value" });
        TableNotificationHeaders.setModel(TableNotificationHeadersModel);


        serveur = new Serveur();
        serveur.setNotificationListener(this);

        ButtonStartStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (ButtonStartStop.getText().equals("Start")) {
                    if(formulaire.valider()) {
                        try {
                            abonnement = new Abonnement(urlTextField.getText(), portTextField.getText(), topicTextField.getText(), filtreTextField.getText());
                            serveur.setPort(Integer.parseInt(TextPort.getText()));
                            serveur.start();
                            abonnement.abonnement();
                            ButtonStartStop.setText("Stop");
                            ButtonStartStop.setForeground(Color.RED);
                        } catch (Exception e1) {
                            JOptionPane d = new JOptionPane();
                            d.showMessageDialog( PanelApp.getParent(),
                                    e1.getMessage(),
                                    "Erreur lors du démarrage du client",
                                    JOptionPane.ERROR_MESSAGE);
                            try {
                                abonnement.desabonnement();
                            } catch (Exception e2) {
                                d = new JOptionPane();
                                d.showMessageDialog( PanelApp.getParent(),
                                        e2.getMessage(),
                                        "Erreur lors du désabonnement du client",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                } else {
                    try {
                        serveur.stop();
                        abonnement.desabonnement();
                        ButtonStartStop.setText("Start");
                        ButtonStartStop.setForeground(Color.GREEN);
                    } catch (Exception e1) {
                        JOptionPane d = new JOptionPane();
                        d.showMessageDialog( PanelApp.getParent(),
                                e1.getMessage(),
                                "Erreur lors du désabonnement du client",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        ListNotifications.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int selected = ListNotifications.getSelectedIndex();
                updateNotificationView(listeNotifications.get(selected));
            }
        });
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        JFrame frame = new JFrame("wsn-abonnement");
        JPanel panel = new App().PanelApp;
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override public void nouvelleNotification(Notification notification) {
        listeNotifications.add(notification);
        ((DefaultListModel) ListNotifications.getModel()).addElement(notification.getDate());
    }

    public void updateNotificationView(Notification notification) {
        EditorPaneNotification.setText(notification.message);

        DefaultTableModel tableModel = (DefaultTableModel) TableNotificationHeaders.getModel();
        tableModel.setRowCount(0);
        Iterator headersIter = notification.getHeaders().entrySet().iterator();
        while (headersIter.hasNext()) {
            Map.Entry entry = (Map.Entry) headersIter.next();
            tableModel.addRow(new Object[] { entry.getKey(), ((LinkedList)entry.getValue()).getFirst() });
        }
        ((DefaultTableModel) TableNotificationHeaders.getModel()).fireTableDataChanged();

    }


    class AppTextField implements AppFormField {

        public JTextField textField;
        public Boolean isPlaceholder = false;
        public String placeholder;
        public String defaultText;
        public Boolean notnull;

        public AppTextField(JTextField jTextField, String placeholder, String defaultText, Boolean notnull) {
            this.textField = jTextField;
            this.placeholder = placeholder;
            this.defaultText = defaultText;
            this.notnull = notnull;
            if (defaultText == null || defaultText.isEmpty()){
                showPlaceholder();
            } else {
                showDefaultText();
            }
            listeners();
        }

        public String getText(){
            if (!isPlaceholder){
                return textField.getText();
            }
            return null;
        }

        private void showDefaultText() {
            isPlaceholder = false;
            textField.setForeground(Color.BLACK);
            textField.setFont(new Font(TextUrl.getFont().getFamily(), Font.PLAIN, TextUrl.getFont().getSize()));
            textField.setText(defaultText);
        }

        private void showPlaceholder() {
            isPlaceholder = true;
            textField.setText(placeholder);
            textField.setForeground(Color.GRAY);
            textField.setFont(new Font(TextUrl.getFont().getFamily(), Font.ITALIC, TextUrl.getFont().getSize()));
        }

        private void listeners(){
            textField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if(isPlaceholder) {
                        showDefaultText();
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if(textField.getText().isEmpty()){
                       showPlaceholder();
                    }
                }
            });
        }

        @Override
        public Boolean valider() {
            if (notnull) {
                if (getText() == null || getText().isEmpty()){
                    textField.setForeground(Color.RED);
                    return false;
                }
            }
            return true;
        }

    }


    interface AppFormField {
        Boolean valider();
    }


    class AppForm {

        public List<AppFormField> formFields = new ArrayList<>();

        public Boolean valider(){
            boolean valide = true;
            for (AppFormField formField : formFields){
                if(!formField.valider()){
                    valide = false;
                }
            }
            return valide;
        }

    }
}