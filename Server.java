import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends javax.swing.JFrame {

    static ServerSocket ss;
    static Socket socket_obj;
    static DataInputStream dis;
    static DataOutputStream dos;

    public Server() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        server_label = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        show_msg = new javax.swing.JTextArea();
        write_msg = new javax.swing.JTextField();
        send_msg = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        server_label.setFont(new java.awt.Font("Ubuntu", 1, 24));
        server_label.setText("SERVER");

        show_msg.setBackground(new java.awt.Color(158, 175, 182));
        show_msg.setColumns(20);
        show_msg.setRows(5);
        jScrollPane1.setViewportView(show_msg);

        write_msg.setText("Type Here !!");

        send_msg.setText("PING");
        send_msg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                send_msgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(server_label, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(write_msg, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(send_msg, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(server_label, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(write_msg, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(send_msg, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }

    private void send_msgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_send_msgActionPerformed
        try {
            String msg = write_msg.getText();
            dos.writeUTF(msg); 
            write_msg.setText("");
        } catch(Exception e) {
            show_msg.setText("Opps!!  \n  Message couldn't send !!");
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });

        String msg_from_client = "";

        try {
            ss = new ServerSocket(1963);
            socket_obj = ss.accept();
            dis = new DataInputStream(socket_obj.getInputStream());
            dos = new DataOutputStream(socket_obj.getOutputStream());

            while (!msg_from_client.equals("byebye")) {
                msg_from_client = dis.readUTF();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        show_msg.setText(show_msg.getText() + " \n Client :  " + msg_from_client);
                    }
                });
            }
        } catch (Exception e) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    show_msg.setText("Wait ! \n Client is not Connected  !!");
                }
            });
        }
    }

    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton send_msg;
    private javax.swing.JLabel server_label;
    private static javax.swing.JTextArea show_msg;
    private javax.swing.JTextField write_msg;
}
