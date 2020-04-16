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
public class Regra {
    
    public static final String TEXT_SE = "SE";
    
    public static final String TEXT_ENTAO = "ENTAO";

    private List<RegraItem> itens;
    
    private RegraItem itemConclusao;

    private String nome;
            
    private int ordem;
    
    private double activationValue;
    
    /**
     * @return the itens
     */
    public List<RegraItem> getItens() {
        return itens;
    }

    /**
     * @param itens the itens to set
     */
    public void setItens(List<RegraItem> itens) {
        this.itens = itens;
    }

    /**
     * @return the itemConclusao
     */
    public RegraItem getItemConclusao() {
        return itemConclusao;
    }

    /**
     * @param itemConclusao the itemConclusao to set
     */
    public void setItemConclusao(RegraItem itemConclusao) {
        this.itemConclusao = itemConclusao;
    }
    
    public void addItem(RegraItem item) {
        if(itens == null) {
            itens = new ArrayList<>();
        }
        itens.add(item);
        
    }

    @Override
    public Regra clone() throws CloneNotSupportedException {
        
        Regra regra = new Regra();
        for (RegraItem item : itens) {
            regra.addItem(item.clone());
        }
            
        if(this.itemConclusao != null) {
            regra.setItemConclusao(this.itemConclusao.clone());
        }
        
        regra.setNome(this.getNome());
        regra.setOrdem(this.getOrdem());
        
        return regra;
        
    }

    @Override
    public String toString() {
        return getOrdem() + "    " + getNome();
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the ordem
     */
    public int getOrdem() {
        return ordem;
    }

    /**
     * @param ordem the ordem to set
     */
    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }
    
    public void activationFuncition() {
        
        double finalValue = 0;
        
        int qtdE = 0;
        
        List<RegraItem> listExecuting = new ArrayList<>();
        
        for (RegraItem item : itens) {
            listExecuting.add(item);
            if(item.getOperator() == RegraItem.OPERATOR_E) {
                qtdE++;
            }
        }
        
        while (listExecuting.size() > 1) {
            
            int i = 0;
            for (RegraItem item : listExecuting) {
                if(i == 0) {
                    i++;
                    continue;
                }
                
                if(qtdE == 0 || item.getOperator() == RegraItem.OPERATOR_E) {
                    finalValue = item.calc(listExecuting.get(i-1).getValue());
                    listExecuting.remove(i);
                    listExecuting.remove(i-1);
                    
                    if(qtdE > 0) {
                        qtdE--;
                    }
                    
                    break;
                }
                i++;
            }
        }
        
        if(!listExecuting.isEmpty()) {
            finalValue = listExecuting.get(0).calc(finalValue);
        }
        
        setActivationValue(finalValue);
        
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
    
    
}
