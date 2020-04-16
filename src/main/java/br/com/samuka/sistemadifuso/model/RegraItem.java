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
public class RegraItem {

    public static final int OPERATOR_E = 0;

    public static final int OPERATOR_OU = 1;

    private Variavel variavel;

    private Integer operator = -1;

    private Modificador modificador;

    private Medida medida;

    private String reservedStr;

    private double activationValue;

    /**
     * @return the variavel
     */
    public Variavel getVariavel() {
        return variavel;
    }

    /**
     * @param variavel the variavel to set
     */
    public void setVariavel(Variavel variavel) {
        this.variavel = variavel;
    }

    /**
     * @return the medida
     */
    public Medida getMedida() {
        return medida;
    }

    /**
     * @param medida the medida to set
     */
    public void setMedida(Medida medida) {
        this.medida = medida;
    }

    /**
     * @return the operator
     */
    public Integer getOperator() {
        return operator;
    }

    /**
     * @return the operator
     */
    public String getOperatorStr() {
        return operator == OPERATOR_E ? "E" : (operator == OPERATOR_OU ? "OU" : "");
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public static RegraItem getItemSe() {
        RegraItem item = new RegraItem();
        item.setReservedStr(Regra.TEXT_SE);
        return item;
    }

    public static RegraItem getItemEntao() {
        RegraItem item = new RegraItem();
        item.setReservedStr(Regra.TEXT_ENTAO);
        return item;
    }

    /**
     * @return the reservedStr
     */
    public String getReservedStr() {
        return reservedStr;
    }

    public boolean isSE() {
        return reservedStr != null && reservedStr.equals(Regra.TEXT_SE);
    }

    public boolean isENTAO() {
        return reservedStr != null && reservedStr.equals(Regra.TEXT_ENTAO);
    }

    /**
     * @param reservedStr the reservedStr to set
     */
    public void setReservedStr(String reservedStr) {
        this.reservedStr = reservedStr;
    }

    public boolean isReservedString() {
        return reservedStr != null && !this.reservedStr.equals("");
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

    /**
     * @return the modificador
     */
    public Modificador getModificador() {
        return modificador;
    }

    /**
     * @param modificador the modificador to set
     */
    public void setModificador(Modificador modificador) {
        this.modificador = modificador;
    }

    @Override
    public RegraItem clone() throws CloneNotSupportedException {

        RegraItem item = new RegraItem();
        item.setVariavel(this.getVariavel());
        item.setOperator(this.getOperator());
        item.setMedida(this.getMedida());
        item.setReservedStr(this.reservedStr);
        item.setModificador(this.getModificador());

        return item;
    }

    @Override
    public String toString() {

        if (reservedStr != null) {
            return reservedStr;
        } else {
            String modif = modificador != null ? modificador + " " : "";
            return getOperatorStr() + "      " + variavel + " é " + modif + medida;
        }

    }

    public double calc(double valueToCalc) {

        double retorno = 0;

        if (this.operator == OPERATOR_E) {
            retorno = this.getMedida().getActivationValue() < valueToCalc ? this.getMedida().getActivationValue() : valueToCalc;
        } else {
            retorno = this.getMedida().getActivationValue() > valueToCalc ? this.getMedida().getActivationValue() : valueToCalc;
        }

        return retorno;

    }

    public double calc(double valueToCalc, int operator) {

        double retorno = 0;

        if (operator == OPERATOR_E) {
            retorno = this.getMedida().getActivationValue() < valueToCalc ? this.getMedida().getActivationValue() : valueToCalc;
        } else {
            retorno = this.getMedida().getActivationValue() > valueToCalc ? this.getMedida().getActivationValue() : valueToCalc;
        }

        return retorno;

    }

    /**
     * <pre>
     * Dividendo[0]
     * Dividor [1];
     * </pre>
     *
     * @return
     */
    public double[] getDividendoDivisor() {

        double[] retorno = new double[2];

        int defaultDistance = 5;

        double qtdOcorrencia = 1 + (getMedida().getNucleoSuporte().getNucleoFinal() - getMedida().getNucleoSuporte().getNucleoInicial()) / defaultDistance;

        double valueImplement = getMedida().getNucleoSuporte().getNucleoInicial();

        if (getMedida().getNucleoSuporte().getNucleoInicial() == 0) {
            qtdOcorrencia--;
            valueImplement = 5;
        }

        double dividendo = 0;

        int count = 0;
        while (count < qtdOcorrencia) {
            dividendo += valueImplement + count * defaultDistance;
            count++;
        }

        dividendo = dividendo * getActivationValue();

        double divisor = getActivationValue() * qtdOcorrencia;

        retorno[0] = dividendo;
        retorno[1] = divisor;

        return retorno;

    }

    public boolean equals(RegraItem itemToCompare) {
        return getVariavel().equals(itemToCompare.getVariavel()) && getMedida().equals(itemToCompare.getMedida());
    }

    /**
     * Executa o calculo do modificador.
     *
     * @return
     */
    public double getValue() {
        return modificador != null ? modificador.calc(getMedida().getActivationValue()) : getMedida().getActivationValue();
    }

}
