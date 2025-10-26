import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        String caracteres = "mjqjpqmgbljsphdztnvjfqwrcgsmlb";
        HashMap<Character, Integer> charCode = new HashMap<>();
        int init = 0;
        int numCaracteres = 0;
        for (int i = 0; i < caracteres.length(); i++) {
            numCaracteres++;
            char c = caracteres.charAt(i);
            if (charCode.containsKey(c)){
                init = charCode.remove(c);
                charCode.put(c, i);
            }else{
                charCode.put(c, i);
            }
            if(charCode.size() == 14){
                break;
            }
        }
        System.out.println(caracteres + " começa os 14 caracteres distintos na "+ (init+1) +" posição ");
        System.out.println("O número de caracteres processados é de "+numCaracteres);
    }
}