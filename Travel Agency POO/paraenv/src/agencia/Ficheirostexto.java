/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author utilizador
 */
public class Ficheirostexto {

    
    protected void Ficheirostexto(){}
    
    private BufferedReader fR;
    private BufferedWriter fW;


    public void abreLeitura(String nomeDoFicheiro) throws IOException
    {
        fR = new BufferedReader(new
        FileReader(nomeDoFicheiro));
    }
    public void abreEscrita(String nomeDoFicheiro) throws IOException
    {
        fW = new BufferedWriter(new FileWriter(nomeDoFicheiro));
    } 
    public String leLinha() throws IOException
    {
        return fR.readLine();
    } 

    public int[] leNumeroInt() throws IOException {
        int[] result = new int[2];
        String st = fR.readLine();
        if (st != null) {
            result[0] = 0;
            result[1] = Integer.parseInt(st);
        } else {
            result[0] = -1;}
            return result;
        } 

    public void escreveLinha(String linha) throws IOException
    {
        fW.write(linha,0,linha.length());
        fW.newLine();
    } 

    public void escreveNumero(int num) throws IOException
    {
        String st = "";
        st = st.valueOf(num);
        escreveLinha(st);
    } 

    public void fechaLeitura() throws IOException {
        fR.close();
    }
    
    public void fechaEscrita() throws IOException {
        fW.close();
    } 
    
    //falta fazer isto
    boolean exists() {
        return false;
        //verificar se ficheiro existe
        
    }
    
    


}
