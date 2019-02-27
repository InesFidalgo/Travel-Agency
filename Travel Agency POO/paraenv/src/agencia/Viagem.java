
package agencia;

import java.io.Serializable;
import java.util.*;


public class Viagem implements Serializable{
    protected String origem, destino;
    protected int codigo, duracao;
    protected double preco;
    protected Data data = new Data();
    protected ArrayList<Reserva> passageiros = new ArrayList<>();
    protected ArrayList<Utilizador> Ucancelados = new ArrayList<>();
    protected ArrayList<Autocarro> auto = new ArrayList<>();
    protected ArrayList<Cliente> lista_espera = new ArrayList<>();
    
    //done
    protected ArrayList<Avaliação> avaliaçoes = new ArrayList<>();

   
    protected Viagem(){
       
   
    }

    public void setData(Data data) {
        this.data = data;
    }
    
    
    public void setDestino(String dest){
        this.destino = dest;
    }
    
    public void setOrigem(String orig){
        this.origem= orig;
    }
    
    public void setPreco(double prec){
        this.preco=prec;
    }
    
    public void setDuracao(int dura){
        this.duracao=dura;
    }
    
    public void setCodigo(int codig){
        this.codigo=codig;
    }
    
    public String getOrigem(){
        return origem;
    }
    
    public String getDestino(){
        return destino;
    }
    
    public double getPreco(){
        return preco;
    }
    
    public int getDurancao(){
        return duracao;
    }
    public int getCodigo(){
        return codigo;
    }
    
    public ArrayList<Cliente> getLista_espera() {
        return lista_espera;
    }
    public void setLista_espera(ArrayList<Cliente> lista_espera) {
        this.lista_espera = lista_espera;
    }

    public Data getData() {
        return data;
    }

    public ArrayList<Avaliação> getAvaliaçoes() {
        return avaliaçoes;
    }

    public ArrayList<Reserva> getPassageiros() {
        return passageiros;
    }
    
    protected boolean equalsV(Viagem other){
        if(codigo == (other.getCodigo())){
            return true;
        }
        return false;
    }
    
}
