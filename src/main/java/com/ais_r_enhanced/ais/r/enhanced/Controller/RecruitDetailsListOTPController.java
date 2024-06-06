package com.ais_r_enhanced.ais.r.enhanced.Controller;

import com.ais_r_enhanced.ais.r.enhanced.ComputeClient;
import com.ais_r_enhanced.ais.r.enhanced.Model.RecruitDetails;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Date;
import java.util.List;


public class RecruitDetailsListOTPController {

    @FXML
    public TableColumn<RecruitDetails, String> fullName;
    @FXML
    public TableColumn<RecruitDetails, String> address;
    @FXML
    public TableColumn<RecruitDetails, String> phoneNo;
    @FXML
    public TableColumn<RecruitDetails, String> emailAddress;
    @FXML
    public TableColumn<RecruitDetails, Date> dateOfInterview;
    @FXML
    public TableColumn<RecruitDetails, RecruitDetails.QualificationLevel> highestQualification;
    @FXML
    public TableColumn<RecruitDetails, RecruitDetails.Department> department;
    @FXML
    public TableView<RecruitDetails> recruitTableView;
    @FXML
    public TableColumn<RecruitDetails, String> adminFullName;
    @FXML
    public Button moveToHome;
    @FXML
    public TableView<String> tableView;
    @FXML
    public TableColumn<RecruitDetails, String> otp;
    @FXML
    public TableColumn<RecruitDetails, RecruitDetails.Location> locations;

    private ObservableList<String> data;
    private List<RecruitDetails> recruitList;
//    private RecruitService recruitService = new RecruitService();

    @FXML
    public void initialize() {
        fullName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFullName()));
        address.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        phoneNo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        emailAddress.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEmail()));
        dateOfInterview.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getInterviewDate()));
        highestQualification.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQualificationLevel()));
        department.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDepartment()));
        otp.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getOTPCode()));
        locations.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getLocation()));
        loadRecruit();
    }

    private void loadRecruit() {
        recruitList = (List<RecruitDetails>) ComputeClient.sendRequest("getAllOTPRequestRecruit");
        ObservableList<RecruitDetails> recruitData = FXCollections.observableArrayList(recruitList);
        recruitTableView.setItems(recruitData);
    }

    public void generateOTP(ActionEvent actionEvent) {
        ComputeClient.sendRequest("setOTPInDB", recruitList);
        loadRecruit();
    }
}
