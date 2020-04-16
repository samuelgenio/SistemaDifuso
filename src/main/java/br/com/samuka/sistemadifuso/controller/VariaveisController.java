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
import br.com.samuka.sistemadifuso.model.NucleoSuporte;
import br.com.samuka.sistemadifuso.model.Variavel;
import br.com.samuka.sistemadifuso.util.Uteis;
import br.com.samuka.sistemadifuso.util.Variaveis;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class VariaveisController implements Initializable {

    @FXML
    public Button btIncluirVariavel;

    @FXML
    public Button btExcluirVariavel;

    @FXML
    public Button btIncluirMedida;

    @FXML
    public Button btExcluirMedida;

    @FXML
    public Button btFechar;

    @FXML
    public ListView lvVariaveis, lvMedidas, lvNucleoSuporte;

    @FXML
    public Button btAddVariavel, btAddMedida, btAddAll;

    @FXML
    public Text tVariavel, tMedida, tNucleo1, tNucleo2, tSuporte1, tSuporte2;

    @FXML
    public TextField txVariavel, txMedida, txNucleo1, txNucleo2, txSuporte1, txSuporte2;

    /**
     * Inserindo novo registro.
     */
    private boolean flgNovaVariavel;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loadData();
        
        btAddVariavel.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                onActionBtAddVariavel(event);
            }
        });

        btAddMedida.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                onActionBtAddMedida(event);
            }
        });
        
        btAddAll.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                onActionBtAddAll(event);
            }
        });

        lvVariaveis.setOnMouseClicked((MouseEvent event) -> {
            if (lvVariaveis.getSelectionModel().getSelectedIndex() != -1) {
                txVariavel.setText(lvVariaveis.getSelectionModel().getSelectedItem().toString());
                txVariavel.requestFocus();
                enableDisableComponent(btIncluirMedida, true);
                enableDisableComponent(btExcluirVariavel, true);
                
                lvMedidas.getItems().remove(0, lvMedidas.getItems().size());
                lvNucleoSuporte.getItems().remove(0, lvNucleoSuporte.getItems().size());
                
                if(((Variavel) lvVariaveis.getSelectionModel().getSelectedItem()).getMedidas() != null) {
                    lvMedidas.getItems().addAll(((Variavel) lvVariaveis.getSelectionModel().getSelectedItem()).getMedidas());
                }
            }
        });

        lvMedidas.setOnMouseClicked((MouseEvent event) -> {
            if (lvMedidas.getSelectionModel().getSelectedIndex() != -1) {
                txMedida.setText(lvMedidas.getSelectionModel().getSelectedItem().toString());
                txMedida.requestFocus();
                enableDisableComponent(btIncluirMedida, true);
                enableDisableComponent(btExcluirMedida, true);
                clearAllTexts();
                enableDisableComponent(txMedida, true);
                enableDisableComponent(btAddMedida, true);
                enableDisableNucleoSuporte(true);
                flgNovaVariavel = false;
                
                lvNucleoSuporte.getItems().remove(0, lvNucleoSuporte.getItems().size());
                
                NucleoSuporte nucleoSuporte = new NucleoSuporte();
                
                try{
                    if(((Medida) lvMedidas.getSelectionModel().getSelectedItem()).getNucleoSuporte() != null) {
                        nucleoSuporte = ((Medida) lvMedidas.getSelectionModel().getSelectedItem()).getNucleoSuporte();
                        txMedida.setText(((Medida) lvMedidas.getSelectionModel().getSelectedItem()).getDescricao());
                    }
                } catch (Exception e) {}
                
                lvNucleoSuporte.getItems().add(nucleoSuporte);
                
                txNucleo1.setText(String.valueOf(nucleoSuporte.getNucleoInicial()));
                
                if (nucleoSuporte.getNucleoFinal() == NucleoSuporte.INT_NULL_VALUE) {
                    txNucleo2.setText("");
                } else {
                    txNucleo2.setText(String.valueOf(nucleoSuporte.getNucleoFinal()));
                }
                
                if (nucleoSuporte.getSuporteInicial() == NucleoSuporte.INT_NULL_VALUE) {
                    txSuporte1.setText("");
                } else {
                    txSuporte1.setText(String.valueOf(nucleoSuporte.getSuporteInicial()));
                }
                
                if (nucleoSuporte.getSuporteFinal() == NucleoSuporte.INT_NULL_VALUE) {
                    txSuporte2.setText("");
                } else {
                    txSuporte2.setText(String.valueOf(nucleoSuporte.getSuporteFinal()));
                }
            }
        });

    }

    @FXML
    void onActionBtIncluirVariavel(ActionEvent ev) {

        clearAllTexts();
        enableDisableComponent(tVariavel, true);
        enableDisableComponent(txVariavel, true);
        enableDisableComponent(btAddVariavel, true);
        txVariavel.requestFocus();

        flgNovaVariavel = true;
    }
    
    @FXML
    void onActionBtIncluirMedida(ActionEvent ev) {

        Variavel variavel = (Variavel) lvVariaveis.getSelectionModel().getSelectedItem();
        
        if(variavel != null) {
        
            clearAllTexts();
            enableDisableComponent(tMedida, true);
            enableDisableComponent(txMedida, true);
            enableDisableComponent(btAddMedida, true);
            enableDisableNucleoSuporte(true);
            txVariavel.setText(variavel.getDescricao());
            txMedida.requestFocus();

            flgNovaVariavel = true;
        }
    }

    @FXML
    void onActionBtAddVariavel(Event ev) {

        if (txVariavel.getText().isEmpty()) {
            return;
        }

        Variavel variavel = new Variavel();

        if (flgNovaVariavel) {

            List<Variavel> variaveis = lvVariaveis.getItems();

            if (variaveis != null) {
                for (Variavel var : variaveis) {
                    if (var.getDescricao().equalsIgnoreCase(txVariavel.getText())) {
                        Uteis.showAlert(Alert.AlertType.WARNING, "Aviso", "Váriável já adicionada");
                        return;
                    }
                }
            }

            variavel.setDescricao(txVariavel.getText());

            lvVariaveis.getItems().add(variavel);

            lvVariaveis.getSelectionModel().select(lvVariaveis.getItems().size() - 1);
            
            enableDisableComponent(tMedida, true);
            enableDisableComponent(txMedida, true);
            enableDisableComponent(btAddMedida, true);
            enableDisableNucleoSuporte(true);

            lvMedidas.getItems().remove(0, lvMedidas.getItems().size());
            lvNucleoSuporte.getItems().remove(0, lvNucleoSuporte.getItems().size());
            txMedida.requestFocus();

            flgNovaVariavel = false;

        } else {
            variavel = (Variavel) lvVariaveis.getSelectionModel().getSelectedItem();
            variavel.setDescricao(txVariavel.getText());
        }
        lvVariaveis.refresh();
        atualizaVariaveisGlobal();
    }

    @FXML
    void onActionBtAddMedida(Event ev) {

        flgNovaVariavel = lvMedidas.getSelectionModel().getSelectedIndex() == -1;

        if (txMedida.getText().isEmpty()) {
            return;
        }

        Medida medida = new Medida();

        Variavel variavel = (Variavel) lvVariaveis.getSelectionModel().getSelectedItem();

        if (flgNovaVariavel) {

            List<Medida> medidas = lvMedidas.getItems();

            if (medidas != null) {
                for (Medida med : medidas) {
                    if (med.getDescricao().equalsIgnoreCase(txMedida.getText())) {
                        Uteis.showAlert(Alert.AlertType.WARNING, "Aviso", "Medida já adicionada");
                        return;
                    }
                }
            }

            medida.setDescricao(txMedida.getText());

            variavel.addMedida(medida);

            lvMedidas.getItems().add(medida);

            txMedida.setText("");

            txMedida.requestFocus();

        } else {

            medida = (Medida) lvMedidas.getSelectionModel().getSelectedItem();

            medida.setDescricao(txMedida.getText());
        }

        lvMedidas.refresh();
        atualizaVariaveisGlobal();
    }

    @FXML
    void onActionBtAddAll(Event ev) {

        flgNovaVariavel = lvMedidas.getSelectionModel().getSelectedIndex() == -1;

        if (txMedida.getText().isEmpty() || txNucleo1.getText().isEmpty() || (txSuporte1.getText().isEmpty() && txSuporte2.getText().isEmpty())) {
            return;
        }

        Medida medida = new Medida();

        NucleoSuporte nucleoSuporte = new NucleoSuporte();

        try {
            nucleoSuporte.setNucleoInicial(Double.parseDouble(txNucleo1.getText()));
            if (!txNucleo2.getText().isEmpty()) {
                nucleoSuporte.setNucleoFinal(Double.parseDouble(txNucleo2.getText()));
            } else {
                nucleoSuporte.setNucleoFinal(NucleoSuporte.INT_NULL_VALUE);
            }

            if (!txSuporte1.getText().isEmpty()) {
                nucleoSuporte.setSuporteInicial(Double.parseDouble(txSuporte1.getText()));
            } else {
                nucleoSuporte.setSuporteInicial(NucleoSuporte.INT_NULL_VALUE);
            }

            if (!txSuporte2.getText().isEmpty()) {
                nucleoSuporte.setSuporteFinal(Double.parseDouble(txSuporte2.getText()));
            } else {
                nucleoSuporte.setSuporteFinal(NucleoSuporte.INT_NULL_VALUE);
            }

        } catch (NumberFormatException e) {
            Uteis.showAlert(Alert.AlertType.ERROR, "Erro", "Núcleo e Suporte somente aceitam valores númericos.");
        }

        Variavel variavel = (Variavel) lvVariaveis.getSelectionModel().getSelectedItem();

        if (flgNovaVariavel) {

            List<Medida> medidas = lvMedidas.getItems();

            if (medidas != null) {
                for (Medida med : medidas) {
                    if (med.getDescricao().equalsIgnoreCase(txMedida.getText())) {
                        Uteis.showAlert(Alert.AlertType.WARNING, "Aviso", "Medida já adicionada");
                        return;
                    }
                }
            }

            medida.setDescricao(txMedida.getText());

            medida.setNucleoSuporte(nucleoSuporte);

            variavel.addMedida(medida);

            lvMedidas.getItems().add(medida);

            lvNucleoSuporte.getItems().add(nucleoSuporte);

            //lvMedidas.getSelectionModel().select(lvMedidas.getItems().size() - 1);
            txMedida.setText("");
            txNucleo1.setText("");
            txNucleo2.setText("");
            txSuporte1.setText("");
            txSuporte2.setText("");

            txMedida.requestFocus();

        } else {

            medida = (Medida) lvMedidas.getSelectionModel().getSelectedItem();

            medida.setNucleoSuporte(nucleoSuporte);

            medida.setDescricao(txMedida.getText());
        }

        lvMedidas.refresh();
        lvNucleoSuporte.refresh();
        atualizaVariaveisGlobal();
    }

    @FXML
    void onActionBtEcluirVariavel(Event ev) {

        clearAllTexts();
        
        if (lvVariaveis.getSelectionModel().getSelectedIndex() != -1) {

            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            
            ButtonType btnSim = new ButtonType("Sim");
            ButtonType btnNao = new ButtonType("Não");
            
            confirmacao.setTitle("Confimação");
            confirmacao.setContentText("Deseja remover Variável " + ((Variavel) lvVariaveis.getSelectionModel().getSelectedItem()) + " ?");
            confirmacao.getButtonTypes().setAll(btnSim, btnNao);
            confirmacao.showAndWait().ifPresent(b -> {
                if (b == btnSim) {
                    lvVariaveis.getItems().remove(lvVariaveis.getSelectionModel().getSelectedIndex());
                    lvMedidas.getItems().remove(0, lvMedidas.getItems().size());
                    lvNucleoSuporte.getItems().remove(0, lvNucleoSuporte.getItems().size());
                    lvVariaveis.refresh();
                    lvMedidas.refresh();
                    lvNucleoSuporte.refresh();
                }
            });
            atualizaVariaveisGlobal();
        }
        
        enableDisableComponent(btIncluirMedida, false);
        enableDisableComponent(btExcluirMedida, false);
        if(lvVariaveis.getItems().size() < 1) {
            enableDisableComponent(btExcluirVariavel, false);
        }

    }

    @FXML
    void onActionBtEcluirMedida(Event ev) {

        clearAllTexts();
        
        if (lvMedidas.getSelectionModel().getSelectedIndex() != -1) {

            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            
            ButtonType btnSim = new ButtonType("Sim");
            ButtonType btnNao = new ButtonType("Não");
            
            confirmacao.setTitle("Confimação");
            confirmacao.setContentText("Deseja remover Medida " + ((Variavel) lvMedidas.getSelectionModel().getSelectedItem()) + " ?");
            confirmacao.getButtonTypes().setAll(btnSim, btnNao);
            confirmacao.showAndWait().ifPresent(b -> {
                if (b == btnSim) {
            
                    Variavel variavel = (Variavel) lvVariaveis.getSelectionModel().getSelectedItem();

                    variavel.getMedidas().remove(lvMedidas.getSelectionModel().getSelectedIndex());

                    lvMedidas.getItems().remove(lvMedidas.getSelectionModel().getSelectedIndex());
                    lvNucleoSuporte.getItems().remove(0, lvNucleoSuporte.getItems().size());
                    lvMedidas.refresh();
                    lvNucleoSuporte.refresh();
                }
            });
            atualizaVariaveisGlobal();
        }
        
        if(lvVariaveis.getItems().size() < 1) {
            enableDisableComponent(btExcluirMedida, false);
        }
    }
    
    @FXML
    void onActionBtEcluirNucleoSuporte(Event ev) {

        if (lvNucleoSuporte.getSelectionModel().getSelectedIndex() != -1) {

            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            
            ButtonType btnSim = new ButtonType("Sim");
            ButtonType btnNao = new ButtonType("Não");
            
            confirmacao.setTitle("Confimação");
            confirmacao.setContentText("Deseja remover Nucleo/Suporte " + ((Variavel) lvNucleoSuporte.getSelectionModel().getSelectedItem()) + " ?");
            confirmacao.getButtonTypes().setAll(btnSim, btnNao);
            confirmacao.showAndWait().ifPresent(b -> {
                if (b == btnSim) {
            
                    Medida medida = (Medida) lvMedidas.getSelectionModel().getSelectedItem();

                    medida.setNucleoSuporte(null);

                    lvNucleoSuporte.getItems().remove(0, lvNucleoSuporte.getItems().size());
                    lvNucleoSuporte.refresh();
                }
            });
            atualizaVariaveisGlobal();
        }
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

    private void enableDisableNucleoSuporte(boolean enable) {
        enableDisableComponent(tNucleo1, enable);
        enableDisableComponent(tNucleo2, enable);
        enableDisableComponent(tSuporte1, enable);
        enableDisableComponent(tSuporte2, enable);
        enableDisableComponent(txNucleo1, enable);
        enableDisableComponent(txNucleo2, enable);
        enableDisableComponent(txSuporte1, enable);
        enableDisableComponent(txSuporte2, enable);
        enableDisableComponent(btAddAll, enable);
    }
    
    private void clearAllTexts() {
        txVariavel.setText("");
        txMedida.setText("");
        txNucleo1.setText("");
        txNucleo2.setText("");
        txSuporte1.setText("");
        txSuporte2.setText("");
        txNucleo1.setText("");
        txNucleo2.setText("");
        txSuporte1.setText("");
        txSuporte2.setText("");
    }

    /**
     * Carrega as variáveis
     */
    private void loadData() {
        if(Variaveis.variaveis != null && Variaveis.variaveis.size() > 0) {
            lvVariaveis.getItems().addAll(Variaveis.variaveis);
        }
    }
    
    /**
     * Atualiza as variaveis da aplicação.
     */
    private void atualizaVariaveisGlobal() {
        
        List<Variavel> variaveis = new ArrayList<>();
        
        for (Object object : lvVariaveis.getItems().toArray()) {
            
            Variavel var = (Variavel) object;
            
            variaveis.add(var);
            
        }
        
        //List<Variavel> variaveis = (List<Variavel>) lvVariaveis.getItems().subList(0, lvVariaveis.getItems().size());
        Variaveis.variaveis = variaveis;
    }
    
}
