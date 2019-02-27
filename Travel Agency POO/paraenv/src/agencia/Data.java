
package agencia;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
public class Data implements Serializable{
    private int dia, mes, ano;

   
protected void Data(){
}

protected boolean verificaData(String data){
    String [] dat= data.split("/"); //separa em dd//mm//aa
    //esta merda da erro se nao se usar / e nao sei fazer essa protecçao
    
    int d = Integer.parseInt(dat[0]);//parseInt converte as Strings em Ints
    int m = Integer.parseInt(dat[1]);
    int a = Integer.parseInt(dat[2]);
    
    
    if(a>=2015){
        if(m==1|| m==3||m==5||m==7||m==8||m==10||m==12){
            if(d<=31 && d>0){
                this.dia=d;
                this.mes=m;
                this.ano=a;
                return true;
            }
            else{
                System.out.println("Dia inválido");
                return false;
            } 
        }
        else if(m==4||m==6||m==9||m==11){
            if(d<=30 && d>0){
                this.dia=d;
                this.mes=m;
                this.ano=a;
                return true;
            }
            else{
                System.out.println("Dia inválido");
                return false;
            } 
            
        }
        else if(m==2){
            if(d<=29 && d>0){
                this.dia=d;
                this.mes=m;
                this.ano=a;
                return true;
            }
            else
                System.out.println("Dia inválido");
            return false;
        }
        else{
        System.out.println("Mês inválido");
        return false;
    }
        
    }
    else{
        System.out.println("Ano inválido");
        return false;
    }

    }

protected int comparaTo() {
        
        //passar para inteiro
        
        Calendar cal = Calendar.getInstance(); //data de hoje
        int ano1 = cal.get(Calendar.YEAR);
        int mes1 = cal.get(Calendar.MONTH);
        int dia1 = cal.get(Calendar.DAY_OF_MONTH);
        
        System.out.println("dia:"+dia1+"mes"+mes1+"ano:"+ano1);
        
        if((ano == ano1) && (dia== dia1) && (mes==mes1)){ //igual
            return 0;
        }
        else if((dia>dia1) && (ano==ano1) && (mes==mes1)){ //maior
            return 1;
        }
        else if((dia<dia1) && (ano==ano1) && (mes==mes1)){ //menor
            return -1;
        }
        else if((mes>mes1) && (ano==ano1)){ //maior
            return 1;
        }
        else if((mes<mes1) && (ano==ano1)){ //menor
            return -1;
        }
        else if((ano>ano1)){ //maior
            return 1;
        }
        else if((ano < ano1)){ //menor
            return -1;
        }
        return 0;
    }


//calcula a diferenca de dias entre o hoje e o dia da viagem
protected int dataCancelamento(){
    Calendar cal = Calendar.getInstance(); //data de hoje
    String hoje="", data="";
    Date aDate, bDate;
    
    int ano1 = cal.get(Calendar.YEAR);
    int mes1 = cal.get(Calendar.MONTH);
    int dia1 = cal.get(Calendar.DAY_OF_MONTH);
    
    hoje+= Integer.toString(dia1)+"/"+Integer.toString(mes1)+"/"+Integer.toString(ano1);
    data+=dia+"/"+mes+"/"+ano;

    SimpleDateFormat formatter =  new SimpleDateFormat("dd/MM/yyyy");
    try{
        aDate = formatter.parse(hoje);
        bDate = formatter.parse(data);
        
        Calendar with = Calendar.getInstance();
        with.setTime(aDate);
        
        Calendar to = Calendar.getInstance();
        to.setTime(bDate);
        to.set(Calendar.YEAR, with.get(Calendar.YEAR));
        
        int withDAY = with.get(Calendar.DAY_OF_YEAR);
        int toDAY = to.get(Calendar.DAY_OF_YEAR);

        int diffDay = withDAY - toDAY;

        System.out.println(diffDay);

        if(diffDay>=0){
        return diffDay;
        }
        
        else
            return -1;
      }
      catch(Exception e){
            return -2;
      }
        


    
    
    
} 

    public void setDia(int dia) {
        this.dia = dia;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }
    
public String toString() {
    return ""+this.dia+"/"+this.mes+"/"+this.ano;
}
    
    
    
}