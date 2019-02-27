/*
 * To change this license header, choose License Headers in Project 
Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agencia;

import java.io.Serializable;

/**
 *
 * @author utilizador
 */
public class Avaliação implements Serializable {
    private String comentario;
    private int pontuação = 0;
    private Utilizador user;
 
    
    
    public void Avaliação(){}

    public String getComentario() {
        return comentario;
    }

    public int getPontuação() {
        return pontuação;
    }

    public Utilizador getUser() {
        return user;
    }

    public void setComentario(String comentario) {
        if(comentario.equals("-")){
        }
        else
        this.comentario = comentario;
    }

    public void setPontuação(int pontuação) {
        this.pontuação = pontuação;
    }

    public void setUser(Utilizador user) {
        this.user = user;
    }
    
    
    
    
    
    
}