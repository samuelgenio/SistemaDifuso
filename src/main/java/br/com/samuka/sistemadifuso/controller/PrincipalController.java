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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class PrincipalController implements Initializable {

    @FXML
    public BorderPane desktopPane;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void onActionMenuItem(ActionEvent ev) {

        try {

            MenuItem menuItem = (MenuItem) ev.getTarget();
            
            Class menuControll = Class.forName("br.com.samuka.sistemadifuso.controller.MenuController");
            Constructor c;

            c = menuControll.getConstructor(new Class[]{});
            
            Method method = null;
            
            try{
                method = menuControll.getDeclaredMethod(menuItem.getId(), new Class[0]);
            } catch (NoSuchMethodException | SecurityException e) {
                method = menuControll.getDeclaredMethod(menuItem.getId(), new Object().getClass());
            }
            
            method.setAccessible(true);
            
            Object o = c.newInstance(new Object[]{});
            
            if(method.getParameterCount() > 0) {
                Object param = desktopPane;
                method.invoke(o, param);
            } else {
                method.invoke(o, new Object[]{});
            }
            
            

        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
        }

    }
    
}
