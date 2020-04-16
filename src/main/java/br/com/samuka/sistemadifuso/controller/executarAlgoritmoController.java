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
import br.com.samuka.sistemadifuso.model.Variavel;
import br.com.samuka.sistemadifuso.util.Uteis;
import br.com.samuka.sistemadifuso.util.Variaveis;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class executarAlgoritmoController implements Initializable {

    @FXML
    private ComboBox<Variavel> cbVariavel;

    @FXML
    private TextField tfValor;

    private boolean saved;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loadData();

        cbVariavel.valueProperty().addListener(new ChangeListener<Variavel>() {
            @Override
            public void changed(ObservableValue ov, Variavel t, Variavel t1) {
                if (cbVariavel.getSelectionModel().getSelectedIndex() != -1) {
                    String value = cbVariavel.getSelectionModel().getSelectedItem().getValue() == Variavel.DOUBLE_NULL_VALUE ? "" : String.valueOf(cbVariavel.getSelectionModel().getSelectedItem().getValue());
                    tfValor.setText(value);
                    tfValor.setDisable(false);
                } else {
                    tfValor.setDisable(true);
                }
            }
        });

        tfValor.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    try {
                        double value = Double.parseDouble(tfValor.getText());
                        cbVariavel.getSelectionModel().getSelectedItem().setValue(value);
                    } catch (NumberFormatException e) {
                    }
                }
            }
        });

    }

    @FXML
    void onActionBtAvancar(Event ev) {

        boolean next = true;

        for (Variavel variavel : Variaveis.variaveis) {
            variavel.activationFuncion();
            if (!variavel.isObjetivo() && variavel.getValue() == Variavel.DOUBLE_NULL_VALUE) {
                next = false;
                break;
            }
        }

        if (next) {

            List<RegraItem> listItemConclusao = new ArrayList<>();

            for (Regra regra : Variaveis.regras) {
                regra.activationFuncition();

                if (regra.getActivationValue() > 0) {
                    boolean found = false;
                    for (RegraItem item : listItemConclusao) {
                        if (item.equals(regra.getItemConclusao())) {
                            item.setActivationValue(item.calc(regra.getActivationValue(), RegraItem.OPERATOR_OU));
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        try {
                            RegraItem item = regra.getItemConclusao().clone();
                            item.setActivationValue(regra.getActivationValue());
                            listItemConclusao.add(item);
                        } catch (CloneNotSupportedException ex) {
                            Logger.getLogger(executarAlgoritmoController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }

            double dividendo = 0;
            double divisor = 0;

            for (RegraItem item : listItemConclusao) {
                double[] dividendoDivisor = item.getDividendoDivisor();
                dividendo += dividendoDivisor[0];
                divisor += dividendoDivisor[1];
            }

            DecimalFormat format = new DecimalFormat("##.##");

            Uteis.showAlert(Alert.AlertType.INFORMATION, "Resultado Obtido", format.format(dividendo / divisor) + " de " + listItemConclusao.get(0).getVariavel().getDescricao());

        }

    }

    @FXML
    void onActionBtFechar(Event ev) {
        ((Node) (ev.getSource())).getScene().getWindow().hide();
    }

    /**
     * Carrega as variáveis
     */
    private void loadData() {

        for (Variavel variavel : Variaveis.variaveis) {
            if (!variavel.isObjetivo()) {
                cbVariavel.getItems().add(variavel);
            }
        }
    }

    public boolean isSaved() {
        return saved;
    }
}
