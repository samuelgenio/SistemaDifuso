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

import br.com.samuka.sistemadifuso.model.Variavel;
import br.com.samuka.sistemadifuso.util.Variaveis;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class ObjetivoController implements Initializable {

    @FXML
    private ListView<Variavel> lvVariaveis;

    @FXML
    private Button btAddObjetivo;

    @FXML
    private Button btRemoveObjetivo;

    @FXML
    private ListView<Variavel> lvObjetivos;

    List<Variavel> variaveis;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loadData();
        
        lvVariaveis.setOnMouseClicked((MouseEvent event) -> {
            if (lvVariaveis.getSelectionModel().getSelectedIndex() != -1) {
                enableDisableComponent(btAddObjetivo, true);
            }
        });

        lvObjetivos.setOnMouseClicked((MouseEvent event) -> {
            if (lvObjetivos.getSelectionModel().getSelectedIndex() != -1) {
                enableDisableComponent(btRemoveObjetivo, true);
            }
        });

    }

    @FXML
    void onActionBtSalvar(ActionEvent ev) {
        
        
        
        Variaveis.variaveis = variaveis;
    }
    
    @FXML
    void onActionBtAddObjetivo(ActionEvent ev) {

        if(lvVariaveis.getSelectionModel().getSelectedIndex() != -1) {
            Variavel var = lvVariaveis.getItems().remove(lvVariaveis.getSelectionModel().getSelectedIndex());
            var.setObjetivo(true);
            lvObjetivos.getItems().add(var);
        }
        
    }
    
    @FXML
    void onActionBtAddAllObjetivo(ActionEvent ev) {
        for (Variavel var : lvVariaveis.getItems().subList(0, lvVariaveis.getItems().size())) {
            var.setObjetivo(true);
            lvObjetivos.getItems().add(var);
        }
        lvVariaveis.getItems().remove(0, lvVariaveis.getItems().size());
    }
    
    @FXML
    void onActionBtRemoveObjetivo(ActionEvent ev) {
        if(lvObjetivos.getSelectionModel().getSelectedIndex() != -1) {
            Variavel var = lvObjetivos.getItems().remove(lvObjetivos.getSelectionModel().getSelectedIndex());
            var.setObjetivo(false);
            lvVariaveis.getItems().add(var);
        }
    }
    
    @FXML
    void onActionBtRemoveAllObjetivo(ActionEvent ev) {
        for (Variavel var : lvObjetivos.getItems().subList(0, lvObjetivos.getItems().size())) {
            var.setObjetivo(false);
            lvVariaveis.getItems().add(var);
        }
        lvObjetivos.getItems().remove(0, lvObjetivos.getItems().size());
    }

    @FXML
    void onActionBtFechar(Event ev) {
        ((Node)(ev.getSource())).getScene().getWindow().hide();
    }

    /**
     * Desabilita componente
     *
     * @param control Controle a ser desabilitado.
     * @param enable true para habilitar, false para desabilitar.
     */
    private void enableDisableComponent(Object control, boolean enable) {
        if (control instanceof Control) {
            ((Control) control).setDisable(!enable);
        } else {
            ((Text) control).setDisable(!enable);
        }
    }
    
    /**
     * Carrega as variáveis
     */
    private void loadData() {
        
        if(Variaveis.variaveis != null && Variaveis.variaveis.size() > 0) {
            
            variaveis = new ArrayList<>();
            
            for (Variavel var : Variaveis.variaveis) {
                try {
                    variaveis.add(var.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(ObjetivoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            for (Variavel var : variaveis) {
                if(var.isObjetivo()) {
                    lvObjetivos.getItems().add(var);
                } else {
                    lvVariaveis.getItems().add(var);
                }
            }
            
        }
    }
}
