package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import lib.User;
import model.AddModel;
import model.NewChatModel;
import view.AddView;
import view.NewChatView;

/**
 *
 * @author blegoh
 */
class AddController {

    private AddModel theModel;
    private AddView theView;
    private User user;

    AddController(AddModel theModel, AddView theView) {
        this.theModel = theModel;
        this.theView = theView;
        this.user = this.theModel.getUser();
        this.theView.addCancelListener(new CancelListener());
        this.theView.addTambahListener(new TambahListener());
    }

    class TambahListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                if (theModel.isUserExist(theView.getUsername())) {
                    User kontak = new User(theView.getUsername());
                    theModel.setKontak(kontak);
                    theModel.saveKontak();
                    JOptionPane.showMessageDialog(theView, "Berhasil ditambahkan");
                }else{
                    JOptionPane.showMessageDialog(theView, "User tidak ditemukan");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            theView.clear();
        }

    }

    class CancelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                theView.dispose();
                NewChatView theView = new NewChatView();
                NewChatModel theModel;
                theModel = new NewChatModel(user);
                NewChatController theController = new NewChatController(theModel, theView);
                theView.setVisible(true);
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
