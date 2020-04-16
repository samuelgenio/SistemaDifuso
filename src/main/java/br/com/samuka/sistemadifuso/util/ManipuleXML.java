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

import br.com.samuka.sistemadifuso.model.Medida;
import br.com.samuka.sistemadifuso.model.Modificador;
import br.com.samuka.sistemadifuso.model.NucleoSuporte;
import br.com.samuka.sistemadifuso.model.Regra;
import br.com.samuka.sistemadifuso.model.RegraItem;
import br.com.samuka.sistemadifuso.model.Variavel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author 'Samuel José Eugênio - https://github.com/samuelgenio'
 */
public class ManipuleXML {
    
    private static final String NODE_RAIZ = "difuso";
    
    private static final String NODE_VARIAVEIS = "variaveis";
    
    private static final String NODE_VARIAVEl = "variavel";
    
    private static final String NODE_REGRAS = "regras";
    
    private static final String NODE_MEDIDAS = "medida";
    
    private static final String NODE_REGRA = "regra";
    
    private static final String NODE_REGRA_ITEM = "regra_item";
    
    private static boolean proced = true;
    
    public static void salvarArquivo(List<Regra> regras, List<Variavel> variaveis, File file) throws Exception {
        
        if (file == null) {
            file = new File(Variaveis.pathOpenedModel);
        }
        
        proced = true;
        
        if (file.exists()) {
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            
            ButtonType btnSim = new ButtonType("Sim");
            ButtonType btnNao = new ButtonType("Não");
            
            confirmacao.setTitle("Confimação");
            confirmacao.setContentText("Arquivo destino já existe, Deseja sobreescreve-lo?");
            confirmacao.getButtonTypes().setAll(btnSim, btnNao);
            confirmacao.showAndWait().ifPresent(b -> {
                if (b == btnNao) {
                    proced = false;
                }
            });
        }
        
        if (proced) {
            
            if (file.exists()) {
                file.delete();
                file = new File(Variaveis.pathOpenedModel);
            }
            
            String data = gerarXml(regras, variaveis);
            
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(data.getBytes());
            fos.flush();
            fos.close();
        }
        
    }
    
    private static String gerarXml(List<Regra> regras, List<Variavel> variaveis) throws Exception {
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        Document doc = db.newDocument();

        //TAG RAIZ
        Element elemRaiz = doc.createElement(NODE_RAIZ);
        
        Element elemRegras = doc.createElement(NODE_REGRAS);
        Element elemVariaveis = doc.createElement(NODE_VARIAVEIS);
        
        Element elemVar = null;
        //Cria as tags de Telefone
        Element elemDescricao;
        Element elemObjetivo;
        
        Element elemMedDescricao;
        Element elemMedSuporteInicial;
        Element elemMedSuporteFinal;
        Element elemMedNucleoInicial;
        Element elemMedNucleoFinal;
        
        if (variaveis != null) {
            for (Variavel var : variaveis) {
                
                elemVar = doc.createElement("variavel");
                
                elemDescricao = doc.createElement("descricao");
                elemObjetivo = doc.createElement("objetivo");
                
                elemDescricao.setTextContent(var.getDescricao());
                elemObjetivo.setTextContent(String.valueOf(var.isObjetivo()));
                
                elemVar.appendChild(elemDescricao);
                elemVar.appendChild(elemObjetivo);
                
                Element elemMed;
                
                for (Medida medida : var.getMedidas()) {
                    
                    elemMed = doc.createElement(NODE_MEDIDAS);
                    
                    elemMedDescricao = doc.createElement("descricao");
                    elemMedSuporteInicial = doc.createElement("suporte_inicial");
                    elemMedSuporteFinal = doc.createElement("suporte_final");
                    elemMedNucleoInicial = doc.createElement("nucleo_inicial");
                    elemMedNucleoFinal = doc.createElement("nucleo_final");
                    
                    elemMedDescricao.setTextContent(medida.getDescricao());
                    elemMedSuporteInicial.setTextContent(medida.getNucleoSuporte() == null ? "" : String.valueOf(medida.getNucleoSuporte().getSuporteInicial()));
                    elemMedSuporteFinal.setTextContent(medida.getNucleoSuporte() == null ? "" : String.valueOf(medida.getNucleoSuporte().getSuporteFinal()));
                    elemMedNucleoInicial.setTextContent(medida.getNucleoSuporte() == null ? "" : String.valueOf(medida.getNucleoSuporte().getNucleoInicial()));
                    elemMedNucleoFinal.setTextContent(medida.getNucleoSuporte() == null ? "" : String.valueOf(medida.getNucleoSuporte().getNucleoFinal()));
                    
                    elemMed.appendChild(elemMedDescricao);
                    elemMed.appendChild(elemMedSuporteInicial);
                    elemMed.appendChild(elemMedSuporteFinal);
                    elemMed.appendChild(elemMedNucleoInicial);
                    elemMed.appendChild(elemMedNucleoFinal);
                    
                    elemVar.appendChild(elemMed);
                    
                }
                elemVariaveis.appendChild(elemVar);
            }
        }
        
        elemRaiz.appendChild(elemVariaveis);
        
        Element elemReg = null;
        //Cria as tags de Telefone
        Element elemNome;
        Element elemOrdem;
        
        Element elemRegConclusaoVar;
        Element elemRegConclusaoMed;
        
        Element elemRegItemVar;
        Element elemRegItemMed;
        Element elemRegItemEOu;
        Element elemRegItemModif;
        
        if (regras != null) {
            for (Regra reg : regras) {
                
                elemReg = doc.createElement(NODE_REGRA);
                
                elemNome = doc.createElement("nome");
                elemOrdem = doc.createElement("ordem");
                elemNome.setTextContent(reg.getNome());
                elemOrdem.setTextContent(String.valueOf(reg.getOrdem()));
                elemReg.appendChild(elemNome);
                elemReg.appendChild(elemOrdem);
                
                elemRegConclusaoVar = doc.createElement("variavel_conclusao");
                elemRegConclusaoMed = doc.createElement("variavel_medida");
                elemRegConclusaoVar.setTextContent(reg.getItemConclusao().getVariavel().getDescricao());
                elemRegConclusaoMed.setTextContent(reg.getItemConclusao().getMedida().getDescricao());
                elemReg.appendChild(elemRegConclusaoVar);
                elemReg.appendChild(elemRegConclusaoMed);
                
                Element elemItem;
                
                for (RegraItem item : reg.getItens()) {
                    
                    elemItem = doc.createElement(NODE_REGRA_ITEM);
                    
                    elemRegItemVar = doc.createElement("variavel_item");
                    elemRegItemMed = doc.createElement("medida_item");
                    elemRegItemEOu = doc.createElement("eOu");
                    elemRegItemModif = doc.createElement("modificador_item");
                    
                    elemRegItemVar.setTextContent(item.getVariavel().getDescricao());
                    elemRegItemMed.setTextContent(item.getMedida().getDescricao());
                    elemRegItemEOu.setTextContent(String.valueOf(item.getOperator()));
                    if(item.getModificador() != null) {
                        elemRegItemModif.setTextContent(String.valueOf(item.getModificador().getValue()));
                    } else {
                        elemRegItemModif.setTextContent("-1");
                    }
                    
                    elemItem.appendChild(elemRegItemVar);
                    elemItem.appendChild(elemRegItemMed);
                    elemItem.appendChild(elemRegItemEOu);
                    elemItem.appendChild(elemRegItemModif);
                    
                    elemReg.appendChild(elemItem);
                }
                elemRegras.appendChild(elemReg);
            }
        }
        
        elemRaiz.appendChild(elemRegras);
        
        doc.appendChild(elemRaiz);
        
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        
        StreamResult result = new StreamResult(new StringWriter());
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);
        
        String xmlString = result.getWriter().toString();
        
        return xmlString;
        
    }

    /**
     * Carrega base de dados para a aplicação
     *
     * @param file Arquivo a sr aberto.
     * @throws Exception
     * @throws TransformerException
     */
    public static void carregaDados(File file) throws Exception, TransformerException {
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        //Informamos qual arquivo xml vamos ler
        Document doc = db.parse(file);
        //Criamos um objeto Element que vai receber as informacoes de doc
        Element raiz = doc.getDocumentElement();
        
        Variaveis.regras = new ArrayList<>();
        
        Variaveis.variaveis = new ArrayList<>();
        
        NodeList elemRegras = doc.getElementsByTagName(NODE_REGRA);
        
        NodeList elemVariaveis = doc.getElementsByTagName(NODE_VARIAVEl);
        
        Element elemVar;
        
        for (int i = 0; i < elemVariaveis.getLength(); i++) {
            elemVar = (Element) elemVariaveis.item(i);
            
            Variavel variavel = new Variavel();
            variavel.setDescricao(getElementValue(elemVar, "descricao"));
            variavel.setObjetivo(Boolean.parseBoolean(getElementValue(elemVar, "objetivo")));
            
            NodeList elemMedida = elemVar.getElementsByTagName(NODE_MEDIDAS);
            
            Element elemMed;
            
            for (int j = 0; j < elemMedida.getLength(); j++) {
                
                elemMed = (Element) elemMedida.item(j);
                
                Medida medida = new Medida();
                medida.setDescricao(getElementValue(elemMed, "descricao"));
                
                NucleoSuporte nucleoSuporte = null;
                
                if (!getElementValue(elemMed, "nucleo_inicial").isEmpty()) {
                    nucleoSuporte = new NucleoSuporte();
                    
                    nucleoSuporte.setNucleoInicial(Double.parseDouble(getElementValue(elemMed, "nucleo_inicial")));
                    
                    try {
                        nucleoSuporte.setNucleoFinal(Double.parseDouble(getElementValue(elemMed, "nucleo_final")));
                    } catch (Exception e) {
                        nucleoSuporte.setNucleoFinal(NucleoSuporte.INT_NULL_VALUE);
                    }
                    try {
                        nucleoSuporte.setSuporteInicial(Double.parseDouble(getElementValue(elemMed, "suporte_inicial")));
                    } catch (Exception e) {
                        nucleoSuporte.setSuporteInicial(NucleoSuporte.INT_NULL_VALUE);
                    }
                    
                    try {
                        nucleoSuporte.setSuporteFinal(Double.parseDouble(getElementValue(elemMed, "suporte_final")));
                    } catch (Exception e) {
                        nucleoSuporte.setSuporteFinal(NucleoSuporte.INT_NULL_VALUE);
                    }
                }
                
                medida.setNucleoSuporte(nucleoSuporte);
                
                variavel.addMedida(medida);
                
            }
            
            Variaveis.variaveis.add(variavel);
        }
        
        Element elemReg;
        
        for (int i = 0; i < elemRegras.getLength(); i++) {
            elemReg = (Element) elemRegras.item(i);
            
            Regra regra = new Regra();
            regra.setNome(getElementValue(elemReg, "nome"));
            regra.setOrdem(Integer.parseInt(getElementValue(elemReg, "ordem")));
            
            RegraItem item = new RegraItem();
            item.setVariavel(getVariavel(getElementValue(elemReg, "variavel_conclusao")));
            item.setMedida(getMedida(item.getVariavel(), getElementValue(elemReg, "variavel_medida")));
            regra.setItemConclusao(item);
            NodeList elemRegItens = elemReg.getElementsByTagName(NODE_REGRA_ITEM);
            
            Element elemRegItem;
            
            for (int j = 0; j < elemRegItens.getLength(); j++) {
                
                elemRegItem = (Element) elemRegItens.item(j);
                
                RegraItem regraItem = new RegraItem();
                regraItem.setVariavel(getVariavel(getElementValue(elemRegItem, "variavel_item")));
                regraItem.setMedida(getMedida(regraItem.getVariavel(), getElementValue(elemRegItem, "medida_item")));
                regraItem.setOperator(Integer.parseInt(getElementValue(elemRegItem, "eOu")));
                
                try{
                    Modificador mod = Integer.parseInt(getElementValue(elemRegItem, "modificador_item")) != -1 ? new Modificador(Integer.parseInt(getElementValue(elemRegItem, "modificador_item"))) : null;
                    regraItem.setModificador(mod);
                } catch (Exception e) {
                }
                regra.addItem(regraItem);
                
            }
            
            Variaveis.regras.add(regra);
        }
        
    }
    
    private static String getElementValue(Element elem, String tagName) throws Exception {
        NodeList children = elem.getElementsByTagName(tagName);
        String result = null;
        
        if (children == null) {
            return result;
        }
        
        Element child = (Element) children.item(0);
        
        if (child == null) {
            return result;
        }
        
        result = child.getTextContent();
        
        return result;
    }
    
    private static Variavel getVariavel(String varName) {
        
        Variavel retorno = null;
        
        for (Variavel var : Variaveis.variaveis) {
            
            if (var.getDescricao().equalsIgnoreCase(varName)) {
                return var;
            }
        }
        return retorno;
    }
    
    private static Medida getMedida(Variavel var, String medName) {
        
        Medida retorno = null;
        
        for (Medida med : var.getMedidas()) {
            
            if (med.getDescricao().equalsIgnoreCase(medName)) {
                return med;
            }
        }
        return retorno;
    }
    
}
