
package agencia;

import java.util.*;


public class Cliente extends Utilizador{
    private int penalizacao;
    
    protected Cliente(){
        System.out.println("Cliente:");

    }
    
    
    public int getPenalizacao(){
        return penalizacao;
    }
    
    
    
}
