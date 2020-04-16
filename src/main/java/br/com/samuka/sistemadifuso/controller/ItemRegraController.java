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

import br.com.samuka.sistemadifuso.model.Medida;
import br.com.samuka.sistemadifuso.model.Modificador;
import br.com.samuka.sistemadifuso.model.RegraItem;
import br.com.samuka.sistemadifuso.model.Variavel;
import br.com.samuka.sistemadifuso.util.Uteis;
import br.com.samuka.sistemadifuso.util.Variaveis;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class ItemRegraController implements Initializable {

    @FXML
    private ComboBox<Variavel> cbVariavel;

    @FXML
    private Text txCondicao;

    @FXML
    private Text txConectivo;

    @FXML
    private ComboBox<Modificador> cbModificador;
    
    @FXML
    private ComboBox<Medida> cbMedida;

    private RegraItem item;

    private boolean isCondicao;

    private boolean isFirstCond;

    @FXML
    private RadioButton rbE;

    @FXML
    private RadioButton rbOu;

    private boolean saved;

    public void initData(boolean isFirstCond, boolean isCondicao) {
        this.isFirstCond = isFirstCond;
        this.isCondicao = isCondicao;
        loadData();
    }

    public void initData(boolean isFirstCond, RegraItem item, boolean isCondicao) {
        this.isFirstCond = isFirstCond;
        this.item = item;
        this.isCondicao = isCondicao;
        loadData();
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cbVariavel.valueProperty().addListener(new ChangeListener<Variavel>() {
            @Override
            public void changed(ObservableValue ov, Variavel t, Variavel t1) {
                loadMedida();
            }
        });
    }

    private void loadMedida() {
        if (cbVariavel.getSelectionModel().getSelectedIndex() != -1) {
            cbMedida.getItems().remove(0, cbMedida.getItems().size());
            cbMedida.getItems().addAll(cbVariavel.getSelectionModel().getSelectedItem().getMedidas());
        }
    }

    @FXML
    void onActionBtSalvar(Event ev) {

        if (cbVariavel.getSelectionModel().getSelectedIndex() != -1
                && cbMedida.getSelectionModel().getSelectedIndex() != -1) {

            if (getItem() == null) {
                item = new RegraItem();
            }

            item.setVariavel(cbVariavel.getSelectionModel().getSelectedItem());
            if (isCondicao) {
                if (rbE.isSelected()) {
                    item.setOperator(RegraItem.OPERATOR_E);
                } else if (rbOu.isSelected()) {
                    item.setOperator(RegraItem.OPERATOR_OU);
                }
            }
            item.setMedida(cbMedida.getSelectionModel().getSelectedItem());
            
            if(cbModificador.getSelectionModel().getSelectedIndex() != -1 && cbModificador.getSelectionModel().getSelectedItem().getValue() != -1) {
                item.setModificador(cbModificador.getSelectionModel().getSelectedItem());
            } else {
                item.setModificador(null);
            }
            
            saved = true;
            onActionBtFechar(ev);
        } else {
            Uteis.showAlert(Alert.AlertType.ERROR, "Informação ausente!", "Para salvar é necessário informar todos os dados.");
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

        if (isCondicao) {
            txCondicao.setText("é");
        } else {
            txCondicao.setText("=");
        }

        if (isCondicao) {
            if (isFirstCond) {
                rbE.setDisable(true);
                rbOu.setDisable(true);
            } else {
                ToggleGroup groupRadio = new ToggleGroup();
                rbE.setToggleGroup(groupRadio);
                rbOu.setToggleGroup(groupRadio);
                rbE.setSelected(true);
            }
            
            cbModificador.getItems().addAll(Modificador.getModificadores());
            
        } else {
            cbModificador.setVisible(false);
            txConectivo.setVisible(false);
            rbE.setVisible(false);
            rbOu.setVisible(false);
        }
        
        if (isCondicao) {
            if (Variaveis.variaveis != null) {
                
                for (Variavel variavel : Variaveis.variaveis) {
                    if (!variavel.isObjetivo()) {
                        cbVariavel.getItems().add(variavel);
                    }
                }

                if (item != null) {
                    cbVariavel.getSelectionModel().select(item.getVariavel());
                    loadMedida();
                    cbMedida.getSelectionModel().select(item.getMedida());
                    if (item.getOperator() == RegraItem.OPERATOR_E) {
                        rbE.setSelected(true);
                    } else if (item.getOperator() == RegraItem.OPERATOR_OU) {
                        rbOu.setSelected(true);
                    }
                    
                    if(item.getModificador() != null) {
                        cbModificador.getSelectionModel().select(item.getModificador());
                    }
                    
                }

            }
        } else {
            for (Variavel variavel : Variaveis.variaveis) {
                if (variavel.isObjetivo()) {
                    cbVariavel.getItems().add(variavel);
                }
            }
            if (item != null) {
                cbVariavel.getSelectionModel().select(item.getVariavel());
                loadMedida();
                cbMedida.getSelectionModel().select(item.getMedida());
                if (item.getOperator() == RegraItem.OPERATOR_E) {
                    rbE.setSelected(true);
                } else if (item.getOperator() == RegraItem.OPERATOR_OU) {
                    rbOu.setSelected(true);
                }
            }
        }
    }

    /**
     * @return the item
     */
    public RegraItem getItem() {
        return item;
    }

    public boolean isSaved() {
        return saved;
    }
}
