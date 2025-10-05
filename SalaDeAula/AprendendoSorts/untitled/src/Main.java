import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int[] listaToSort = {6, 1, 10, 7, 5};
        int[] listInsertioSort = insertioSort(listaToSort);
        System.out.println(Arrays.toString(listInsertioSort));
        int[] listQuickSort = quickSort(new int[]{
                472, 915, 38, 604, 251, 799, 183, 928, 56, 347,
                782, 621, 94, 503, 388, 642, 27, 714, 830, 469,
                302, 578, 998, 420, 169, 258, 863, 736, 195, 315,
                441, 567, 804, 99, 223, 657, 308, 941, 145, 718,
                34, 867, 589, 770, 201, 482, 313, 653, 846, 126,
                550, 64, 712, 285, 433, 958, 390, 143, 841, 229,
                772, 358, 687, 980, 573, 493, 263, 326, 709, 36,
                687, 485, 142, 568, 622, 193, 349, 981, 504, 267,
                815, 241, 600, 749, 154, 482, 715, 912, 379, 457,
                689, 852, 937, 175, 218, 449, 333, 509, 990, 821
        });
        System.out.println(Arrays.toString(listQuickSort));
    }

    private static int[] quickSort(int[] ints) {
        return quickSortAux(ints, 0, ints.length - 1);
    }

    private static int[] quickSortAux(int[] ints, int init, int end) {
        if (init >= end) return ints;
        int pivo = ints[end];
        int i = init - 1;
        for (int j = init; j < end; j++) {
            if(pivo >= ints[j]){
                i++;
                int temp = ints[j];
                ints[j] = ints[i];
                ints[i] = temp;
            }
        }
        i++;
        ints[end] = ints[i];
        ints[i] = pivo;
        quickSortAux(ints, init, i-1);
        quickSortAux(ints, i+1, end);
        return ints;
    }


    private static int[] insertioSort(int[] listaToSort) {
        //avarage case = O(n²)
        //best case = O(n)
        //worst case = O(n²)
        //space = O(1)
        for (int i = 1; i < listaToSort.length; i++) {
            int numAtual = listaToSort[i];
            int j = i - 1;
            while (j >= 0 && numAtual < listaToSort[j]) {
                listaToSort[j + 1] = listaToSort[j];
                j--;
            }
            listaToSort[j + 1] = numAtual;
        }
        return listaToSort;
    }


}