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

import br.com.samuka.sistemadifuso.util.ManipuleXML;
import br.com.samuka.sistemadifuso.util.Variaveis;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class MenuController {

    private String filePah = null;
    
    private final FileChooser fileChooser = new FileChooser();

    public MenuController() {
        fileChooser.setTitle("Selecione uma imagem");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("xml", ".xml"));
    }
    
    private void arquivoNovo(Object objectPane) {
        
        BorderPane borderPane = (BorderPane) objectPane;

        try {
            BorderPane pane = FXMLLoader.load(getClass().getResource(Variaveis.DIR_VIEW + "algoritmo.fxml"));
            borderPane.getChildren().setAll(pane);
        } catch (IOException e) {
        }

    }

    private void arquivoAbrir(Object objectPane) {
        try{
            
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                ManipuleXML.carregaDados(file);
                arquivoNovo(objectPane);
                this.filePah = file.getAbsolutePath();
            }
        } catch (Exception e) {
        }
    }

    private void arquivoSalvar() {
        try{
            
            File fileToSave = null;
            
            if (filePah != null) {
                fileToSave = new File(filePah);
            }
            
            ManipuleXML.salvarArquivo(Variaveis.regras, Variaveis.variaveis, fileToSave);
        } catch (Exception e) {
        }
    }
}
