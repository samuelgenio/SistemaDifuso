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

package br.com.samuka.sistemadifuso.main;

import br.com.samuka.sistemadifuso.util.Variaveis;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class Main extends Application {

    public static Parent root;
    public static FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) {

        try {

            loader = new FXMLLoader(getClass().getResource(Variaveis.DIR_VIEW + "principal.fxml"));
            root = loader.load();
            
            Scene scene = new Scene(root);
            
            scene.getStylesheets().add(getClass().getResource(Variaveis.DIR_CSS + "principal.css").toExternalForm());
            
            primaryStage.setScene(scene);
            primaryStage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
            primaryStage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
            
            primaryStage.show();
        } catch (IOException e) {
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
