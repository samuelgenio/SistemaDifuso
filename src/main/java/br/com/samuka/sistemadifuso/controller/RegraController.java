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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class RegraController implements Initializable {

    @FXML
    private Button btIncluirRegra;

    @FXML
    private Button btAlterarRegra;

    @FXML
    private Button btExcluirRegra;
    
    @FXML
    private TextField tfNomeRegra;

    @FXML
    private TextField tfOrdemRegra;

    @FXML
    private ListView<RegraItem> lvRegras;

    Regra regra;

    private boolean saved;

    public void initData(Regra regra) {
        if(regra != null) {
            try {
                this.regra = regra.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(RegraController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        loadData();
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        lvRegras.setOnMouseClicked((MouseEvent event) -> {
            if (lvRegras.getSelectionModel().getSelectedIndex() != -1) {
                
                enableDisableComponent(btIncluirRegra, true);
                enableDisableComponent(btAlterarRegra, false);
                enableDisableComponent(btExcluirRegra, false);
                
                if (!lvRegras.getSelectionModel().getSelectedItem().isReservedString()) {
                    enableDisableComponent(btAlterarRegra, true);
                    enableDisableComponent(btExcluirRegra, true);
                }
                
            }
        });
        
        tfNomeRegra.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    try {
                        if(regra == null) {
                            regra = new Regra();
                        }
                        regra.setNome(tfNomeRegra.getText());
                    } catch (Exception e) {
                    }
                }
            }
        });
        
        tfOrdemRegra.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    try {
                        if(regra == null) {
                            regra = new Regra();
                        }
                        try{
                            regra.setOrdem(Integer.parseInt(tfOrdemRegra.getText()));
                        } catch (Exception e) {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        
    }

    @FXML
    void onActionBtIncluirRegra(ActionEvent event) {

        int i = getSeEntao();

        int numCondicoes = (regra != null && regra.getItens() != null) ? regra.getItens().size() : 0;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Variaveis.DIR_VIEW + "itemRegra.fxml"));
            BorderPane root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Item Regra");
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.setMaximized(false);

            ItemRegraController itemController = loader.<ItemRegraController>getController();
            itemController.initData(numCondicoes == 0, i == 0);

            stage.showAndWait();

            if (itemController.isSaved()) {

                RegraItem item = itemController.getItem();

                if (regra == null) {
                    regra = new Regra();
                }

                if (i == 0) {
                    //condição
                    regra.addItem(item);
                } else {
                    //resultado
                    regra.setItemConclusao(item);
                }

                loadData();
            }

        } catch (IOException e) {
        }

    }

    @FXML
    void onActionBtAlterarRegra(ActionEvent event) {

        RegraItem itemEdit = lvRegras.getSelectionModel().getSelectedItem();

        if (itemEdit.isENTAO() || itemEdit.isSE()) {
            return;
        }

        int i = getSeEntao();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Variaveis.DIR_VIEW + "itemRegra.fxml"));
            BorderPane root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Item Regra");
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.setMaximized(false);

            ItemRegraController itemController = loader.<ItemRegraController>getController();
            itemController.initData(lvRegras.getSelectionModel().getSelectedIndex() == 1, itemEdit.clone(), i == 0);

            stage.showAndWait();

            if (itemController.isSaved()) {

                RegraItem item = itemController.getItem();

                itemEdit.setVariavel(item.getVariavel());
                itemEdit.setMedida(item.getMedida());
                itemEdit.setOperator(item.getOperator());
                itemEdit.setModificador(item.getModificador());

                loadData();
            }

        } catch (IOException e) {
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(RegraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void onActionBtExcluirRegra(ActionEvent event) {

        int i = getSeEntao();
        if (i == 0) {
            regra.getItens().remove(lvRegras.getSelectionModel().getSelectedItem());
        } else {
            regra.setItemConclusao(null);
        }
        loadData();
    }

    @FXML
    void onActionBtSalvar(Event ev) {

        if (!tfNomeRegra.getText().isEmpty() && !tfOrdemRegra.getText().isEmpty()) {

            int numOrdemRegra = -1;

            try {
                numOrdemRegra = Integer.parseInt(tfOrdemRegra.getText());
            } catch (NumberFormatException e) {
                return;
            }
            
            regra.setNome(tfNomeRegra.getText());
            regra.setOrdem(numOrdemRegra);

            saved = true;
            onActionBtFechar(ev);
        }

    }

    @FXML
    void onActionBtFechar(Event ev) {
        ((Node) (ev.getSource())).getScene().getWindow().hide();
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

        if(regra != null) {
            tfNomeRegra.setText(regra.getNome());
            tfOrdemRegra.setText(String.valueOf(regra.getOrdem()));
        }
        
        lvRegras.getItems().remove(0, lvRegras.getItems().size());

        lvRegras.getItems().add(RegraItem.getItemSe());

        if (regra != null && regra.getItens() != null && regra.getItens().size() > 0) {
            lvRegras.getItems().addAll(regra.getItens());
        }

        lvRegras.getItems().add(RegraItem.getItemEntao());

        if (regra != null && regra.getItemConclusao() != null) {
            lvRegras.getItems().add(regra.getItemConclusao());
        }
    }

    /**
     * Atualiza as variaveis da aplicação.
     */
    private void atualizaVariaveisGlobal() {

        /*List<Variavel> variaveis = new ArrayList<>();
        
        for (Object object : lvVariaveis.getItems().toArray()) {
            
            Variavel var = (Variavel) object;
            
            variaveis.add(var);
            
        }
        
        //List<Variavel> variaveis = (List<Variavel>) lvVariaveis.getItems().subList(0, lvVariaveis.getItems().size());
        Variaveis.variaveis = variaveis;*/
    }

    /**
     *
     * @return 0 para SE, 1 para ENTAO
     */
    private int getSeEntao() {

        RegraItem item = null;

        int i = lvRegras.getSelectionModel().getSelectedIndex();
        if (i >= 0) {
            item = lvRegras.getItems().get(i);
            while (!item.isReservedString()) {
                i--;
                if (i >= 0) {
                    item = lvRegras.getItems().get(i);
                } else {
                    break;
                }
            }
        }

        return item != null && item.isENTAO() ? 1 : 0;

    }

    public Regra getRegra() {
        return regra;
    }

    public boolean isSaved() {
        return saved;
    }

}
