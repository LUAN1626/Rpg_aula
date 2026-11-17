import java.util.Random;
import java.util.Scanner;

public class Main2 {

    public static void mostrarInicio() {
        System.out.println("***********");
        System.out.println("   RPG   ");
        System.out.println("***********");
    }

    public static int escolhaPersonagem() {
        Scanner sc = new Scanner(System.in);
        int opcaoClasse = 0;
        do {

            System.out.println("1- Guerreiro");
            System.out.println("2- Arqueiro");
            System.out.println("3- Xamã");
            System.out.println("4- Goblin");
            opcaoClasse = sc.nextInt();
        } while (opcaoClasse <= 0 || opcaoClasse >= 5);
        return opcaoClasse;
    }

    public static int combater(int vida,
                               int ataque, int defesa,
                               boolean temArma,
                               boolean temArmadura) {

        Random random = new Random();
        Scanner sc = new Scanner(System.in);


        int monstroEspecial = random.nextInt(20) + 1;
        int vidaMonstro = random.nextInt(80) + 40 + monstroEspecial;
        int ataqueMonstro = random.nextInt(30) + 5;

        if (vidaMonstro >= 100) {
            System.out.println("voce encontrou o megamonstro");
        }

        if (temArmadura) {
            defesa += 20;
        }

        if (temArma) {
            ataque += 20;
        }

        System.out.println("o inimigo tem:");
        System.out.println("vida: " + vidaMonstro);
        System.out.println("ataque: " + ataqueMonstro);

        while (vidaMonstro > 0 && vida > 0) {

            int sorteioDado = sortearDadoD20();

            if (sorteioDado <= 7) {
                System.out.println("você errou");
            } else if (sorteioDado <= 14) {
                vidaMonstro -= ataque / 2;
                System.out.println("metade do dano");
            } else if (sorteioDado <= 19) {
                vidaMonstro -= ataque;
                System.out.println("ataque total");
            } else {
                vidaMonstro -= ataque * 2;
                System.out.println("ataque critico");
            }

            System.out.println("Vida personavel : " + vida);
            System.out.println("vida mosntro : " + vidaMonstro);

            if (vidaMonstro > 0) {

                sorteioDado = sortearDadoD20();

                if (sorteioDado <= 7) {
                    System.out.println("o goblin errou");
                } else if (sorteioDado <= 14) {
                    vida -= ataqueMonstro / 2;
                } else if (sorteioDado <= 19) {
                    vida -= ataqueMonstro;
                    System.out.println("voce recebeu o ataque total");
                } else {
                    vida -= ataqueMonstro * 2;
                    System.out.println("voce recebeu o critico");
                }
                System.out.println("Vida personavel : " + vida);
                System.out.println("vida mosntro : " + vidaMonstro);

            }

        }

        if (vidaMonstro >0){
            System.out.println("o goblin te pegou");
            return 0;
        }else{
            System.out.println("voce matou ele");
            return vida;
        }
    }


    public static int sortearDadoD20() {
        Random random = new Random();
        return random.nextInt(20) + 1;
    }

    public static int explorar() {

        Random random = new Random();
        int sorteio = random.nextInt(100) + 1;

        if (sorteio <= 40) {
            return 1; //explorar
        } else if (sorteio <= 60) {
            return 2; //energia
        } else if (sorteio <= 95) {
            return 3; //segue
        } else {
            return 4; //item raro
        }

           /*
            1- encontrou inimigo(40%)
            2- encontrou uma energia(20%)
            3- segue a vida(35%)
            4- encontrou item raro(5%)
             */

    }

    public static void main() throws InterruptedException {

        mostrarInicio();

        Scanner sc = new Scanner(System.in);

        System.out.println("Informe o nome do seu personagem: ");
        String nome = sc.next();
        int vida ;
        String classe = "";
        int ataque;
        int defesa;
        int velocidade = 0;
        int energia = 0;
        int nivel = 0;
        boolean temArma = false;
        boolean temArmadura = false;


        int opcao = escolhaPersonagem();

        if (opcao == 1) {
            //guerreiro
            vida = 120;
            ataque = 80;
            defesa = 50;

        } else if (opcao == 2) {
            vida = 100;
            ataque = 70;
            defesa = 40;
        } else if (opcao == 3) {
            vida = 80;
            ataque = 50;
            defesa = 30;
        } else {
            vida = 90;
            ataque = 40;
            defesa = 20;
        }

        int opcaoJogo = -1;
        int vidaMaxima = vida;

        while (opcaoJogo != 0) {

            System.out.println("Inicio da jornada");
            System.out.println("1-Explorar");
            System.out.println("2-Ver status");
            System.out.println("3-Recuperar");
            System.out.println("0-Sair");

            opcaoJogo = sc.nextInt();

            if (opcaoJogo == 1) {

                int opcaoExplorar = explorar();

                if (opcaoExplorar == 1) {
                    System.out.println("voce encontrou um goblin");

                    System.out.println("selecione sua ação");
                    System.out.println("1- atacar");
                    System.out.println("2- defender-se");
                    System.out.println("3- fugir");
                    int opcaoLuta = sc.nextInt();

                    if (opcaoLuta == 1) {
                        //lutar
                       vida = combater(vida, ataque, defesa, temArma, temArmadura);

                       if (vida <=0){
                           System.err.println("voce morreu");
                           break;
                       }

                    } else if (opcaoLuta == 2) {

                    } else if (opcaoLuta == 3) {
                        System.out.println("covarde, voce fugiu");
                    } else {
                        System.out.println("opção invalida");
                    }

                } else if (opcaoExplorar == 2) {
                    System.out.println("voce encontrou um bau de energia");

                    if (energia + 1 < 10) {
                        energia += 1;
                    } else {
                        System.out.println("energia já está cheia");
                    }


                } else if (opcaoExplorar == 3) {
                    System.out.println("voce segue sua vida");
                } else if (opcaoExplorar == 4) {
                    System.out.println("voce encontrou um item raro");

                    Random random = new Random();
                    int itemSorteado = random.nextInt(2) + 1;

                    if (itemSorteado == 1) {

                        if (temArmadura) {
                            System.out.println("já possui armadura");
                        } else {
                            temArmadura = true;
                        }

                    } else {

                        if (temArma) {
                            System.out.println("ja possui arma");
                        } else {
                            temArma = true;
                        }

                    }

                }


            } else if (opcaoJogo == 2) {
                System.out.println("Olá " + nome);
                System.out.println("ataque: " + ataque);
                System.out.println("defesa: " + defesa);
                System.out.println("vida: " + vida);
                System.out.println("energia: " + energia);
                System.out.println("armadura: " + (temArmadura ? "sim" : "nao"));
                System.out.println("arma: " + (temArma ? "sim" : "nao"));

            } else if (opcaoJogo == 3) {
                vida += 10;
                if (vida >= vidaMaxima) {
                    vida = vidaMaxima;
                    System.out.println("Vida maxima atingida");
                } else {
                    System.out.println("vida aumentada");
                }

                System.out.println("recuperando energia...");

                Thread.sleep(2000);

                System.out.println("ainda sendo recuperada...");
                Thread.sleep(3000);

                System.out.println("energia recuperada");

                energia += 1;


            } else {
                break;
            }
        }
    }
}
