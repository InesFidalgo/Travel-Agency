package agencia;

import java.io.IOException;
import java.util.*;

public class Agencia {


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        
        
        Autocarro autocarro;
        Utilizador user;
        Viagem viagem;
        
        
        Gestao gestao = new Gestao();
        //le ficheiro de users e carrega no array
        
        gestao.leFichUsers();
        
        //le ficheiro de users e carrega no array
        gestao.leFichViagens();
        gestao.leFichRese();
        //le ficheiro de users e carrega no array
        gestao.leFichAutoc();
        
        //le ficheiro de users e carrega no array
        gestao.leFichReservas();

        for(int y=0; y<gestao.getUtilizadores().size();y++){
            System.out.println("tipo: "+gestao.getUtilizadores().get(y).getTipoU()+"\nID: "+y+ "     nome: "+gestao.getUtilizadores().get(y).getNome());
        }
        
        for(int y=0; y<gestao.getAutocarros().size();y++){
            System.out.println("matricula autocarro: "+gestao.getAutocarros().get(y).getMatricula());
        }
        
        for(int y=0; y<gestao.getViagens().size();y++){
            System.out.println("codigo viagem: "+gestao.getViagens().get(y).codigo);
        }
        
        /*for(int y= 0; y<gestao.getReservas().size();y++){
            System.out.println(""+gestao.getReservas().get(y));
        }*/
        
        int op,i,modo,resp;
        String nome, passi;
        gestao.listarUsers();
        
        System.out.println("----------------------");
        gestao.listarViagens();
        
        
        System.out.println("\n\n--------Melhor Agencia de Sempre---------\n\n");
        System.out.println("1- Sign in\n2- Sign up\n\n");
        Scanner sc = new Scanner(System.in);
        op = sc.nextInt();
        
        if (op==1){
            System.out.println("Nome de Utilizador: ");
            Scanner a = new Scanner(System.in);
            nome = a.nextLine();
            while((user = gestao.procuraUsernome(nome))==null){
                System.out.println("Esse utilizador não existe!");
                System.out.println("Nome de Utilizador: ");
                nome = a.nextLine();  
            }
            
            System.out.println("Insira a sua password: (insira 0 se quiser sair) ");
            passi = a.nextLine();
            
            ////DONE
            int j=3;
            while(j>=0 && passi.equals("0")==false){
           
            if((gestao.verificaPass(passi,user)!=1)){
                if(j>0){
                    System.out.println("Password errada.\nTentativas restantes: "+j);
                    System.out.println("Insira a sua password: (insira 0 se quiser sair) ");
                    passi = a.nextLine();
                    j--;
                }
                
                else if(j==0){
                    System.out.println("Ja esgotou as tentativas que tinha.");
                    j--;
                }
            } 
            
            
            
            
            //não existe o utilizador
            if(user==null){
                System.out.println("Esse Utilizador não existe!!");
                return;
            } 
            //não acerta na password
            if (passi.equals("0")){
                return;
            }
            
                
            if(gestao.verificaPass(passi,user)==1){
                
            //tem de verificar se é cliente ou administrador automaticamente  -> done
                //premium                //regular
                if((user.getTipoU() == 1) || (user.getTipoU() == 2)){
                   
                    /* para notificao
                    if((gestao.verificaLespera()==true) &&()
                    */
                    
                    while(true){
                    System.out.println("\nPretende:\n1.Consultar viagens.\n2.Reservar uma viagem.\n3.Consultar as suas reservas.\n4.Cancelar uma Reserva.\n5.Pontuar uma viagem.\n6.Sair.\n");
                    System.out.println("\n--------\n");
                    resp =sc.nextInt();
                    
                        
                    switch (resp){
                        case 1:
                        gestao.listarViagens();
                        gestao.listarViagenscheias();
                        System.out.println("1 - Voltar atrás?\n2 - Sair");
                        int r = sc.nextInt();
                        if(r==1){
                            break;
                        }
                        if(r==2){
                            gestao.actualizaFicheiros();
                            return;
                            
                        }
                        else{
                            System.out.println("Insira uma opcção valida");
                            break;
                           
                        }
                        
                    
                        case 2 :
                            
                            
                            gestao.reservarViagem(user);
                            break;

                        case 3:
                            
                            gestao.minhasReservas(user);
                            //consultar as minhas reservas
                            break;


                        case 4:
                            
                            //System.out.println("Insira o código da viagem: ");
                            //int codi = sc.nextInt();
                            Viagem v = gestao.procuraViagem();
                            Reserva reserva = gestao.procuraReserva(v);
                            if(reserva==null){
                                break;
                            }
                            gestao.cancelaReserva(reserva, user);
                            //cancelar reserva
                            break;

                        case 5:
                       
                            if((viagem = gestao.procuraViagem()) == null){
                                System.out.println("Não existe nenhuma viagem com esse codigo.");
                                break;
                            }
                            gestao.atribuirPontVi(viagem, user);
                            break;

                        case 6:
                            gestao.actualizaFicheiros();
                            return;
                            
                    
                        }
                    }
                }
                else{
                    //Administrador
                    //fazer protecção para só dar para escolher entre 1,2,3
                    while(true){
                        System.out.println("\nEscolha um campo:\n1.Utilizadores.\n2.Viagens.\n3.Autocarros.\n4.Reservas.\n5.Estatisticas.\n6.Sair.");
                    System.out.println("\n--------\n");
                    int resp1=sc.nextInt();
                    switch (resp1){
                        case 1:
                            System.out.println("\nPretende:\n1.Alterar as informações de um cliente.\n2.Listar Utilizadores.\n3. Sair.");//blablabla
                            int resp2=sc.nextInt();
                            switch(resp2){
                                case 1:
                                    Utilizador alvo;
                                    if((alvo = gestao.procuraUser())== null){
                                        break;
                                    };
                                    System.out.println("o que pretende alterar?");
                                    System.out.println("1 - nome");
                                    System.out.println("2 - nif");
                                    System.out.println("3 - morada");
                                    System.out.println("4 - password");
                                    System.out.println("5 - email");
                                    System.out.println("6 - telefone");
                                    int n = sc.nextInt();
                                    gestao.alteraCliente(alvo, n);
                                    break;
                                    
                                case 2:
                                    gestao.listarUsers();
                                    break;
                                case 3: 
                                    return;
    
                            }
                        break;
                        case 2:
                            System.out.println("\nPretende:\n1.Criar uma viagem.\n2.Eliminar uma viagem.\n3.Alterar uma viagem.\n4.Listar Viagens\n5.Sair.");//blablabla
                            resp=sc.nextInt();
                            switch(resp){    
                                case 1:
                                    gestao.criaViagem();
                                    System.out.println("1 - Voltar ao menu\n2 - Sair");
                                    int o = sc.nextInt();
                                    if(o==1){
                                    break;                            
                                    }                                          
                                    if(o==2){
                                        gestao.actualizaFicheiros();
                                        return;
                                    }
                                    else{
                                        System.out.println("insira uma opcção valida");
                                        break;
                                        //volta a chamr o menu
                                    }
                                
                                case 2:
                                    if(gestao.procuraViagem()!=null){
                                        viagem = gestao.procuraViagem();
                                        gestao.eliminaViagem(viagem);
                                        break;
                                    }
                                    else{
                                        System.out.println("Essa viagem não existe.");
                                        break;
                                    }
                                case 4:
                                    gestao.listarViagens();
                                    break;     
                                    
                                case 3:
                                    System.out.println("o que pretende alterar?");
                                    System.out.println("1 - Origem");
                                    System.out.println("2 - Destino");
                                    System.out.println("3 - Preço");
                                    System.out.println("4 - Duração");
                                    o = sc.nextInt();
                                    viagem = gestao.procuraViagem();
                                    gestao.alteraViagem(viagem,o);
                                    break;                                   
                                case 5:
                                    gestao.actualizaFicheiros();
                                    return;
                                
                                    }
                        break;
                        //Autocarros
                        case 3:
                            System.out.println("\nPretende:\n1.Criar um autocarro.\n2.Eliminar um autocarro\n3.Alterar as informações de um autocarro.\n4.Listar Autocarros.\n5. Sair.");//blablabla
                            resp= sc.nextInt();
                            switch(resp){
                                //cria
                                case 1:
                                                            
                                    gestao.criaAutocarro();
                                    System.out.println("1 - Voltar ao menu\n2 - Sair");
                                    op = sc.nextInt();
                                    if(op==1){
                                        break;
                                        //volta a chamar
                            
                                       }
                                    if(op==2){
                                        gestao.actualizaFicheiros();
                                        return;
                            
                                    }
                                    else{
                                        System.out.println("insira uma opcção valida");
                                        break;
                            
                                    }
                            
                                case 2:
                                    autocarro= gestao.procuraAutocarro();
                                    gestao.eliminaAutocarro(autocarro);
                                    break;
                                    
                                case 3:
                                    autocarro = gestao.procuraAutocarro();
                                    System.out.println("o que pretende alterar?");
                                    System.out.println("1 - Matrícula");
                                    System.out.println("2 - Capacidade");
                                    int n = sc.nextInt();
                                    gestao.alteraAutocarro(autocarro, n);
                                    break;
                                
                                case 4:
                                    gestao.listarBus();
                                    break;
                                    
                                case 5:
                                    gestao.actualizaFicheiros();
                                    return;
                                                              
                            }
                        //Reservas
                        case 4:
                            System.out.println("\nPretende:\n1.Criar uma reserva.\n2.Listar reservas.\n3. Sair.");//blablabla
                            resp= sc.nextInt();
                            switch(resp){
                            case 1 :
                                gestao.reservarViagem(user);
                                break;

                            case 2:
                                gestao.listarReservas();
                                break;
                            case 3:
                                gestao.actualizaFicheiros();
                                return;
                            }
                        //Estatisticas
                        case 5:
                            System.out.println("\nPretende:\n1.Identificar a viagem mais vendida num determinado mês;\n2. Identificar o cliente que mais viagens comprou num determinado mês;\n3. Listar todas as viagens que não tiveram reservas num determinado mês;\n4. Listar as reservas de uma viagem;\n5. Listar as reservas canceladas de uma viagem;\n6. Listar as reservas/clientes em espera;\n7. Identificar a viagem com melhor pontuação num determinado mês.\n8. Dados estatísticos relativamente à venda de viagens durante um ano\n9.Sair.");                        
                            int esta = sc.nextInt();
                            switch(esta){
                                case 1:
                                    System.out.println("Insira um numero para o mes:");
                                    int mes= sc.nextInt();
                                    System.out.println(mes);
                                    viagem=gestao.maisVendida(mes);
                                    if(viagem==null){
                                        System.out.println("Não foram vendidas viagens nesse mes");
                                       
                                    }
                                    else
                                        System.out.println("A viagem mais vendida foi: "+ viagem.getCodigo());
                                    
                                    break;
                                    
                                case 2:
                                    System.out.println("Insira um numero para o mes:");
                                    int mes1= sc.nextInt();
                                    user = (Cliente)gestao.comprouMais(mes1);
                                    if(user==null){
                                        System.out.println("Não houveram viagens compradas nesse mes");
                                        break;
                                        
                                    }
                                    else{
                                        System.out.println("A viagem mais vendida foi: "+ user.getNome());
                                        break;
                                    }
                                        
                                case 3:
                                    System.out.println("Insira um numero para o mes:");
                                    int mes2= sc.nextInt();
                                    gestao.listaViagensSemReserva(mes2);
                                    break;
                                    
                                case 4:
                                    viagem=gestao.procuraViagem();
                                    gestao.listarReservasViagem(viagem);
                                    break;
                                    
                                case 5:
                                    viagem= gestao.procuraViagem();
                                    gestao.listaReservasCanceladas(viagem);
                                    break;
                                    
                                case 6:
                                    gestao.listarClientesEspera();
                                    break;
                                    
                                case 7:
                                    System.out.println("Insira um numero para o mes:");
                                    int mes3= sc.nextInt();
                                    
                                    viagem=gestao.melhorPontuacao(mes3);
                                    if(viagem==null){
                                        System.out.println("Não foram pontuadas viagens nesse mes");
                                       
                                    }
                                    else
                                        System.out.println("A viagem mais pontuada foi: "+ viagem.getCodigo());
                                    
                                    break;
                                case 8:
                                    System.out.println("Insira um numero para o ano:");
                                    int ano= sc.nextInt();
                                    
                                    gestao.estatisticaAnual(ano);
                                    
                                    
                                    break;
                                case 9:
                                    gestao.actualizaFicheiros();
                                    return;
                                    
                            }
                        
                           
                        case 6:
                        gestao.actualizaFicheiros();
                        return;
                    

                    }
                    break;
                 }
                    
            }

}
            }
        }

        if (op==2){
            System.out.println("Tipo de conta:\n1.Cliente\n2.Administrador\n");
            modo=sc.nextInt();
            user = gestao.criaUtilizador(modo);

            // manda para o menu respectivo
            /////só para pessoas que ja existem antes de correr
                    System.out.println(gestao.verificaLespera());

                    Viagem v = gestao.verificaLespera();
                    if((v!=null) && gestao.isinEspera(user,v)){
                        System.out.println("Abriu uma vaga na viagem: "+v.getCodigo());
                    }
                     /////////////////////////////////////////////////////
            
            
            //Cliente
            if(modo==1){
                while(true){
                System.out.println("\nPretende:\n1.Consultar viagens.\n2.Reservar uma viagem.\n3.Consultar as suas reservas.\n4.Cancelar uma Reserva.\n5.Pontuar uma viagem.\n6.Sair.");
                System.out.println("\n--------\n");
                resp =sc.nextInt();
                
                        
                    switch (resp){
                        case 1:
                        gestao.listarViagens();
                        gestao.listarViagenscheias();
                        System.out.println("1 - Voltar atrás?\n2 - Sair");
                        int r = sc.nextInt();
                        if(r==1){
                            break;
                        }
                        if(r==2){
                            gestao.actualizaFicheiros();
                            return;
                            
                        }
                        else{
                            System.out.println("Insira uma opcção valida");
                            break;
                           
                        }

                        case 2 :
                            gestao.reservarViagem(user);
                            break;

                        case 3:
                            //consultar as minhas reservas
                            gestao.minhasReservas(user);
                            break;


                        case 4:
                             v = gestao.procuraViagem();
                            Reserva reserva = gestao.procuraReserva(v);
                            if(reserva==null){
                                break;
                            }
                            gestao.cancelaReserva(reserva, user);
                            //cancelar reserva
                            break;

                        case 5: 
                            if((viagem = gestao.procuraViagem()) == null){
                                System.out.println("Não existe nenhuma viagem com esse codigo.");
                                break;
                            }
                            gestao.atribuirPontVi(viagem, user);
                            break;
                            

                        case 6:
                            gestao.actualizaFicheiros();
                            return;
                            
                    
                }
                }
                }
            //Utilizador
            
            if(modo==2){
                 while(true){
                    System.out.println("\nPretende:\n1.Criar uma viagem.\n2.Criar um autocarro.\n3.Alterar as informações de um cliente.\n4.Alterar as informações de um autocarro.\n5.Eliminar uma viagem.\n6.Alterar uma viagem.\n7.Listar Utilizadores.\n8. Listar Autocarros.\n9.Listar Reservas.\n10. Listar Viagens.\n11. Sair.");//blablabla
                    System.out.println("\n--------\n");
                    resp=sc.nextInt();
                    switch (resp){
                        case 1:
                        gestao.criaViagem();
                        System.out.println("1 - Voltar ao menu\n2 - Sair");
                        int o = sc.nextInt();
                        if(o==1){
                            break;
                            //volta a chamar 
                            
                        }
                        if(o==2){
                            gestao.actualizaFicheiros();
                            return;
                        }
                        else{
                            System.out.println("insira uma opcção valida");
                            //volta a chamr o menu
                            break;
                        }
                        
                    
                        case 2:
                        gestao.criaAutocarro();
                        System.out.println("1 - Voltar ao menu\n2 - Sair");
                        o = sc.nextInt();
                        if(o==1){
                            break;
                            //volta a chamar
                            
                        }
                        if(o==2){
                            gestao.actualizaFicheiros();
                            return;
                        }
                        else{
                            System.out.println("insira uma opcção valida");
                            //volta a chamr o menu
                            break;
                        }
                    
                        case 3:
                        Utilizador alvo;
                        alvo = gestao.procuraUser();
                        System.out.println("o que pretende alterar?");
                        System.out.println("1 - nome");
                        System.out.println("2 - nif");
                        System.out.println("3 - morada");
                        System.out.println("4 - password");
                        System.out.println("5 - email");
                        System.out.println("6 - telefone");
                        int n = sc.nextInt();
                        gestao.alteraCliente(alvo, n);
                        //gestao.listarUsers(); FUNCIONA
                        break;
                        

                    
                        case 4:
                        autocarro = gestao.procuraAutocarro();
                        System.out.println("o que pretende alterar?");
                        System.out.println("1 - Matrícula");
                        System.out.println("2 - Capacidade");
                        n = sc.nextInt();
                        gestao.alteraAutocarro(autocarro, n);
                        break;

                    
                        case 5:
                        if(gestao.procuraViagem()!=null){
                            viagem = gestao.procuraViagem();
                            gestao.eliminaViagem(viagem);
                            break;
                        }
                        else {
                            System.out.println("Essa viagem não existe.");
                            break;
                        }
                    
                        case 6:
                        System.out.println("o que pretende alterar?");
                        System.out.println("1 - Origem");
                        System.out.println("2 - Destino");
                        System.out.println("3 - Preço");
                        System.out.println("4 - Duração");
                        o = sc.nextInt();
                        viagem = gestao.procuraViagem();
                        gestao.alteraViagem(viagem,o);
                        break;
                        
                        case 7:
                        gestao.listarUsers();
                        break;
                    
                    
                        case 8:
                        gestao.listarBus();
                        break;
                    
                        case 9:
                        gestao.listarReservas();
                        break;
                    
                        case 10:
                        gestao.listarViagens();
                        break;
                            
                        case 11:
                        gestao.actualizaFicheiros();
                        return;
                    

                        }
                    }
                }
            }
            gestao.actualizaFicheiros();
        }

        

}