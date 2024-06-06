package com.ais_r_enhanced.ais.r.enhanced.Controller;

import com.ais_r_enhanced.ais.r.enhanced.ComputeClient;
import com.ais_r_enhanced.ais.r.enhanced.Model.RecruitDetails;
import com.ais_r_enhanced.ais.r.enhanced.Model.RecruitDto;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;


public class RecruitDetailsListSortedAndGroupedController {

    @FXML
    public TableColumn<RecruitDto, String> fullName;
    @FXML
    public TableView<RecruitDto> recruitTableView;
    @FXML
    public Button moveToHome;
    @FXML
    public TableColumn<RecruitDto, RecruitDetails.Location> locations;
    private List<RecruitDto> recruitDtos;
    private ObservableList<String> data;


    @FXML
    public void initialize() {
        fullName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFullName()));
        locations.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getLocation()));
        loadRecruit();
    }

    private void loadRecruit() {
        recruitDtos = (List<RecruitDto>) ComputeClient.sendRequest("getAllRecruitSortByLastNameDescAndGroupByLocation");

        ObservableList<RecruitDto> recruitData = FXCollections.observableArrayList(recruitDtos);
        recruitTableView.setItems(recruitData);
    }

}
