import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class atl {

    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();
    static ArrayList<String> inventario = new ArrayList<>();
    static int energia = 0;

    public static void mostrarInicio() {
        System.out.println("***********");
        System.out.println("   RPG   ");
        System.out.println("***********");
    }

    public static int escolhaPersonagem() {
        int opcao;
        do {
            System.out.println("1- Guerreiro");
            System.out.println("2- Arqueiro");
            System.out.println("3- Xamã");
            System.out.println("4- Goblin");
            opcao = sc.nextInt();
        } while (opcao <= 0 || opcao >= 5);
        return opcao;
    }

    public static int sortearDadoD20() {
        return random.nextInt(20) + 1;
    }

    public static int explorar() {
        int sorteio = random.nextInt(100) + 1;
        if (sorteio <= 40) return 1;
        if (sorteio <= 60) return 2;
        if (sorteio <= 95) return 3;
        return 4;
    }

    // ===== SISTEMA DE RECUPERACAO =====
    public static int recuperar(int vida, int vidaMaxima) {

        System.out.println("\n=== Como voce quer se recuperar? ===");
        System.out.println("Vida atual: " + vida + " / " + vidaMaxima);
        System.out.println("Energia: " + energia);
        System.out.println("1- Descansar (gratis, recupera 20%)");
        System.out.println("2- Meditar gastando energia (2 energias, recupera 50%)");
        System.out.println("3- Usar pocao de vida (recupera tudo)");
        System.out.println("4- Cancelar");

        int opcao = sc.nextInt();

        if (opcao == 1) {
            int cura = vidaMaxima * 20 / 100;
            vida += cura;
            if (vida > vidaMaxima) vida = vidaMaxima;
            System.out.println("Voce descansou e recuperou " + cura + " de vida!");

        } else if (opcao == 2) {
            if (energia >= 2) {
                energia -= 2;
                int cura = vidaMaxima * 50 / 100;
                vida += cura;
                if (vida > vidaMaxima) vida = vidaMaxima;
                System.out.println("Voce meditou e recuperou " + cura + " de vida!");
                System.out.println("Energia restante: " + energia);
            } else {
                System.out.println("Energia insuficiente! Precisa de 2 energias.");
            }

        } else if (opcao == 3) {
            if (inventario.contains("Pocao de Vida")) {
                inventario.remove("Pocao de Vida");
                vida = vidaMaxima;
                System.out.println("Voce usou uma Pocao de Vida e ficou com vida cheia!");
            } else {
                System.out.println("Voce nao tem nenhuma Pocao de Vida no inventario!");
            }

        } else {
            System.out.println("Recuperacao cancelada.");
        }

        return vida;
    }

    public static int combater(int vida, int ataque, int defesa,
                               boolean temArma, boolean temArmadura) {

        int monstroEspecial = random.nextInt(20) + 1;
        int vidaMonstro = random.nextInt(80) + 40 + monstroEspecial;
        int ataqueMonstro = random.nextInt(30) + 5;

        String nomeMonstro = "goblin";
        if (vidaMonstro >= 100) {
            nomeMonstro = "megamonstro";
            System.out.println("voce encontrou o megamonstro");
        }

        if (temArmadura) defesa += 20;
        if (temArma) ataque += 20;

        System.out.println("o " + nomeMonstro + " tem:");
        System.out.println("vida: " + vidaMonstro);
        System.out.println("ataque: " + ataqueMonstro);

        while (vidaMonstro > 0 && vida > 0) {

            System.out.println("\n--- Turno de combate ---");
            System.out.println("Sua vida: " + vida + " | Vida do " + nomeMonstro + ": " + vidaMonstro);
            System.out.println("Energia: " + energia);
            System.out.println("1- Atacar normal (d20)");
            System.out.println("2- Habilidade especial (dano x3, gasta 3 energias)");
            System.out.println("3- Fugir");

            int acao = sc.nextInt();

            if (acao == 3) {
                System.out.println("Voce fugiu da luta!");
                return -2;
            }

            int danoCausado = 0;

            if (acao == 2) {
                if (energia >= 3) {
                    energia -= 3;
                    danoCausado = ataque * 3;
                    vidaMonstro -= danoCausado;
                    System.out.println("\nVOCE USOU HABILIDADE ESPECIAL!");
                    System.out.println("Dano massivo: " + danoCausado + "!");
                    System.out.println("Energia restante: " + energia);
                } else {
                    System.out.println("\nEnergia insuficiente! Precisa de 3 energias.");
                    continue;
                }
            } else {
                int dado = sortearDadoD20();

                if (dado <= 7) {
                    System.out.println("\nVoce errou o ataque!");
                } else if (dado <= 14) {
                    danoCausado = Math.max(1, ataque / 2);
                    vidaMonstro -= danoCausado;
                    System.out.println("\nAtaque normal - metade do dano: " + danoCausado);
                } else if (dado <= 19) {
                    danoCausado = ataque;
                    vidaMonstro -= danoCausado;
                    System.out.println("\nAtaque normal - dano total: " + danoCausado);
                } else {
                    danoCausado = ataque * 2;
                    vidaMonstro -= danoCausado;
                    System.out.println("\nCRITICO! Dano dobrado: " + danoCausado);
                }
            }

            System.out.println("Vida do " + nomeMonstro + " restante: " + Math.max(0, vidaMonstro));

            if (vidaMonstro <= 0) {
                System.out.println("\nVoce matou o " + nomeMonstro + "!");
                return vida;
            }

            System.out.println("\nVez do " + nomeMonstro + "...");
            int dadoMonstro = sortearDadoD20();

            if (dadoMonstro <= 7) {
                System.out.println(nomeMonstro + " errou o ataque!");
            } else if (dadoMonstro <= 14) {
                int dano = Math.max(1, ataqueMonstro / 2);
                vida -= dano;
                System.out.println(nomeMonstro + " causou dano parcial: " + dano);
            } else if (dadoMonstro <= 19) {
                vida -= ataqueMonstro;
                System.out.println(nomeMonstro + " causou dano total: " + ataqueMonstro);
            } else {
                vida -= ataqueMonstro * 2;
                System.out.println(nomeMonstro + " causou CRITICO! Dano dobrado: " + ataqueMonstro * 2);
            }

            System.out.println("Sua vida restante: " + vida);

            if (vida <= 0) {
                System.out.println("\nVoce foi derrotado pelo " + nomeMonstro + "!");
                return -1;
            }
        }

        return vida;
    }

    public static void main(String[] args) throws InterruptedException {

        mostrarInicio();

        System.out.println("Informe o nome do seu personagem: ");
        String nome = sc.next();

        int vida;
        int ataque;
        int defesa;

        int opcao = escolhaPersonagem();

        if (opcao == 1) {
            vida = 120; ataque = 80; defesa = 50;
        } else if (opcao == 2) {
            vida = 100; ataque = 70; defesa = 40;
        } else if (opcao == 3) {
            vida = 80; ataque = 50; defesa = 30;
        } else {
            vida = 90; ataque = 40; defesa = 20;
        }

        int vidaMaxima = vida;
        int inimigosMortos = 0;
        boolean temArma = false;
        boolean temArmadura = false;
        int opcaoJogo = -1;

        while (opcaoJogo != 0) {

            System.out.println("\n=== Inicio da jornada ===");
            System.out.println("1- Explorar");
            System.out.println("2- Ver status");
            System.out.println("3- Recuperar");
            System.out.println("0- Sair");

            opcaoJogo = sc.nextInt();

            if (opcaoJogo == 1) {

                int evento = explorar();

                if (evento == 1) {
                    System.out.println("\nvoce encontrou um goblin!");

                    vida = combater(vida, ataque, defesa, temArma, temArmadura);

                    if (vida == -1) {
                        System.err.println("\n*********** FIM DE JOGO ***********");
                        System.out.println("Voce morreu, " + nome + "!");
                        System.out.println("Inimigos derrotados: " + inimigosMortos);
                        System.out.println("Inventario: " + inventario);
                        break;
                    } else if (vida == -2) {
                        System.out.println("Voce fugiu e sobreviveu!");
                    } else {
                        inimigosMortos++;
                        System.out.println("\nTotal de inimigos derrotados: " + inimigosMortos);
                    }

                } else if (evento == 2) {
                    System.out.println("\nvoce encontrou um baú de energia!");
                    if (energia + 1 < 10) {
                        energia += 1;
                        System.out.println("Energia: " + energia);
                    } else {
                        System.out.println("Energia já está cheia!");
                    }

                } else if (evento == 3) {
                    System.out.println("\nvoce segue sua jornada...");

                } else if (evento == 4) {
                    System.out.println("\nvoce encontrou um baú raro!");

                    String[] itensPossiveis = {"Espada Longa", "Escudo de Ferro",
                            "Elmo Magico", "Anel de Forca", "Pocao de Vida"};
                    int indiceItem = random.nextInt(itensPossiveis.length);
                    String itemEncontrado = itensPossiveis[indiceItem];

                    if (!itemEncontrado.equals("Pocao de Vida") && inventario.contains(itemEncontrado)) {
                        System.out.println("Voce ja possui esse item!");
                    } else {
                        inventario.add(itemEncontrado);
                        System.out.println("Voce guardou no inventario: " + itemEncontrado);

                        if (itemEncontrado.equals("Espada Longa")) {
                            ataque += 10;
                            System.out.println("Ataque aumentou em 10!");
                        } else if (itemEncontrado.equals("Escudo de Ferro")) {
                            defesa += 10;
                            System.out.println("Defesa aumentou em 10!");
                        } else if (itemEncontrado.equals("Elmo Magico")) {
                            vidaMaxima += 20;
                            vida += 20;
                            System.out.println("Vida maxima aumentou em 20!");
                        } else if (itemEncontrado.equals("Anel de Forca")) {
                            ataque += 5;
                            defesa += 5;
                            System.out.println("Ataque e defesa aumentaram em 5!");
                        } else if (itemEncontrado.equals("Pocao de Vida")) {
                            System.out.println("Guarde para usar quando estiver ferido!");
                        }
                    }
                }

            } else if (opcaoJogo == 2) {
                System.out.println("\n=== Status ===");
                System.out.println("Olá " + nome);
                System.out.println("Ataque: " + ataque);
                System.out.println("Defesa: " + defesa);
                System.out.println("Vida: " + vida + " / " + vidaMaxima);
                System.out.println("Energia: " + energia + " / 10");
                System.out.println("Inimigos derrotados: " + inimigosMortos);

                if (inventario.isEmpty()) {
                    System.out.println("Inventario: vazio");
                } else {
                    System.out.println("Inventario:");
                    for (String item : inventario) {
                        System.out.println(" - " + item);
                    }
                }

            } else if (opcaoJogo == 3) {
                vida = recuperar(vida, vidaMaxima);

            } else {
                System.out.println("\nObrigado por jogar, " + nome + "!");
                System.out.println("Inimigos derrotados: " + inimigosMortos);
                System.out.println("Inventario: " + inventario);
            }
        }
    }
}