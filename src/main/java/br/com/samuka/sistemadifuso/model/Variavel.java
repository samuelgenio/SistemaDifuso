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
package br.com.samuka.sistemadifuso.model;

import java.util.ArrayList;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class Variavel {

    public static final double DOUBLE_NULL_VALUE = -9999;

    private String descricao;

    private ArrayList<Medida> medidas;

    private boolean objetivo;

    private double value;

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the medidas
     */
    public ArrayList<Medida> getMedidas() {
        return medidas;
    }

    /**
     * @param medidas the medidas to set
     */
    public void setMedidas(ArrayList<Medida> medidas) {
        this.medidas = medidas;
    }

    /**
     * @return the objetivo
     */
    public boolean isObjetivo() {
        return objetivo;
    }

    /**
     * @param objetivo the objetivo to set
     */
    public void setObjetivo(boolean objetivo) {
        this.objetivo = objetivo;
    }

    public void addMedida(Medida medida) {

        if (medidas == null) {
            medidas = new ArrayList<>();
        }

        medidas.add(medida);

    }

    @Override
    public String toString() {
        return getDescricao();
    }

    @Override
    public Variavel clone() throws CloneNotSupportedException {

        Variavel variavel = new Variavel();
        variavel.setDescricao(this.getDescricao());
        variavel.setMedidas(this.getMedidas());
        variavel.setObjetivo(this.isObjetivo());

        return variavel;
    }

    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Aplica a função de ativação de acordo com o valor recebido pela variável.
     */
    public void activationFuncion() {

        if (value != DOUBLE_NULL_VALUE) {

            for (Medida medida : medidas) {
                medida.activationFuncion(value);
            }
        }

    }

}
