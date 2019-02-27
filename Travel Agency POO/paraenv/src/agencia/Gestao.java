
package agencia;

import java.io.EOFException;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;


public class Gestao implements Serializable{
    private ArrayList<Utilizador> utilizadores = new ArrayList<>();
    private ArrayList<Autocarro> autocarros = new ArrayList<>();
    private ArrayList<Reserva> reservas = new ArrayList<>();
    private ArrayList<Viagem> viagens = new ArrayList<>();
    
    
    protected Utilizador criaUtilizador(int tipo_ut) throws IOException{
        Utilizador ut;
        if(tipo_ut==1){
            
            System.out.println("Tipo de cliente: \n1 - Regular;\n2 - Premium; ");
            Scanner sc = new Scanner(System.in);
            int i=sc.nextInt();
            if(i==1){
                
                ut = new Regular();
                ut.setTipoU(2);
                
            }
            else if(i ==2){
                ut = new Premium();
                ut.setTipoU(1);
                
            }
            else{
                
                System.out.println("insira uma opcção válida");
                return null;
            }
        }
        
        else {
            
            System.out.println("yo devia haver aqui uma pass de acesso, senao toda a gente cria um administrador");
            ut = new Administrador();
            ut.setTipoU(3);
            
        }
        
        //passar isto para a main
        System.out.println("Qual é o seu nome?");
        Scanner sc = new Scanner(System.in);
        ut.setNome(sc.nextLine());
            
        
        //System.out.println(ut.getnif());
        
        do{
            System.out.println("Insira um nif válido: ");
            String a = sc.nextLine();
            ut.setNif(a);
        }
        
        while(verificarNIF(ut)==true);
        
            
        System.out.println("Qual é a sua morada?");
        ut.setMorada(sc.nextLine());
            
        System.out.println("Qual é a sua password?");
        ut.setPass(sc.nextLine());
            
        System.out.println("Qual é o seu email?");
        ut.setEmail(sc.nextLine());
         
        System.out.println("Qual é o seu nº de telefone?");
        ut.setTelefone(sc.nextInt());
        
        
        utilizadores.add(ut);

        return ut;
    }
    
    protected void criaViagem() throws IOException{
        String d;
        boolean verifica;
        Scanner sc = new Scanner(System.in);
        Viagem viagem =new Viagem();
        Data data= new Data();
   
        
        do{
        System.out.println("Insira a data desejada (dia/mes/ano)");
        d=sc.nextLine();
        
        
        verifica = data.verificaData(d);
        
        }while(verifica !=true);
        viagem.setData(data);
        
        System.out.println("Qual é a origem?");
        viagem.setOrigem(sc.nextLine());
            
        System.out.println("Qual é o destino?");
        viagem.setDestino(sc.nextLine());
            
        System.out.println("Qual é o preco?");
        viagem.setPreco(sc.nextDouble());
        
        System.out.println("Qual é a duracao da viagem?");
        viagem.setDuracao(sc.nextInt());
        
        
        Random generator = new Random();
        int codigo = generator.nextInt(1000);
        while(verificaCodigoexis(codigo)!=false){
            codigo = generator.nextInt(1000);
        }
        
        viagem.setCodigo(codigo);
        System.out.println("O código atribuido a esta viagem foi: "+codigo);
        
        System.out.println("Insira o número de autocarros para esta viagem: ");
        int nauto = sc.nextInt();
        
        if(atribuiAutocarroViagem(viagem,nauto)==true){
            viagens.add(viagem);
            System.out.println("Viagem criada com sucesso!");
        }
        else{
            System.out.println("A Viagem não pode ser criada.");
            return;       
        }

    }
    
    protected boolean atribuiAutocarroViagem(Viagem vi, int nauto){
        int i=0;
        int cnt = 0;
        if(autocarros.size()==0){
            System.out.println("Não existem autocarros.");
            return false;
        }
        for(i=0; i<autocarros.size(); i++){
            if(autocarros.get(i).getIsEmUso()==false){
                cnt++;
               }
        }
        
        if(cnt<nauto){
            System.out.println("Não há autocarros suficiente.");
            return false;
        }
        i = 0;
        while(vi.auto.size() < nauto){
            if(autocarros.get(i).getIsEmUso()==false){
                vi.auto.add(autocarros.get(i));
                autocarros.get(i).setIsEmUso(true);
               }
            else{
                i++;
           } 
        }
        return true;
    
    }
    
    protected void criaAutocarro() throws IOException{
        Autocarro autocarro = new Autocarro();;
        autocarro.atribuiValores();
        autocarros.add(autocarro);
        //System.out.println("auto array size "+autocarros.size());
        
    }    
    
        //////MUDEI ISTO AQUI DENTRO Filipa
    protected void reservarViagem(Utilizador x){
        //listarViagens();
        //listarViagenscheias();
        Viagem v = procuraViagem();
        Scanner sc= new Scanner(System.in);
        boolean verifica;
        String d;
        Data data=new Data();
        //a viagem tem vagas
        
        
        for(int i =0 ;i<v.passageiros.size();i++){
            if (x.equalsV(v.passageiros.get(i).getCliente())==true && v.passageiros.get(i).getIsActivo()==true){
                System.out.println("Já tinha feito uma reserva nesta viagem");
                return; 
            }
        }
        
        
        for(int i = 0; i<v.auto.size();i++){
        if(v.auto.get(i).getCapacidade()!=0){
            System.out.println("Pretende reservar esta viagem?\n 1 - Sim\n 2- Voltar atrás");
            Scanner n = new Scanner(System.in);
            int op= n.nextInt();
            
            ///////////////OP 1 TEM VAGAS E VAI RESERVAR
            if(op==1){
                Reserva nova = new Reserva();
                
                do{
                System.out.println("Insira a data desejada (dia/mes/ano)");
                d=sc.nextLine();
        
                
                verifica = data.verificaData(d);
                }while(verifica !=true);
                nova.setData(data);
                
                if (x.getTipoU()==1){

                    Autocarro cap = nova.verificaCap(v);
                    if(cap!=null){
                    Premium pre = (Premium) x;
                    nova.setCliente(pre); 
                    nova.setNum_reserva(v.passageiros.size()+1);
                    nova.setViagem(v);
                    nova.setPagamento(v.getPreco()-(v.getPreco() * pre.getDesconto()));
                    cap.setLugaresOcupados(cap.getLugaresOcupados()+1);
                    nova.setActivo(true);
                    v.passageiros.add(nova);
                    
                    reservas.add(nova);
                    
                    System.out.println("Está sentado no lugar: "+cap.getLugaresOcupados());
                    return;
                    }
    
                }
                else{
                    Autocarro cap = nova.verificaCap(v);
                    if(cap!=null){
                    Regular reg = (Regular) x;
                    nova.setCliente(reg);
                    nova.setViagem(v);
                    nova.setNum_reserva(v.passageiros.size()+1); 
                    nova.setPagamento(v.preco);
                    cap.setLugaresOcupados(cap.getLugaresOcupados()+1);
                    //nova.data
                    nova.setActivo(true);
                    reservas.add(nova);
                    v.passageiros.add(nova);
                    System.out.println("Está sentado no lugar: "+nova.getNum_reserva());
                    return;
  
                    }
                    
                }
            
            }
            /////////////////////////////TEM VAGAS E VAI VOLTAR ATRAS
            else if(op == 2){
               reservarViagem(x);
               
            }
            else{
                return;
                //System.out.println("Insira uma opcção válida");

                //por a voltar ao menu, alias fazer isso em todos os menus
            }
            
        }////////////////////NÃO HÁ VAGAS
        else{
            //ja não há vagas mas pode ficar em lista de espera
            System.out.println("Pretende entrar na lista de espera?\n1- Sim\n 2- Voltar atrás");
            Scanner n = new Scanner(System.in);
            int op= n.nextInt();
            if(op==1){
                Reserva nova = new Reserva();
                if (x.getTipoU() == 1){
                    Premium pre = (Premium) x;
                    nova.setCliente(pre);
                    nova.setViagem(v);
                    //nova.data
                    int lugar = nova.getViagem().lista_espera.size();
                    
                    nova.getViagem().lista_espera.add(pre);
                    
                    
                    System.out.println("Em Lista de Espera com o numero"+lugar);
                    return;
    
                }
                else{
                   
                    if( x.getTipoU()==2){
                        Regular reg = (Regular) x;
                        nova.setCliente(reg); 
                        nova.setViagem(v);
                        //nova.data
                        int lugar = nova.getViagem().lista_espera.size();
                        nova.getViagem().lista_espera.add(reg);
                        System.out.println("Em Lista de Espera com o numero"+lugar);
                        return;
  
                    }
                    
            
                }
                
            }
     
            }
            
        }
    }
    
    protected void minhasReservas(Utilizador user){
        //System.out.println("NIF ORIGINAL"+user.getNif());
        int cnt = 0;
        
        ArrayList<Viagem> encontradas = new ArrayList();
       
        for(int i=0; i<viagens.size();i++){
            for(int j=0;j<viagens.get(i).passageiros.size();j++){
            //System.out.println(reservas.get(i).getCliente().getNif());
                
            if (viagens.get(i).passageiros.get(j).getCliente().equalsV(user)){
                
                cnt=0;
                for( int k = 0;k<encontradas.size();k++){
                    if(viagens.get(i).getCodigo()== encontradas.get(k).getCodigo()){
                //Para controlar a diferença de id's internos sempre que lê o ficheiro
                //se ja tiver impresso a reserva daquele utilizador mas que tem o mesmo id interno
                //if(((lasti == null) || (lasti.equalsV(user)!= true))){
                        cnt++;
                        }
                    
                }
                if(cnt==0){
                System.out.println("Número de reserva: "+viagens.get(i).passageiros.get(j).getNum_reserva()+"\nOrigem: "+viagens.get(i).passageiros.get(j).getViagem().origem+"\nDestino: "+viagens.get(i).passageiros.get(j).getViagem().destino+"\nData da viagem: "+viagens.get(i).passageiros.get(j).getViagem().data);
                encontradas.add(viagens.get(i).passageiros.get(j).getViagem());

                }
                }
            }
       }
    }
    

    protected void alteraAutocarro(Autocarro x, int n){
        Scanner op = new Scanner(System.in);
        if(n==1){
            System.out.println("Insira a matricula desejada: ");
            String a = op.nextLine();
            x.setMatricula(a);
        }
        else{
            System.out.println("Inisra a capacidade: ");
            int b = op.nextInt();
            x.setCapacidade(b);
        }
        
    }
    
    protected void alteraViagem(Viagem x, int n){
        Scanner op = new Scanner(System.in);
        if(n==1){
            System.out.println("Insira a origem que pretendida: ");
            String a = op.nextLine();
            x.setOrigem(a);
        }
        
        if(n==2){
            System.out.println("Insira o destino que deseja: ");
            String a = op.nextLine();
            x.setDestino(a); 
        }
        
        if(n==3){
            System.out.println("Insira o preço pretendido: ");
            double b = op.nextDouble();
            x.setPreco(b);
        }
        if(n==4){
            System.out.println("Insira a duração pretendido: ");
            int b = op.nextInt();
            x.setDuracao(b);
        }
        
    }
        
    protected void alteraCliente(Utilizador x, int n){
        int b;
        String a;
        Scanner op = new Scanner(System.in);
        
        if (n==1){
            System.out.println("Insira o nome desejado: ");
            a = op.nextLine();
            System.out.println(a);
            x.setNome(a);
            
            }
        if (n==2){
            //pimenta 
            //verificar se o nif não é usado em mais lado nenhum ou não se altera
            //verificar quando se cria utilizador que o nif ainda não existe
              do{
            System.out.println("Insira um nif válido: ");
            a = op.nextLine();
            x.setNif(a);
        }
        
        while(verificarNIF(x)==true);

        }
        if (n==3){
            System.out.println("Insira a morada desejada: ");
            a = op.nextLine();
            x.setMorada (a);
                
        }
        if (n==4){
            System.out.println("Insira a password desejada: ");
            a = op.nextLine();
            
            x.setPass(a);   
                }
        if (n==3){
            System.out.println("Insira o email desejado: ");
            a = op.nextLine();
            x.setEmail(a);
        }    
        if (n==4){
            System.out.println("Insira o telefone desejado: ");
            b = op.nextInt();
            x.setTelefone(b);
                  
        }
          
    }

    

    

    
    //procura viagem especifica
    protected Viagem consultarViagens(){
        System.out.println("Insira o codigo da viagem que pretende consultar:");
        Scanner a = new Scanner(System.in);
        int cod = a.nextInt();
        for(int i =0 ;i<viagens.size();i++){
            if(viagens.get(i).codigo == cod){
                return viagens.get(i);
            }
        }
        System.out.println("Esta viagem não existe!");
        return null;
    }
    
    //lista todas as viagens ainda disponiveis
    protected void listarViagens(){
        int vagos = 0;
        System.out.println("Tem "+viagens.size()+" viagens disponives");
        System.out.println("(Listar viagens)\n ");
        
        for (int i =0;i<viagens.size();i++){
            Viagem a = viagens.get(i);
            int n = a.auto.size() - 1;
            
            while(n>=0){
                //System.out.println((a.auto.get(n).capacidade - a.auto.get(n).lugaresOcupados));
                //CICLO INFINITO NÃO PASSA DAQUI
                
                if((a.auto.get(n).getCapacidade() - a.auto.get(n).getLugaresOcupados()) > 0){
                    
                    vagos+=a.auto.get(n).getCapacidade() - a.auto.get(n).getLugaresOcupados();
                    System.out.println("Número de Autocarro: "+n);
                    System.out.println("Lugares Vagos: "+(a.auto.get(n).getCapacidade() - a.auto.get(n).getLugaresOcupados()));
                    
                } 
                n--;
            
            }
            
            if(vagos>0) {
                System.out.println("Código: "+viagens.get(i).codigo);
                System.out.println("Origem: "+viagens.get(i).origem);
                System.out.println("Destino: "+viagens.get(i).destino);
                System.out.println("Duração: "+viagens.get(i).duracao);
                System.out.println("Preço: "+viagens.get(i).preco);
                System.out.println("Data da Viagem: "+viagens.get(i).data);
            }
        }
        
    }
   
    protected void listarViagenscheias(){
       int vagos = 0;
       System.out.println("Viagens só com reserva:");
       for (int i=0;i<viagens.size();i++){
           
            Viagem a = viagens.get(i);
            int n = a.auto.size() - 1;
            
            while(n>=0){
                if((a.auto.get(n).getCapacidade() - a.auto.get(n).getLugaresOcupados()) > 0){
                   vagos++;
                   
                } 
                n--;
            }
            
            if(vagos==0){
                System.out.println("codigo: "+viagens.get(i).codigo);
                System.out.println("origem: "+viagens.get(i).origem);
                System.out.println("destino: "+viagens.get(i).destino);
                System.out.println("duração: "+viagens.get(i).duracao);
                System.out.println("preço: "+viagens.get(i).preco);
                System.out.println("Data da Viagem: "+viagens.get(i).data);
            }
       }
   }
    
   
    protected void listarBus(){
        System.out.println("Existem "+autocarros.size()+" autocarros.");
        for(int i=0;i<autocarros.size();i++){
            System.out.println("matricula: "+autocarros.get(i).getMatricula());
            System.out.println("capacidade: "+autocarros.get(i).getCapacidade());
            System.out.println("Lugares Ocupados: "+autocarros.get(i).getLugaresOcupados());
        }
    }
    
    protected int listarUsers(){
        System.out.println("(Já adicionou e estou a listar users)\n ");
        for (int i =0;i<utilizadores.size();i++){
            
            System.out.println("nome: "+utilizadores.get(i).getNome());
            System.out.println("nif: "+utilizadores.get(i).getNif());
            System.out.println("telefone: "+utilizadores.get(i).getTelefone());
            System.out.println("morada: "+utilizadores.get(i).getMorada());
            System.out.println("pass: "+utilizadores.get(i).getPass());
            
        }
        return 0;
    }
    
    protected void listarReservas(){
        System.out.println("Existem "+reservas.size()+"viagens.");
        for(int i=0; i< reservas.size();i++){
            System.out.println("Número da reserva: "+reservas.get(i).getNum_reserva());
            System.out.println("Nome do cliente: "+reservas.get(i).getCliente().getNome());
            System.out.println("Data da viagem: "+reservas.get(i).getViagem().data.getDia()+"/"+reservas.get(i).getViagem().data.getMes()+"/"+reservas.get(i).getViagem().data.getAno());
            System.out.println("Pagamento: "+reservas.get(i).getPagamento());
            
        }
    }
    
    
    
    protected Utilizador procuraUser(){
        System.out.println("Procura por nif: ");
        Scanner n = new Scanner(System.in);
        String nif = n.nextLine();
        for(int i =0;i<utilizadores.size();i++){
            if(utilizadores.get(i).getNif().equals(nif)){
                return utilizadores.get(i);
            }
        }
        System.out.println("Esse utilizador não existe!");
        return null;
        
    }  
    
    protected Utilizador procuraUsernome(String nom){
        for(int i =0;i<utilizadores.size();i++){
            if(utilizadores.get(i).getNome().equals(nom)){
                return utilizadores.get(i);
                
            }
        }
        return null;
    }
    
    protected Viagem procuraViagem(){
        System.out.println("Insira o código da viagem: ");
        Scanner n = new Scanner(System.in);
        int cod_viagem= n.nextInt();
        for(int i =0;i<viagens.size();i++){
            if(viagens.get(i).codigo ==cod_viagem){
                return viagens.get(i);
            }
        }
        return null;
        
    }
    
    protected Autocarro procuraAutocarro(){
        System.out.println("insira a matricula do autocarro");
        Scanner n = new Scanner(System.in);
        String matri= n.nextLine();
        for(int i =0;i<autocarros.size();i++){
            if(autocarros.get(i).getMatricula().equals(matri)){
                return autocarros.get(i);
            }
        }
        return null;
    }
    
    protected Reserva procuraReserva(Viagem v){
        for(int i = 0; i<v.passageiros.size();i++){
            if(v.passageiros.get(i).getViagem().getCodigo()==v.getCodigo()){
                return v.passageiros.get(i);
            }
        }
        
        System.out.println("Essa reserva não existe.");
        return null;
    }
    
   
    
    
    
      protected void atribuirPontVi(Viagem a, Utilizador x){
        
        Avaliação nova = new Avaliação();
        Scanner op = new Scanner(System.in);
        Scanner op2 = new Scanner(System.in);
        
        int cnt = 0;
        System.out.println("Numero de passageros"+a.getPassageiros().size());

        for(int i=0;i<a.getPassageiros().size();i++){
            //verificar se a viagem ja ocoreu, verificar sempre a data de hoje a partir do pc? ohoho /done
                    System.out.println("__________\nCliente nesta viagem:"+a.getPassageiros().get(i).getCliente().getNome());
            if(x.equalsV(a.getPassageiros().get(i).getCliente())){
                
                cnt++;
            }
            for(Avaliação avalia :a.getAvaliaçoes()){
                if(x.equalsV(avalia.getUser())){
                System.out.println("Já atribuiu pontuação a esta viagem.");
                return;
            }
            }
                        
            if(cnt==1){
                int n = a.getData().comparaTo();
                System.out.println("N DATA:"+n);
                if(n==-1||n==0){
                    System.out.println("Insira a sua pontuação relativa a esta viagem: (1-5) ");
                    
                    int pontos = op.nextInt();
                    while((pontos>5) && (pontos<1)){
                        System.out.println("Insira a sua pontuação válida relativa a esta viagem: (1-5)");
                        pontos = op.nextInt();
                    }
                    nova.setPontuação(pontos);
                    System.out.println("Um comentário acerca da viagem? (Se não quiser escrever nada insira - )");
                    String os = op2.nextLine();
                    nova.setComentario(os); 
                    nova.setUser(x); 
                    
                    a.getAvaliaçoes().add(nova);
  
                }
                else{
                    System.out.println("Ainda não pode atribuir avaliação");
                    return;
                }
  
            }

            
            else{
                System.out.println("Não foi nesta viajem.");
            }
        }
        
    }
    

    
    protected int verificaPass(String passi,Utilizador x){
        if(x.getPass().equals(passi)){
            //System.out.println("encontrou a pass: "+x.pass +" escreveste isto: " +passi);
            return 1;
        }
        else{
            //System.out.println("escreveste isto:" +passi);
            return 0;
        }

}
    
    protected boolean verificaCodigoexis(int codig){
        int i;
        for(i=0;i<viagens.size();i++){
            if(viagens.get(i).codigo == codig){
                return true;
            }
        }
        return false;
    }
    
    protected boolean verificarNIF(Utilizador a){
        for(int i =0;i< utilizadores.size();i++){
        if(a.equalsV(utilizadores.get(i)) ){
            System.out.println("esse nif já existe");
            return true;
            }
        }
        return false;
    }
    

        
    
    
   
    protected void eliminaViagem(Viagem viagem){
        
        for(int i =0;i<viagens.size();i++){
            if (viagens.get(i).getCodigo() == viagem.codigo){
                 viagens.remove(i);
            }
        }
        System.out.println("Viagem eliminada com sucesso.");
    }
    
    protected void cancelaReserva(Reserva reserva, Utilizador utilizador){
        int difDias = reserva.getViagem().data.dataCancelamento();
        
        System.out.println("DIFERENÇA DE DIAS:"+difDias);
        
        //pus maior igual nos dois, se for menor não há reembolso -----------> dizer à filipa
        if(utilizador.getTipoU()==1 && difDias>=2){
           //cancelar premium
           //PAGAMENTO É O VALOR QUE NÃO FOI DEVOLVIDO
            //cancelar regular, se for 7 dias 
            reserva.setPagamento(reserva.getViagem().preco);  //
            reserva.getViagem().Ucancelados.add(utilizador);
            reserva.setActivo(false);
            for(int i =0;i<reserva.getViagem().passageiros.size();i++){
                System.out.println(reserva.getViagem().passageiros.get(i).getCliente().getNif());
                System.out.println(utilizador.getNif());
                if (utilizador.equalsV(reserva.getViagem().passageiros.get(i).getCliente()) ){
                    System.out.println("cancelou");
                     reserva.getViagem().passageiros.remove(i);
                }
            }
            System.out.println("Reserva eliminada com sucesso.");
           
           //reservas.remove(reserva);
           
        }
        else if(utilizador.getTipoU()==2&& difDias>=7){
            //cancelar regular, se for 7 dias 
            reserva.setPagamento(reserva.getViagem().preco *0.5);  //pagam metade da viagem 
            reserva.getViagem().Ucancelados.add(utilizador);
            reserva.setActivo(false);
            for(int i =0;i<reserva.getViagem().passageiros.size();i++){
                System.out.println(reserva.getViagem().passageiros.get(i).getCliente().getNif());
                System.out.println(utilizador.getNif());
                if (utilizador.equalsV(reserva.getViagem().passageiros.get(i).getCliente()) ){
                    System.out.println("cancelou");
                     reserva.getViagem().passageiros.remove(i);
                }
            }
            System.out.println("Reserva eliminada com sucesso.");
           
            
            
            //reservas.remove(reserva);

        }
        else{
            //reserva.pagamento fica igual, ou seja tem penalização total, ou seja, pagamento fica igual
            reserva.getViagem().Ucancelados.add(utilizador);
            reserva.setActivo(false);
            for(int i =0;i<reserva.getViagem().passageiros.size();i++){
                System.out.println(reserva.getViagem().passageiros.get(i).getCliente().getNif());
                System.out.println(utilizador.getNif());
                if (utilizador.equalsV(reserva.getViagem().passageiros.get(i).getCliente()) ){
                    System.out.println("cancelou");
                     reserva.getViagem().passageiros.remove(i);
                }
            }
            System.out.println("Reserva eliminada com sucesso.");
        }
    }
    
    protected void eliminaAutocarro(Autocarro auto){
        for(int i=0; i< viagens.size(); i++){
            for(int j =0;j<viagens.get(i).auto.size();j++){
                if(viagens.get(i).auto.get(j)==auto){
                    System.out.println("Este autocarro está requisitado para uma viagem.\nNão pode ser eliminado.");
                    return;
                 }
            }
        }
        for(int i =0;i<autocarros.size();i++){
            if (autocarros.get(i).getMatricula().equals(auto.getMatricula())){
                 autocarros.remove(i);
            }
        }
        System.out.println("Autocarro eliminada com sucesso.");
        
    }
    
   
protected boolean isinEspera(Utilizador user,Viagem v){
        
    
        
            for(int j= 0; j<v.lista_espera.size(); j++){
            //se estiver na lista de espera de uma viagem
            if(v.lista_espera.get(j).equalsV(user))
                return true;
            }
        
        return false;
    }
    
    protected Viagem verificaLespera(){
        for(int i = 0;i<reservas.size();i++){
            for(int k=0; k<reservas.get(i).getViagem().auto.size();k++){
                    if(reservas.get(i).getViagem().auto.get(k).getLugaresOcupados()< reservas.get(i).getViagem().auto.get(k).getCapacidade()){
                        if(reservas.get(i).getViagem().lista_espera.size()>0){
                            return reservas.get(i).getViagem();
                        }
                    }
                }
        }
    
        return null;
    }
    
    
    
      
    protected Viagem melhorPontuacao(int mes){
        int compara=0;
        int pontTotal;
        Viagem viagem=null;
        for(int i=0; i<viagens.size();i++){
            if(viagens.get(i).data.getMes()==mes){
                pontTotal=0;
                for(int j=0; j<viagens.get(i).avaliaçoes.size();j++){
                    pontTotal+=viagens.get(i).avaliaçoes.get(j).getPontuação();
                }
                if(pontTotal>compara){
                    viagem=viagens.get(i);
                    compara=pontTotal;
                }
            }
            
        }
        return viagem;
        
    }

   
    
    
   
    protected void leFichUsers()throws IOException, ClassNotFoundException{
        Utilizador utilizador;
        FicheirosDeObjectos f1= new FicheirosDeObjectos();
        
        //int y=0;
        
        if(f1.abreLeitura("Users.dat")==true){
            try{
            while(true) {
            utilizador = (Utilizador) f1.leObjecto();
            if(utilizador==null){
                System.out.println("O ficheiro Users.dat está vazio");
                f1.fechaLeitura();
                break;
            }
            else{
                utilizadores.add(utilizador);
                }
            }
        }catch(EOFException e){
            
        }
        }
        
        else{
            System.out.println("O ficheiro Users.dat não existe");
            f1.fechaLeitura();
        }
    
            
          
    }
    
    protected void leFichViagens() throws IOException, ClassNotFoundException{
        FicheirosDeObjectos f2= new FicheirosDeObjectos();
        Viagem viagem;
      
        if(f2.abreLeitura("Viagens.dat")==true){
            try{
            while(true) {
            viagem = (Viagem) f2.leObjecto();
            if(viagem==null){
                System.out.println("O ficheiro Viagens.dat está vazio");
                f2.fechaLeitura();
                break;
            }
            else{
                viagens.add(viagem);
                }
            }
        }catch(EOFException e){
            
        }
        }
        
        else{
            System.out.println("O ficheiro Viagens.dat não existe");
            f2.fechaLeitura();
        }
        
        
       
    }
    protected void leFichRese() throws IOException, ClassNotFoundException{
        FicheirosDeObjectos f5= new FicheirosDeObjectos();
        Reserva reserva;
        ArrayList<Reserva> encontradas = new ArrayList();
        
        if(f5.abreLeitura("Reservas.dat")==true){
            try{
            while(true) {
            reserva = (Reserva) f5.leObjecto();
            
            if(reserva==null){
                System.out.println("O ficheiro Reservas.dat está vazio");
                f5.fechaLeitura();
                break;
            }
            boolean criadas=false;
            
            for(int i = 0 ;i<encontradas.size();i++){
                if(reserva.equalsV(encontradas.get(i))){
                    criadas = true;
                    
                }
                
            }
            if(criadas==false){
                reservas.add(reserva);
            }

            }
        }catch(EOFException e){
            
        }
        }
        
        else{
            System.out.println("O ficheiro Reservas.dat não existe");
            f5.fechaLeitura();
        }
        
        
       
    }
    
    protected void leFichAutoc() throws IOException, ClassNotFoundException{
        Autocarro autocarro;
        FicheirosDeObjectos f3= new FicheirosDeObjectos();
        if(f3.abreLeitura("Autocarros.dat")==true){
            try{
            while(true) {
            autocarro = (Autocarro) f3.leObjecto();
            if(autocarro==null){
                System.out.println("O ficheiro Autocarros.dat está vazio");
                f3.fechaLeitura();
                break;
            }
            else{
                autocarros.add(autocarro);
                }
            }
        }catch(EOFException e){
            
        }
        }
        
        else{
            System.out.println("O ficheiro Autocarros.dat não existe");
            f3.fechaLeitura();
        }
    }
    
    protected void leFichReservas() throws IOException, ClassNotFoundException{
        FicheirosDeObjectos f4= new FicheirosDeObjectos();
        Reserva reserva;
        if(f4.abreLeitura("Reservas.dat")==true){
            try{
            while(true) {
            reserva = (Reserva) f4.leObjecto();
            if(reserva==null){
                System.out.println("O ficheiro Reservas.dat está vazio");
                f4.fechaLeitura();
                break;
            }
            else{
                reservas.add(reserva);
                }
            }
        }catch(EOFException e){
            
        }
        }
        
        else{
            System.out.println("O ficheiro Reservas.dat não existe");
            f4.fechaLeitura();
        }
    }
    
    protected void actualizaFicheiros() throws IOException {
        FicheirosDeObjectos f1 = new FicheirosDeObjectos();
        FicheirosDeObjectos f2 = new FicheirosDeObjectos();
        FicheirosDeObjectos f3 = new FicheirosDeObjectos();
        FicheirosDeObjectos f4 = new FicheirosDeObjectos();
        if(utilizadores.isEmpty()==false){
            
            f1.abreEscrita("Users.dat");
            for(int i=0; i<utilizadores.size();i++){
                f1.escreveObjecto(utilizadores.get(i));
            }
            f1.fechaEscrita();
            
        }
         if(viagens.isEmpty()==false){
            f2.abreEscrita("Viagens.dat");
            for(int i=0; i<viagens.size();i++){
                f2.escreveObjecto(viagens.get(i));
            }
            f2.fechaEscrita();  
        }
        
         if(autocarros.isEmpty()==false){
            f3.abreEscrita("Autocarros.dat");
            for(int i=0; i<autocarros.size();i++){
                f3.escreveObjecto(autocarros.get(i));
            }
            f3.fechaEscrita();  
        }
        
        if(reservas.isEmpty()==false){
            f4.abreEscrita("Reservas.dat");
            for(int i=0; i<reservas.size();i++){
                f4.escreveObjecto(reservas.get(i));
            }
            f4.fechaEscrita();  
        }
        
    }
        

    //Ponto 1
    //return null se nao se venderam viagens nesse mes
    protected Viagem maisVendida(int mes){
        int contador=0;
        int compara=0;
        Viagem viagem;
        Data data;
        Viagem maisVendida=null;
        for(int i=0; i<reservas.size();i++){
            data=reservas.get(i).getData();
            if(data.getMes()== mes){
                viagem=reservas.get(i).getViagem();
                for(int j=0; j<reservas.size();j++){
                    if(reservas.get(j).getViagem()==viagem){
                        contador+=1;
                    }
                }
                if(contador>compara){
                    compara = contador;
                    maisVendida=viagem;
                }
                
            }
        }
        return maisVendida;
               
    }
    //Ponto 3
    protected void listaViagensSemReserva(int mes){
        Viagem viagem;
        ArrayList<Viagem> semReserva = viagens;
        for(int i=0; i<viagens.size();i++){
            viagem=viagens.get(i);
            for(int j=0; j<reservas.size();j++){
                if(reservas.get(j).getData().getMes()==mes && reservas.get(j).getViagem() == viagem){
                       semReserva.remove(viagem);
                }
            }
        }
        System.out.println("Lista de viagens sem reserva no mês:"+mes );
        for(Viagem viagen : semReserva) {
            
            System.out.println("codigo: " + viagen.codigo);
            System.out.println("origem: " + viagen.origem);
            System.out.println("destino: " + viagen.destino);
            System.out.println("duração: " + viagen.duracao);
            System.out.println("preço: " + viagen.preco);
            
        }
        if(semReserva.isEmpty()){
            System.out.println("Não há viagens sem reserva nesse mês.");
        }
        
        
    }
    //Ponto 5
    protected void listaReservasCanceladas(Viagem viagem){
        boolean verifica=false;
        System.out.println("Lista de reservas canceladas");
        for(Reserva reserva : reservas){
            if(reserva.getIsActivo()==false){
                System.out.println("Numero da reserva:"+reserva.getNum_reserva());
                System.out.println("Nome do cliente:" + reserva.getCliente().getNome());
                verifica=true;
            }
        }
        if(verifica==false){
            System.out.println("Nunca cancelaram uma reserva.");
        }
    }
    //Ponto 2
    //return null se não se compraram viagens nesse mes
    protected Cliente comprouMais(int mes){
        Cliente comprador= null;
        int contador=0;
        int compara=0;
        
        for(Utilizador utilizador: utilizadores){
            if(utilizador.getTipoU()!=3){
                for(Reserva reserva:reservas){
                    if(reserva.getData().getMes()==mes && reserva.getCliente().getNif().equals(utilizador.getNif())){
                        contador++;
                    }
                }
            if(contador>compara){
                comprador=(Cliente)utilizador;
            }
                
            }
        }
        return comprador;
    }
    //Ponto 4
    protected void listarReservasViagem(Viagem viagem){
        boolean existe=false;
        int cont=0;
        ArrayList<Reserva> encontradas = new ArrayList();
        System.out.println("Lista de reservas da viagem.");
        for(Reserva reserva : reservas){
            if(reserva.getViagem().getCodigo()==viagem.getCodigo()){
                encontradas.add(reserva);
                
                System.out.println("Numero da reserva: "+reserva.getNum_reserva());
                System.out.println("Nome do cliente: " + reserva.getCliente().getNome());
                existe=true;
            }
        }
        if(existe==false){
            System.out.println("Não foram feitas reservas a esta viagem.");
        }
    }
    //Ponto 6
    protected void listarClientesEspera(){
        boolean existe=false;
        System.out.println("Lista de Viagens com clientes em espera.");
        for(Viagem v : viagens){
            if(v.lista_espera.size()>0){
                for(Cliente cliente : v.lista_espera){
                    System.out.println("Nome de cliente: "+cliente.getNome());
                    System.out.println("Nif do cliente: "+cliente.getNif());
                    existe=true;
                }
            }
        }
        if(existe==false){
            System.out.println("Não há reservas com clientes em espera.");
        }
    }
    //Ponto 8   

    protected void estatisticaAnual(int ano){
          ArrayList<Reserva> reservaAnual= new ArrayList<>();
          int m1=0,m2=0,m3=0,m4=0,m5=0,m6=0,m7=0,m8=0,m9=0,m10=0,m11=0,m12=0;    
          Data data=null;
          int contador=0, venda=0;
          for(Reserva reserva: reservas){
              if(reserva.getIsActivo()==true && reserva.getData().getAno()==ano){
                  reservaAnual.add(reserva);
              }
          }
          if(reservaAnual.isEmpty()&& data==null){
              System.out.println("Não foram feitas reservas nesse ano.");
              return;
          }

          else{
          for(Reserva reservaMensal :reservaAnual){
              switch(reservaMensal.getData().getMes()){
                  case(1):
                      m1+=reservaMensal.getPagamento();
                  case(2):
                      m2+=reservaMensal.getPagamento();
                  case(3):
                      m3+=reservaMensal.getPagamento();
                  case(4):
                      m4+=reservaMensal.getPagamento();
                  case(5):
                      m5+=reservaMensal.getPagamento();
                  case(6):
                      m6+=reservaMensal.getPagamento();
                  case(7):
                      m7+=reservaMensal.getPagamento();
                  case(8):
                      m8+=reservaMensal.getPagamento();
                  case(9):
                      m9+=reservaMensal.getPagamento();
                  case(10):
                      m10+=reservaMensal.getPagamento();
                  case(11):
                      m11+=reservaMensal.getPagamento();
                  case(12):
                      m12+=reservaMensal.getPagamento();       
              }
          }
          System.out.println("Volume de vendas mensal:");
          System.out.println("Janeiro: "+m1);
          System.out.println("Fevereiro: "+m2);
          System.out.println("Março: "+m3);
          System.out.println("Abril: "+m4);
          System.out.println("Maio: "+m5);
          System.out.println("Junho: "+m6);
          System.out.println("Julho: "+m7);
          System.out.println("Agosto: "+m8);
          System.out.println("Setembro: "+m9);
          System.out.println("Outubro: "+m10);
          System.out.println("Novembro: "+m11);
          System.out.println("Dezembro: "+m12);

          for(Reserva reserva :reservaAnual){
              for(Reserva reservaComp :reservaAnual){
                  if(reservaComp.getData().getDia()==reserva.getData().getDia() && reservaComp.getData().getMes()==reserva.getData().getMes()){
                      venda++;
                  }
                  if(venda>contador){
                      contador=venda;
                      data = reserva.getData();
                  }
              }
          }

          System.out.println("O dia do ano "+ano+"em que houve mais vendas foi a "+data.getDia()+"do mes "+data.getMes());


          } 
      } 

    public ArrayList<Utilizador> getUtilizadores() {
        return utilizadores;
    }

    public ArrayList<Autocarro> getAutocarros() {
        return autocarros;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public ArrayList<Viagem> getViagens() {
        return viagens;
    }
    
    
    
    
    
    }
    

    
    
    
    


        
    
    
    
    

