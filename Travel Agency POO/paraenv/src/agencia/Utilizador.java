
package agencia;
import java.io.Serializable;
import java.util.*;

public class Utilizador implements Serializable{
    private String nome, nif, morada, email, pass;
    private int telefone;
    private int tipoU;

    
Utilizador(){}
    
    
    public String getNome(){
        return nome;
    }
    
    public String getNif(){
        return nif;
    }
    public String getMorada(){
        return morada;
    }
    public String getEmail(){
        return email;
    }
    public String getPass(){
        return pass;
    }
    public int getTelefone(){
        return telefone;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setNif(String nif){
        this.nif = nif;
    }
    
    public void setMorada(String morada){
        this.morada = morada;
    }
    
    public void setPass(String pass){
        this.pass = pass;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setTelefone(int telefone){
        this.telefone = telefone;
    }

    public int getTipoU() {
        return tipoU;
    }
    
    public void setTipoU(int i){
        this.tipoU = i;
    }
    
    
    protected boolean equalsV(Utilizador other){
        if(nif.equals(other.getNif())){
            return true;
        }
        return false;
    }
    
    
    //if(u1.equals(u2))
    
}



