package app.controllers;

import app.ControllersConfiguration;
import app.entity.data.user.domain.User;
import app.entity.data.user.repository.UserRepository;
import app.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;

@SuppressWarnings("SpringJavaAutowiringInspection")
public class AddUserController {

    @Qualifier("mainView")
    @Autowired private ControllersConfiguration.ViewHolder mainView;
    @Autowired private MainController mainController;
    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;

    @FXML private TableView<User> usersTable;
    private ObservableList<User> usersData;

    @FXML private TextField code;
    @FXML private TextField name;

    @FXML private TextField email;
    @FXML private TextField address;
    @FXML private Label errorMessage;
    @FXML private Button backBtn;
    @FXML private Button addBtn;

    @PostConstruct
    public void init() {
        usersData = FXCollections.observableArrayList(userService.getAllUsers());
        TableColumn idColumn = usersTable.getColumns().get(0);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn nameColumn = usersTable.getColumns().get(1);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn codeColumn = usersTable.getColumns().get(2);
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

        usersTable.getColumns().setAll(idColumn, nameColumn, codeColumn);
        usersTable.setItems(usersData);
    }


    @FXML
    public void initialize() {
        usersTable.setItems(usersData);
    }

    @FXML
    void addBtnAction(ActionEvent event) {
        errorMessage.setText("");

        User user = null;
        try {
            user = userService.saveUserWithData(name.getText(), code.getText(), email.getText(), address.getText());
        } catch (Exception e) {
            errorMessage.setText("Пользователь с таким кодом уже Существует");
        }
        if (user != null) {
            usersData.add(user);
            clearFieldsData();
            mainController.updateUsersTable();
        }
    }

    private void clearFieldsData() {
        name.setText("");
        code.setText("");
        email.setText("");
        address.setText("");
        errorMessage.setText("");
    }

    public void updateUsersTable() {
        usersData = FXCollections.observableArrayList(userService.getAllUsers());
        usersTable.setItems(usersData);
    }

    @FXML
    void backBtnAction(ActionEvent event) {
        clearFieldsData();
        code.getScene().setRoot(mainView.getView());
    }
}