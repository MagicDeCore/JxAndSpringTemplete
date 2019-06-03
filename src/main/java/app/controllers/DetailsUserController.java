package app.controllers;

import app.ControllersConfiguration;
import app.entity.data.user.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class DetailsUserController {

    @FXML private TextField code;
    @FXML private TextField address;
    @FXML private TextField name;
    @FXML private Label errorMessage;
    @FXML private Button backBtn;
    @FXML private TextField email;

    @Qualifier("mainView")
    @Autowired private ControllersConfiguration.ViewHolder mainView;

    @FXML
    void backBtnAction(ActionEvent event) {
        clearFieldsData();
        backBtn.getScene().setRoot(mainView.getView());
    }

    public void initUserDetails(User user) {
        this.errorMessage.setText("Пользователь: " + user.getName());
        this.name.setText(user.getName());
        this.code.setText(user.getCode());
        if (user.getInfo() != null) {
            this.email.setText(user.getInfo().getEmail());
            this.address.setText(user.getInfo().getAddress());
        }
    }

    private void clearFieldsData() {
        this.name.setText("");
        this.code.setText("");
        this.email.setText("");
        this.address.setText("");
        this.errorMessage.setText("");
    }
}
