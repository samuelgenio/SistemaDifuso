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

package br.com.samuka.sistemadifuso.util;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class VerticalButtonBar extends VBox {

    public VerticalButtonBar() {
        setFillWidth(true);
    }

    public void addButton(Button button) {
        button.setMaxWidth(Double.MAX_VALUE);
        getChildren().add(button);
    }

}
