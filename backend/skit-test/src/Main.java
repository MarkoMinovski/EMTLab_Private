
public class Main {

    public static int findLast(int[] arr, int x) {
        int lastIndex = -1;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) {
                lastIndex = i;
            }
        }

        return lastIndex;
    }


    public static void main(String[] args) {

        int[] array = {6565, 6, 225, 7547, 3, 82375235, 2354};

        System.out.println("last 3 in array: " + findLast(array, 3));
    }
}