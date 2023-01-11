package algorith;
import java.util.*;
import java.util.logging.Logger;

public class Main {

    public static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args)
    {
        double res = polonaiseInverse(saisie());
        logger.info(String.valueOf(res));
    }

    public static String saisie()
    {
        Scanner scanner = new Scanner(System.in);
        logger.info("Entrer un calcul : ");
        String calcul = scanner.nextLine();
        scanner.close();

        verificationSaisie(calcul);

        return calcul;
    }

    public static void verificationSaisie(String saisie)
    {
        if(!verifNombreEtSynbole(saisie))
        {
            throw new IllegalArgumentException("la saisie du calcul ne contient pas que des nombres ou des symboles");
        }

        if(polonaiseInverse(saisie) == Double.POSITIVE_INFINITY)
        {
            throw new IllegalArgumentException("Il y a une division par zero dans le calcul");
        }
    }

    public static boolean verifNombreEtSynbole(String saisie)
    {
        for(int i = 0; i < saisie.length(); i++)
        {
            int ascii = saisie.charAt(i);
            if((ascii < 48 || ascii > 57)
                    && (ascii != 42 && ascii != 43 && ascii != 45 && ascii != 47 && ascii != 32 && ascii != 46))
            {
                return false;
            }
        }
        return true;
    }

    public static double polonaiseInverse(String calcul)
    {
        List<String> listeDeCharatere = split(calcul);
        List<Double> pileOperande = new ArrayList<>();
        List<String> operateur = Arrays.asList("+","-","*","/");

        for(int i = 0; i < sizeOf(listeDeCharatere); i++)
        {
            if(listeContainsElement(operateur, listeDeCharatere.get(i)))
            {
                double operation = calculOperande(pileOperande, listeDeCharatere, i);
                pileOperande = pop(pileOperande);
                pileOperande = pop(pileOperande);
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
        double operation;
        switch (listeDeCharatere.get(pos))
        {
            case "+":
                operation = pileOperande.get(pileOperande.size() - 2) + pileOperande.get(pileOperande.size() - 1);
            break;
            case "-":
                operation = pileOperande.get(pileOperande.size() - 2) - pileOperande.get(pileOperande.size() - 1);
            break;
            case "*":
                operation = pileOperande.get(pileOperande.size() - 2) * pileOperande.get(pileOperande.size() - 1);
            break;
            case "/":
                operation = pileOperande.get(pileOperande.size() - 2) / pileOperande.get(pileOperande.size() - 1);
            break;
            default:
                operation = 0;
        }
        return operation;
    }

    public static List<String> split(String str)
    {
        List<String> res = new ArrayList<>();
        int i = 0;

        while(i < str.length())
        {
            StringBuilder tmp = new StringBuilder();
            while (i < str.length() && str.charAt(i) != ' ')
            {
                tmp.append(str.charAt(i));
                i++;
            }
            i++;
            res.add(tmp.toString());
        }

        return res;
    }

    public static <T> int sizeOf(List<T> listStr)
    {
        int count = 0;
        for (T ignored : listStr) {
            count++;
        }
        return count;
    }

    public static boolean listeContainsElement(List<String> listeStr, String str)
    {
        for (int i = 0; i < sizeOf(listeStr); i++)
        {
            if(Objects.equals(listeStr.get(i), str))
            {
                return true;
            }
        }
        return false;
    }

    public static List<Double> pop(List<Double> listeDouble)
    {
        List<Double> res = new ArrayList<>();
        final int minimumSize = 1;
        if(sizeOf(listeDouble) > minimumSize)
        {
            for (int i = 0; i < sizeOf(listeDouble) - 1; i++)
            {
                res.add(listeDouble.get(i));
            }
        }
        return res;
    }
}