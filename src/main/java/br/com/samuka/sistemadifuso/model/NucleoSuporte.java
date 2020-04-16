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
 * @author Samuel
 */
public class NucleoSuporte {

    public static final int INT_NULL_VALUE = -9999;

    private double suporteInicial = INT_NULL_VALUE;

    private double nucleoInicial = INT_NULL_VALUE;

    private double nucleoFinal = INT_NULL_VALUE;

    private double suporteFinal = INT_NULL_VALUE;

    /**
     * @return the suporteInicial
     */
    public double getSuporteInicial() {
        return suporteInicial;
    }

    /**
     * @param suporteInicial the suporteInicial to set
     */
    public void setSuporteInicial(double suporteInicial) {
        this.suporteInicial = suporteInicial;
    }

    /**
     * @return the nucleoInicial
     */
    public double getNucleoInicial() {
        return nucleoInicial;
    }

    /**
     * @param nucleoInicial the nucleoInicial to set
     */
    public void setNucleoInicial(double nucleoInicial) {
        this.nucleoInicial = nucleoInicial;
    }

    /**
     * @return the nucleoFinal
     */
    public double getNucleoFinal() {
        return nucleoFinal;
    }

    /**
     * @param nucleoFinal the nucleoFinal to set
     */
    public void setNucleoFinal(double nucleoFinal) {
        this.nucleoFinal = nucleoFinal;
    }

    /**
     * @return the suporteFinal
     */
    public double getSuporteFinal() {
        return suporteFinal;
    }

    /**
     * @param suporteFinal the suporteFinal to set
     */
    public void setSuporteFinal(double suporteFinal) {
        this.suporteFinal = suporteFinal;
    }

    /**
     * Retorna os valores do suporte vara visualização.
     *
     * @return
     */
    @Override
    public String toString() {

        if(nucleoInicial == INT_NULL_VALUE) {
            return "";
        }
        
        String retorno = "";

        if (suporteInicial != INT_NULL_VALUE) {
            retorno += suporteInicial + " ~ ";
        }

        retorno += "[" + nucleoInicial + " ";

        if (nucleoFinal != INT_NULL_VALUE) {
            retorno += +nucleoFinal;
        }

        retorno += "] ";

        if (suporteFinal != INT_NULL_VALUE) {
            retorno += " ~ " + suporteFinal;
        }

        return retorno;

    }

}
