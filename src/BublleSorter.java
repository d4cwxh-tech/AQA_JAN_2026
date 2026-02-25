import java.util.Random;
import java.util.Arrays;

public class BublleSorter {

    public static void main(String[] args) throws InterruptedException {

        int[] arr = new int[13];
        
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }

        shuffle(arr);

        System.out.println("Начальный массив:");
        System.out.println(Arrays.toString(arr));

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {

                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    System.out.println(Arrays.toString(arr));
                    Thread.sleep(300);
                }
            }
        }

        System.out.println("Отсортировано:");
        System.out.println(Arrays.toString(arr));
    }

    public static void shuffle(int[] arr) {
        Random random = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);

            int temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;
        }
    }
}
