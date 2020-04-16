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
import java.util.List;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class Modificador {

    /**
     * Muito, pessoas muito altas.
     * <pre>
     * f(x) = x^2
     * </pre>
     */
    public final static int MOD_CONCENTRACAO = 0;

    /**
     * Algo, pessoas mais ou menos altas.
     * <pre>
     * f(x) = x^0.5
     * </pre>
     */
    public final static int MOD_DILATACAO = 1;

    /**
     * Algo, pessoas de fato altas.
     * <pre>
     * SE x > 0.5
     *  f(x) = 1-2(1-x)^2
     * SENAO
     *  f(x) = 2(x)^2
     * </pre>
     */
    public final static int MOD_INTENSIFICACAO = 2;

    /**
     * Muito, pessoas muito, muito altas. n = 3.
     * <pre>
     * f(x) = x^n
     * </pre>
     *
     */
    public final static int MOD_POTENCIA = 3;

    private int value = -1;

    private String descricao = "";

    /**
     *
     * @param modificador Modificador.
     */
    public Modificador(int modificador) {

        switch (modificador) {
            case MOD_CONCENTRACAO:
                descricao = "Muito";
                break;
            case MOD_DILATACAO:
                descricao = "Mais ou menos";
                break;
            case MOD_INTENSIFICACAO:
                descricao = "De fato";
                break;
            case MOD_POTENCIA:
                descricao = "Muito, muito";
                break;
        }

        value = modificador;

    }

    public static List<Modificador> getModificadores() {

        List<Modificador> retorno = new ArrayList<>();

        retorno.add(new Modificador(-1));
        retorno.add(new Modificador(MOD_CONCENTRACAO));
        retorno.add(new Modificador(MOD_DILATACAO));
        retorno.add(new Modificador(MOD_INTENSIFICACAO));
        retorno.add(new Modificador(MOD_POTENCIA));

        return retorno;
    }

    @Override
    public String toString() {
        return descricao;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Executa o calculo do modificador.
     *
     * @param value Valor a ser calculado.
     * @return
     */
    public double calc(double value) {

        double retorno = 0;

        switch (this.value) {
            case MOD_CONCENTRACAO:
                retorno = Math.pow(value, 2);
                break;
            case MOD_DILATACAO:
                retorno = Math.pow(value, 0.5);
                break;
            case MOD_INTENSIFICACAO:
                if(value > 0.5) {
                    retorno = 1-2*(Math.pow(1-value, 2));
                } else {
                    retorno = 2*(Math.pow(value, 2));
                }
                break;
            case MOD_POTENCIA:
                retorno = Math.pow(value, 3);
                break;
        }

        return retorno;

    }

}
