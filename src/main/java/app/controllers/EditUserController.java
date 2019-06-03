package app.controllers;

import app.ControllersConfiguration;
import app.entity.data.user.domain.User;
import app.services.UserService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;

@SuppressWarnings("SpringJavaAutowiringInspection")
public class EditUserController {

    private User selectedUser;

    @Qualifier("mainView")
    @Autowired private ControllersConfiguration.ViewHolder mainView;
    @Autowired private MainController mainController;
    @Autowired private UserService userService;

    @FXML private TableView<User> usersTable;
    private ObservableList<User> usersData;

    @FXML private TextField code;
    @FXML private TextField name;
    @FXML private TextField email;
    @FXML private TextField address;
    @FXML private Label errorMessage;
    @FXML private Button backBtn;
    @FXML private Button saveBtn;
    @FXML private Label userLabel;

    @PostConstruct
    public void init() {

    }

    @FXML
    public void initialize() {

    }

    public void initUserForEdit(User user) {
        this.selectedUser = user;
        this.name.setText(user.getName());
        this.code.setText(user.getCode());
        if (selectedUser.getInfo() != null) {
            this.email.setText(user.getInfo().getEmail());
            this.address.setText(user.getInfo().getAddress());
        } else {
            this.email.setText("");
            this.address.setText("");
        }
        this.userLabel.setText(user.getName());
    }

    @FXML
    void saveBtnAction(ActionEvent event) {
        User savedUser = null;
        try {
            savedUser = userService.editUserWithNewData(selectedUser, name.getText(), code.getText(), email.getText(), address.getText());
        } catch (Exception e) {
            errorMessage.setText("Пользователь с таким Кодом уже существует!");
        }
        if (savedUser != null) {
            errorMessage.setText("");
            mainController.updateUsersTable();
            selectedUser = null;
            name.getScene().setRoot(mainView.getView());
        }
    }

    @FXML
    void backBtnAction(ActionEvent event) {
        errorMessage.setText("");
        name.getScene().setRoot(mainView.getView());
    }
}
