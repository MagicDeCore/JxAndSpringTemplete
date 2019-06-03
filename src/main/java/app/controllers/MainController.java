package app.controllers;

import app.ControllersConfiguration;
import app.entity.data.user.domain.User;
import app.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;


@SuppressWarnings("SpringJavaAutowiringInspection")
public class MainController {
    // Инъекции Spring
    @Qualifier("addUserView")
    @Autowired private ControllersConfiguration.ViewHolder addUserView;
    @Qualifier("editUserView")
    @Autowired private ControllersConfiguration.ViewHolder editUserView;
    @Qualifier("detailUserView")
    @Autowired private ControllersConfiguration.ViewHolder detailUserView;

    @Autowired private AddUserController addUserController;
    @Autowired private EditUserController editUserController;
    @Autowired private DetailsUserController detailsUserController;
    @Autowired private UserService userService;

    // Инъекции JavaFX
    @FXML private TableView<User> usersTable;
    private ObservableList<User> usersData;

    @FXML private TextField code;
    @FXML private TextField name;
    @FXML private Button editBtn;
    @FXML private Button searchBtn;
    @FXML private Button startSearchBtn;
    @FXML private Button createBtn;
    @FXML private Button deleteBtn;
    @FXML private Button refreshBtn;
    @FXML private Button backBtn;

//    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        usersTable.setEditable(true);
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
        setSearchVisibility(false);
    }

    @FXML
    void createBtnAction(ActionEvent event) {
        addUserController.updateUsersTable();
        usersTable.getScene().setRoot(addUserView.getView());
    }

    @FXML
    void editBtnAction(ActionEvent event) {
        if (selectedUser != null) {
            editUserController.initUserForEdit(selectedUser);
            usersTable.getScene().setRoot(editUserView.getView());
        }
    }

    @FXML
    void deleteBtnAction(ActionEvent event) {
        if (selectedUser != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete User " + selectedUser.getName() + " with CODE: " + selectedUser.getCode() +" ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                userService.deleteUser(selectedUser);
                usersData.remove(selectedUser);
                usersTable.setItems(usersData);
                selectedUser = null;
            }
        }
    }

    @FXML
    void refreshBtnAction(ActionEvent event) {
        updateUsersTable();
    }

    public void updateUsersTable() {
        usersData = FXCollections.observableArrayList(userService.getAllUsers());
        usersTable.setItems(usersData);
    }

    @FXML
    void startSearchBtnAction(ActionEvent event) {
        if (!name.getText().equals("") || !code.getText().equals("")) {
            usersData = FXCollections.observableArrayList(userService.searchUsersByInputtedData(name.getText(), code.getText()));
            usersTable.setItems(usersData);
        }
    }

    @FXML
    void backBtnAction(ActionEvent event) {
        if (startSearchBtn.isVisible()) {
            setSearchVisibility(false);
            selectedUser = null;
        }
    }

    @FXML
    void searchBtnAction(ActionEvent event) {
        setSearchVisibility(true);
    }

    private void setSearchVisibility(boolean isVisible) {
        if (isVisible) {
            searchBtn.setVisible(false);
            createBtn.setVisible(false);
            editBtn.setVisible(false);
            deleteBtn.setVisible(false);

            startSearchBtn.setVisible(true);
            backBtn.setVisible(true);
            name.setVisible(true);
            code.setVisible(true);
        } else {
            searchBtn.setVisible(true);
            createBtn.setVisible(true);
            editBtn.setVisible(true);
            deleteBtn.setVisible(true);

            startSearchBtn.setVisible(false);
            backBtn.setVisible(false);
            name.setVisible(false);
            code.setVisible(false);
        }
    }

    private User selectedUser;
    @FXML
    private void selectUser(MouseEvent event) {
        selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2) {
            System.out.println(usersTable.getSelectionModel().getSelectedItem());
            detailsUserController.initUserDetails(selectedUser);
            usersTable.getScene().setRoot(detailUserView.getView());
        }
    }
}
