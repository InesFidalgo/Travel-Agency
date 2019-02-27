
package agencia;

import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;


public class Autocarro implements Serializable{
    private String matricula;
    private int capacidade;
    private int lugaresOcupados=0;
    private boolean emUso=false;
    

    
    protected void Autocarro(){}
    
    public String getMatricula(){
        return matricula;
    }
    
    
    public int getCapacidade(){
        return capacidade;
    }
    
    public void setMatricula(String matricula){
        this.matricula = matricula;
    }
    
    public void setCapacidade(int capacidade){
        this.capacidade = capacidade;
    }
    public void setIsEmUso(boolean val){
        this.emUso = val;
    }

    public int getLugaresOcupados() {
        return lugaresOcupados;
    }

    public boolean getIsEmUso() {
        return emUso;
    }

    public void setLugaresOcupados(int lugaresOcupados) {
        this.lugaresOcupados = lugaresOcupados;
    }

    public void setEmUso(boolean emUso) {
        this.emUso = emUso;
    }

    

    protected void atribuiValores(){
        
        System.out.println("Insira a capacidade do autocarro:");
        Scanner sc = new Scanner(System.in);
        int op = sc.nextInt();
        setCapacidade(op);
        System.out.println("Capacidade do autocarro: "+capacidade);
        this.matricula=geraMatricula();
        System.out.println("Matricula do autocarro: "+matricula);

    }



    private String geraMatricula(){

        String prematricula="";
        Random r = new Random();
        String alfabeto ="abcdefghijklmnopqrstuvwxyz";

        for (int i=0; i<2;i++){
        prematricula+= alfabeto.charAt(r.nextInt(alfabeto.length()));
        }

        prematricula+= ""+(r.nextInt(100)+1);

        for (int i=0; i<2;i++){
        prematricula+= alfabeto.charAt(r.nextInt(alfabeto.length()));
        }

        return prematricula;
    }
}




