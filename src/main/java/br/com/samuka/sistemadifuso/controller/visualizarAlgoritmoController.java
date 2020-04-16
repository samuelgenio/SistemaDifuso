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
import br.com.samuka.sistemadifuso.model.RegraItem;
import br.com.samuka.sistemadifuso.util.Variaveis;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class visualizarAlgoritmoController implements Initializable {

    @FXML
    private ListView<String> lvRegras;
    
    private boolean saved;

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
    void onActionBtFechar(Event ev) {
        ((Node) (ev.getSource())).getScene().getWindow().hide();
    }

    /**
     * Carrega as variáveis
     */
    private void loadData() {

        if(Variaveis.regras != null) {
            
            for (Regra regra : Variaveis.regras) {
                lvRegras.getItems().add(regra.getNome());
                
                int i = 0;
                for (RegraItem item : regra.getItens()) {
                    lvRegras.getItems().add(i == 0 ? "SE " + item.toString() : item.toString());
                    i++;
                }
                
                lvRegras.getItems().add("ENTAO " + regra.getItemConclusao().toString());
                
                lvRegras.getItems().add("");
                
            }
            lvRegras.refresh();
        }
        
    }

    public boolean isSaved() {
        return saved;
    }
}
