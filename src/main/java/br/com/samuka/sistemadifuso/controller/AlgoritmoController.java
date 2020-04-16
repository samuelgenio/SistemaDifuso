/*
 * Copyright (C) 2020 Asconn
 *
 * This file is part of SistemaDifuso.
 * SistemaDifuso is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * SistemaDifuso is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>
 */

package br.com.samuka.sistemadifuso.controller;

import br.com.samuka.sistemadifuso.model.Regra;
import br.com.samuka.sistemadifuso.util.Variaveis;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class AlgoritmoController implements Initializable {

    @FXML
    private ListView<Regra> lvRegras;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    @FXML
    public void onActionBtNovaRegra(ActionEvent ev) {

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Variaveis.DIR_VIEW + "regra.fxml"));
            BorderPane root = loader.load();

            Stage stage = new Stage();		
            stage.setScene(new Scene(root));
            stage.setTitle("Regra");
            stage.initOwner(((Node) ev.getSource()).getScene().getWindow());
            stage.setMaximized(false);
            RegraController regraController = loader.<RegraController>getController();
            regraController.initData(null);
            stage.setResizable(false);
            stage.showAndWait();
            
            if(regraController.isSaved()) {
                if(Variaveis.regras == null) {
                    Variaveis.regras = new ArrayList<>();
                }
                
                Variaveis.regras.add(regraController.getRegra());
                loadData();
            }
            
        } catch (IOException e) {
        }
        
    }

    @FXML
    public void onActionBtAbrirRegra(ActionEvent ev) {

        if(lvRegras.getSelectionModel().getSelectedIndex() == -1) 
            return;
        
        Regra regra = lvRegras.getSelectionModel().getSelectedItem();
        
        int pos = lvRegras.getSelectionModel().getSelectedIndex();
        
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Variaveis.DIR_VIEW + "regra.fxml"));
            BorderPane root = loader.load();

            Stage stage = new Stage();		
            stage.setScene(new Scene(root));
            stage.setTitle("Regra");
            stage.initOwner(((Node) ev.getSource()).getScene().getWindow());
            stage.setMaximized(false);
            RegraController regraController = loader.<RegraController>getController();
            regraController.initData(regra);
            stage.setResizable(false);
            stage.showAndWait();
            
            if(regraController.isSaved()) {
                Variaveis.regras.remove(pos);
                Variaveis.regras.add(pos, regraController.getRegra());
                loadData();
            }
            
        } catch (IOException e) {
        }
        
    }

    @FXML
    public void onActionBtExcluirRegra(ActionEvent ev) {

        int index = lvRegras.getSelectionModel().getSelectedIndex();
        
        if(index != -1) {
            Variaveis.regras.remove(index);
        }
        loadData();
    }

    @FXML
    public void onActionBtVisualizar(ActionEvent ev) {

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Variaveis.DIR_VIEW + "visualizarAlgoritmo.fxml"));
            BorderPane root = loader.load();

            Stage stage = new Stage();		
            stage.setScene(new Scene(root));
            stage.setTitle("Regras");
            stage.initOwner(((Node) ev.getSource()).getScene().getWindow());
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
        }
        
    }
    
    @FXML
    public void onActionBtExecutar(ActionEvent ev) {

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Variaveis.DIR_VIEW + "executarAlgoritmo.fxml"));
            BorderPane root = loader.load();

            Stage stage = new Stage();		
            stage.setScene(new Scene(root));
            stage.setTitle("Execução");
            stage.initOwner(((Node) ev.getSource()).getScene().getWindow());
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
        }
        
    }

    @FXML
    public void onActionBtVariaveis(ActionEvent ev) {

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Variaveis.DIR_VIEW + "variaveis.fxml"));
            BorderPane root = loader.load();

            Stage stage = new Stage();		
            stage.setScene(new Scene(root));
            stage.setTitle("Variáveis");
            stage.initOwner(((Node) ev.getSource()).getScene().getWindow());
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
        }

    }

    @FXML
    public void onActionBtObjetivo(ActionEvent ev) {

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Variaveis.DIR_VIEW + "objetivo.fxml"));
            BorderPane root = loader.load();

            Stage stage = new Stage();		
            stage.setScene(new Scene(root));
            stage.setTitle("Variáveis Objetivos");
            stage.initOwner(((Node) ev.getSource()).getScene().getWindow());
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
        }
        
    }

    @FXML
    public void onActionBtFechar(ActionEvent ev) {

    }

    private void loadData() {
        
        lvRegras.getItems().remove(0, lvRegras.getItems().size());
        
        if(Variaveis.regras != null) {
            
            for (Regra regra : Variaveis.regras) {
                lvRegras.getItems().add(regra);
            }
            lvRegras.refresh();
        }
        
    }
    
}
