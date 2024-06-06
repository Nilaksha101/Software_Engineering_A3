package com.ais_r_enhanced.ais.r.enhanced.Controller;

import com.ais_r_enhanced.ais.r.enhanced.ComputeClient;
import com.ais_r_enhanced.ais.r.enhanced.Model.ManagementStaff;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;


public class ManagementStaffListController {

    @FXML
    public TableColumn<ManagementStaff, String> fullName;
    @FXML
    public TableColumn<ManagementStaff, String> address;
    @FXML
    public TableColumn<ManagementStaff, String> phoneNo;
    @FXML
    public TableColumn<ManagementStaff, String> emailAddress;
    @FXML
    public TableColumn<ManagementStaff, String> staffId;
    @FXML
    public TableColumn<ManagementStaff, ManagementStaff.ManagementLevel> managementLevel;
    @FXML
    public TableColumn<ManagementStaff, ManagementStaff.Branch> branch;

    @FXML
    public TableView<ManagementStaff> managementTableView;

    @FXML
    public Button moveToHome;
    @FXML
    public TableView<String> tableView;

    @FXML
    public void initialize() {
        fullName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFullName()));
        address.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        phoneNo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        emailAddress.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEmail()));
        staffId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStaffId()));
        managementLevel.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getManagementLevel()));
        branch.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBranch()));
        loadRecruit();
    }

    private void loadRecruit() {
        List<ManagementStaff> managementStaffs = (List<ManagementStaff>) ComputeClient.sendRequest("getAllManagementStaff");

        ObservableList<ManagementStaff> recruitData = FXCollections.observableArrayList(managementStaffs);
        managementTableView.setItems(recruitData);
    }

}
