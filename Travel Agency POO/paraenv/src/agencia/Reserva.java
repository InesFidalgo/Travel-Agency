
package agencia;

import java.io.Serializable;
import java.util.*;
public class Reserva implements Serializable{
    private int num_reserva;
    private double pagamento;
    private Utilizador cliente;
    private Viagem viagem;
    private Data data;
    private boolean activo; //false cancelar

    public Reserva(){}


    public Data getData() {
        return data;
    }

    public boolean getIsActivo() {
        return activo;
    }

    

    public void setData(Data data) {
        this.data = data;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    

    
    public int getNum_reserva() {
        return num_reserva;
    }

    public double getPagamento() {
        return pagamento;
    }

    public Utilizador getCliente() {
        return cliente;
    }

    public Viagem getViagem() {
        return viagem;
    }


    
    public void setNum_reserva(int num_reserva) {
        this.num_reserva = num_reserva;
    }

    public void setPagamento(double pagamento) {
        this.pagamento = pagamento;
    }

    public void setCliente(Utilizador cliente) {
        this.cliente = cliente;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    
    public Autocarro verificaCap(Viagem ao){
        for(int i=0;i<ao.auto.size();i++){
            if(ao.auto.get(i).getLugaresOcupados() < ao.auto.get(i).getCapacidade()){
                int n =ao.auto.get(i).getLugaresOcupados();
                ao.auto.get(i).setLugaresOcupados(n);
                return ao.auto.get(i);
            }
    
        }
        return null;
    }
    
    
    protected boolean equalsV(Reserva x){
        if(num_reserva == (x.getNum_reserva())){
            return true;
        }
        return false;
    }
    
    /*public String toString(){
        return "numero reserva"+num_reserva+"pagamento: "+pagamento+"Utilizador:"+cliente.getNif()+"viagem"+viagem.codigo;
    }*/

 
    
    
            
    
}
