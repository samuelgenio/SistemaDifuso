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

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class Medida {

    public final static int MAX_VALUE = 1;

    public final static int MIN_VALUE = 0;

    private String descricao;

    private NucleoSuporte nucleoSuporte;

    private double activationValue;

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

    @Override
    public String toString() {
        return getDescricao();
    }

    /**
     * @return the nucleoSuporte
     */
    public NucleoSuporte getNucleoSuporte() {
        return nucleoSuporte;
    }

    /**
     * @param nucleoSuporte the nucleoSuporte to set
     */
    public void setNucleoSuporte(NucleoSuporte nucleoSuporte) {
        this.nucleoSuporte = nucleoSuporte;
    }

    /**
     * @return the activationValue
     */
    public double getActivationValue() {
        return activationValue;
    }

    /**
     * @param activationValue the activationValue to set
     */
    public void setActivationValue(double activationValue) {
        this.activationValue = activationValue;
    }

    public void activationFuncion(double value) {

        if (nucleoSuporte.getNucleoInicial() <= value && value <= nucleoSuporte.getNucleoFinal()) {
            activationValue = MAX_VALUE;
        } else if (value >= nucleoSuporte.getSuporteInicial() && value < nucleoSuporte.getNucleoInicial()) {
            activationValue = (value - nucleoSuporte.getSuporteInicial()) / (nucleoSuporte.getNucleoInicial() - nucleoSuporte.getSuporteInicial());
        } else if (value <= nucleoSuporte.getSuporteFinal() && value > nucleoSuporte.getNucleoFinal()) {
            activationValue = (nucleoSuporte.getSuporteFinal() - value) / (nucleoSuporte.getSuporteFinal() - nucleoSuporte.getNucleoFinal());
        } else if (value < nucleoSuporte.getSuporteInicial() || value > nucleoSuporte.getSuporteFinal()) {
            activationValue = MIN_VALUE;
        }

    }

}
