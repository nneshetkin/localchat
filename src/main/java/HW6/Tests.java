package HW6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tests {


    static Integer[] resizeArrayInteger(Integer[] arr) {
        int count = 0;
        List<Integer> list = Arrays.asList(arr);
        if (list.contains(4)) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == 4) {
                    count = i + 1;
                }
            }
            list = list.subList(count, list.size());
            System.out.println("all right, new array is: " + list.toString());
            Integer[] a = new Integer[list.size()];
            int ind = 0;
            for (Integer value : list) {
                a[ind++] = value;
            }
            return a;
        }
        throw new RuntimeException("There are no numbers 4 in array");
    }
    static int[] resizeArrayInt(int[] array) throws RuntimeException {
        for (int i = array.length - 1; i >= 0; i--){
            if (array[i] == 4) {
                int idx = i + 1;
                int[] result = new int[array.length - idx];
                System.arraycopy(array, idx, result, 0, result.length);
                return result;
            }
            }
        throw new RuntimeException("There are no numbers 4 in array");
    }
    static boolean testOnConsist1and4(int[] arr){
        int count1=0;
        int count4=0;
        for (int i = 0; i < arr.length; i++)
            if (arr[i]==1)
                count1++;
            else if(arr[i]==4)
                count4++;
    return count1>0&count4>0;
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[]{0,2,4,3,5,6};
        int[] arrInt = new int[]{0,2,3,4,5,6};
        System.out.println(Arrays.toString(resizeArrayInteger(array)));
        System.out.println(Arrays.toString(resizeArrayInt(arrInt)));
        System.out.println(testOnConsist1and4(arrInt));
        }


}
