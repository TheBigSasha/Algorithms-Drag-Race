public class AllTheSorts{

    /**
     * Merges subarrays of dataset, first being from start to mid,
     * second being from mid+1 to end.
     * @param dataset       Array to be merged
     * @param startIDX      Start index
     * @param midIDX        Mid index (end of first array, start of second)
     * @param endIDX        End index
     */
    public void merge(int[] dataset, int startIDX, int midIDX, int endIDX)
    {
        //Determine Sizes
        int sizeOfFirst = midIDX - startIDX + 1;
        int sizeOfSecond = endIDX - midIDX;

        //Create Temporary Arrays
        int[] Left = new int[sizeOfFirst];
        int[] Right = new int[sizeOfSecond];

        //Copy into temporaries
        for (int i = 0; i < sizeOfFirst; i++)
            Left[i] = dataset[startIDX + i];
        for (int i = 0; i < sizeOfSecond; i++)
            Right[i] = dataset[midIDX + 1 + i];

        int indexAtLeft = 0, indexAtRight = 0;
        int indexAtMainDataset = startIDX;

        //This bit merges the arrays such that they are in order! This is where the fun happens!
        while (indexAtLeft < sizeOfFirst && indexAtRight < sizeOfSecond) {
            if (Left[indexAtLeft] <= Right[indexAtRight]) {                  //Adds into the final output if the left is less than the right!
                dataset[indexAtMainDataset] = Left[indexAtLeft];
                indexAtLeft++;
            }
            else {
                dataset[indexAtMainDataset] = Right[indexAtRight];                  //If the right is bigger, it gets added instead!
                indexAtRight++;
            }
            indexAtMainDataset++;
        }

        //Take the remaining items from the arrays if they weren't got before
        while (indexAtLeft < sizeOfFirst) {
            dataset[indexAtMainDataset] = Left[indexAtLeft];
            indexAtLeft++;
            indexAtMainDataset++;
        }
        while (indexAtRight < sizeOfSecond) {
            dataset[indexAtMainDataset] = Right[indexAtRight];
            indexAtRight++;
            indexAtMainDataset++;
        }
    }

    public void mergeSortRecursive(int[] dataset, int startIDX, int endIDX)
    {
        if (startIDX < endIDX) {
            // Find the middle point
            int midIDX = (startIDX + endIDX) / 2;

            // Sort first and second halves
            mergeSortRecursive(dataset, startIDX, midIDX);
            mergeSortRecursive(dataset, midIDX + 1, endIDX);

            // Merge the sorted halves
            merge(dataset, startIDX, midIDX, endIDX);
        }
    }

    /* A utility function to print array of size n */
    static void printArray(int arr[])
    {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }

    // Driver method
    public static void main(String args[])
    {
        int arr[] = { 12, 11, 13, 5, 6, 7 };

        System.out.println("Given Array");
        printArray(arr);

        AllTheSorts ob = new AllTheSorts();
        ob.mergeSortRecursive(arr, 0, arr.length - 1);

        System.out.println("\nSorted array");
        printArray(arr);
    }
}
