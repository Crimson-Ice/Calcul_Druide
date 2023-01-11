package algorith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args)
    {
        System.out.println(polonaiseInverse(saisie()));
    }

    public static String saisie()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrer un calcul : ");
        String calcul = scanner.nextLine();
        scanner.close();

        verificationSaisie(calcul);

        return calcul;
    }

    public static void verificationSaisie(String saisie)
    {
        String regex = "(\\d+)|([+|\\-|*|/]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(saisie);

        if (!saisie.matches("^\\s*\\d*\\s\\d*\\s+[^\\d].*")) {
            throw new IllegalArgumentException("Il n'y a pas deux nombres a gauche du premier symbole");
        }

        while(!matcher.find())
        {
            throw new IllegalArgumentException("la saisie du calcul ne contient pas que des nombres ou des symboles");
        }
    }

    public static double polonaiseInverse(String calcul)
    {
        List<String> listeDeCharatere = Arrays.stream(calcul.split(" ")).toList();
        List<Double> pileOperande = new ArrayList<>();
        List<String> operateur = Arrays.asList("+","-","*","/");

        for(int i = 0; i < listeDeCharatere.size(); i++)
        {
            if(operateur.contains(listeDeCharatere.get(i)))
            {
                double operation = calculOperande(pileOperande, listeDeCharatere, i);
                pileOperande.remove(pileOperande.size() - 1);
                pileOperande.remove(pileOperande.size() - 1);
                pileOperande.add(operation);
            }
            else
            {
                pileOperande.add(Double.valueOf(listeDeCharatere.get(i)));
            }
        }

        return pileOperande.get(0);
    }

    public static double calculOperande(List<Double> pileOperande, List<String> listeDeCharatere, int pos)
    {
        return switch (listeDeCharatere.get(pos)) {
            case "+" -> pileOperande.get(pileOperande.size() - 2) + pileOperande.get(pileOperande.size() - 1);
            case "-" -> pileOperande.get(pileOperande.size() - 2) - pileOperande.get(pileOperande.size() - 1);
            case "*" -> pileOperande.get(pileOperande.size() - 2) * pileOperande.get(pileOperande.size() - 1);
            case "/" -> pileOperande.get(pileOperande.size() - 2) / pileOperande.get(pileOperande.size() - 1);
            default -> 1;
        };
    }
}