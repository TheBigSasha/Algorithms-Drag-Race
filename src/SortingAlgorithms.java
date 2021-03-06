
import java.util.Arrays;
import java.util.Random;
import RuntimeTester.*;

public class SortingAlgorithms {
    private static Random rand = new Random(24601);
    /**
     * Merges subarrays of dataset, first being from start to mid,
     * second being from mid+1 to end.
     * @param dataset       Array to be merged
     * @param startIDX      Start index
     * @param midIDX        Mid index (end of first array, start of second)
     * @param endIDX        End index
     */
    public static void merge(String[] dataset, int startIDX, int midIDX, int endIDX)
    {
        //Determine Sizes
        int sizeOfFirst = midIDX - startIDX + 1;
        int sizeOfSecond = endIDX - midIDX;

        //Create Temporary Arrays
        String[] Left = new String[sizeOfFirst];
        String[] Right = new String[sizeOfSecond];

        //Copy into temporaries
        for (int i = 0; i < sizeOfFirst; i++)
            Left[i] = dataset[startIDX + i];
        for (int i = 0; i < sizeOfSecond; i++)
            Right[i] = dataset[midIDX + 1 + i];

        int indexAtLeft = 0, indexAtRight = 0;
        int indexAtMainDataset = startIDX;

        //This bit merges the arrays such that they are in order! This is where the fun happens!
        while (indexAtLeft < sizeOfFirst && indexAtRight < sizeOfSecond) {
            if (Left[indexAtLeft].compareTo(Right[indexAtRight]) <= 0) {                  //Adds into the final output if the left is less than the right!
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


    public static void mergeSortRecursive(String[] dataset, int startIDX, int endIDX)
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

    /**
     * Sorts the array, hopefully fast
     * @param dataset       Array to be sorted
     */
    public static void mergeSort(String[] dataset) {
        mergeSortRecursive(dataset, 0 , dataset.length - 1);
    }

    public static void bubbleSort(String[] dataset) {
        int wall = dataset.length;
        for (int i = 0; i < wall-1; i++)
            for (int j = 0; j < wall-i-1; j++)
                if (dataset[j].compareTo(dataset[j+1]) > 0)
                {
                    String temp = dataset[j];
                    dataset[j] = dataset[j+1];
                    dataset[j+1] = temp;
                }
    }

    public static void insertionSort(String[] dataset) {
        int n = dataset.length;
        for (int i = 1; i < n; ++i) {
            String key = dataset[i];
            int j = i - 1;
            while (j >= 0 && dataset[j].compareTo(key) > 0) {
                dataset[j + 1] = dataset[j];
                j = j - 1;
            }
            dataset[j + 1] = key;
        }
    }

    public static void main(String args[]) {
        testSorting();
//        BinaryTree<String> balanced = buildBalancedTree(10);
//        BinaryTree<String> smallUnblanced = buildUnbalancedTreeIncreasing(10);
//        BinaryTree<String> bigUnbalanced = buildUnbalancedTreeDecreasing(10);
        Visualizer.launch(SortingAlgorithms.class);
    }

    private static void testSorting() {
        String[] testSubject = new String[10];
        fillList(testSubject);
        String[] subjectII = Arrays.copyOf(testSubject,testSubject.length);
        Arrays.sort(testSubject);
        mergeSort(subjectII);
        for(int i = 0; i < testSubject.length; i++){
            if(!testSubject[i].equals(subjectII[i])) System.out.println("Error! Merge sort failed at index " + i + subjectII[i] + testSubject[i]);
        }
    }

    public static void fillList(String[] list) {
        for (int i = 0; i < list.length; i++) {
            list[i] = ((char) rand.nextInt(400) + " Test " + i + " ---- " + " " + (char) rand.nextInt(400));
        }
    }

    @benchmark(name = "Our Merge Sort", category = "Drag Race")
    public static long testMergeSort(long size){
        String[] test = new String[(int) size];
        fillList(test);
        long startTime = System.nanoTime();
        mergeSort(test);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    @benchmark(name = "Our Insertion Sort", category = "Drag Race")
    public static long testInsertionSort(long size){
        String[] test = new String[(int) size];
        fillList(test);
        long startTime = System.nanoTime();
        insertionSort(test);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
    
    @benchmark(name = "Our Bubble Sort", category = "Drag Race")
    public static long ourBubbleSort(long size){
        String[] test = new String[(int) size];
        fillList(test);
        long startTime = System.nanoTime();
        bubbleSort(test);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    @benchmark(name = "inorder traverse", category = "Binary Tree")
    public static long testInOrderTraversal(long input){
        BinaryTree<String> bTree = new BinaryTree<>();
        for(long i = 0; i <input; i++){
            bTree.add(((char) rand.nextInt(400) + " Test " + i + " ---- " + " " + (char) rand.nextInt(400)));
        }
        long startTime = System.nanoTime();
        bTree.inOrder();
        long endTime = System.nanoTime();
        return startTime-endTime;
    }

    @benchmark(name = "reverse order traverse", category = "Binary Tree")
    public static long testReverseOrderTraversal(long input){
        BinaryTree<String> bTree = new BinaryTree<>();
        for(long i = 0; i <input; i++){
            bTree.add(((char) rand.nextInt(400) + " Test " + i + " ---- " + " " + (char) rand.nextInt(400)));
        }
        long startTime = System.nanoTime();
        bTree.reverseOrder();
        long endTime = System.nanoTime();
        return startTime-endTime;
    }

    @benchmark(name = "iterator", category = "Binary Tree")
    public static long testIterator(long input){
        BinaryTree<String> testSubject = buildBalancedTree(input);
        long StartTime = System.nanoTime();
        for(String s : testSubject){
        }
        long endTime = System.nanoTime();
        return endTime-StartTime;
    }

    @benchmark(name = "unbalanced iterator", category = "Binary Tree")
    public static long testIteratorUnbal(long input){
        BinaryTree<String> testSubject = buildUnbalancedTreeDecreasing(input);
        long StartTime = System.nanoTime();
        for(String s : testSubject){
        }
        long endTime = System.nanoTime();
        return endTime-StartTime;
    }

    @benchmark(name = "add node", category = "Binary Tree")
    public static long testAddNode(long input){
        BinaryTree<String> testSubject = buildBalancedTree(input);
        String testSubject2 = ((char) rand.nextInt(5325323) + " Test " + input +1 + " ---- " + " " + (char) rand.nextInt(400));
        long StartTime = System.nanoTime();
        testSubject.add(testSubject2);
        long endTime = System.nanoTime();
        return endTime-StartTime;
    }

    @benchmark(name = "add node unbalanced left", category = "Binary Tree")
    public static long testAddUnbalanced(long input){
        BinaryTree<String> testSubject = buildUnbalancedTreeDecreasing(input);
        String testSubject2 = String.valueOf((char) 0);
        long StartTime = System.nanoTime();
        testSubject.add(testSubject2);
        long endTime = System.nanoTime();
        return endTime-StartTime;
    }

    @benchmark(name = "add node unbalanced right", category = "Binary Tree")
    public static long testAddUnbal2(long input){
        BinaryTree<String> testSubject = buildUnbalancedTreeIncreasing(input);
        String testSubject2 = String.valueOf((char) 0);
        long StartTime = System.nanoTime();
        testSubject.add(testSubject2);
        long endTime = System.nanoTime();
        return endTime-StartTime;
    }

    @benchmark(name = "iterator", category = "Linked List")
    public static long testIteratorList(long input){
        //Fill our dataset to size (input)
        ListOfLinks<String> list = new ListOfLinks<>();
        ListofLinksTester.fillList((int) input, list);
        //Test our method at that size
        long startTime = System.nanoTime();
        for(String s : list){
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    @benchmark(name = "getFaster()", category="Stack")
    public static long getFasterTest(long input){
        //We make a stack and fill it to the SIZE which is INPUT
        Stack<Integer> s = new Stack<Integer>();
        Random rand = new Random();
        for(int i = 0; i < input; i++){//this for loop uses the Iterator in the Iterable array test
            s.push(rand.nextInt());
        }
        long startTime = System.nanoTime();
        s.getFaster((int) (2 * input) / 3);
        long endTime = System.nanoTime();
        return endTime-startTime;
    }

    @benchmark(name = "get()" , category="Stack")
    public static long getTest(long input){
        //We make a stack and fill it to the SIZE which is INPUT
        Stack<Integer> s = new Stack<Integer>();
        Random rand = new Random();
        for(int i = 0; i < input; i++){//this for loop uses the Iterator in the Iterable array test
            s.push(rand.nextInt());
        }
        long startTime = System.nanoTime();
        s.get((int)  (2 *input )/ 3);
        long endTime = System.nanoTime();
        return endTime-startTime;
    }

    @benchmark(name="pop()", category="Stack")
    public static long popTest(long input){
        //We make a stack and fill it to the SIZE which is INPUT
        Stack<Integer> s = new Stack<Integer>();
        Random rand = new Random();
        for(int i = 0; i < input; i++){//this for loop uses the Iterator in the Iterable array test
            s.push(rand.nextInt());
        }
        long startTime = System.nanoTime();
        s.pop();
        long endTime = System.nanoTime();
        return endTime-startTime;
    }

    @benchmark(name="push()", category="Stack")
    public static long pushTest(long input){
        //We make a stack and fill it to the SIZE which is INPUT
        Stack<Integer> s = new Stack<Integer>();
        Random rand = new Random();
        for(int i = 0; i < input; i++){//this for loop uses the Iterator in the Iterable array test
            s.push(rand.nextInt());
        }
        int added = rand.nextInt();
        long startTime = System.nanoTime();
        s.push(added);
        long endTime = System.nanoTime();
        return endTime-startTime;
    }

    private static BinaryTree<String> buildBalancedTree(long size){
        BinaryTree<String> bTree = new BinaryTree<>();
        for(long i = 0; i <size; i++){
            bTree.add(((char) rand.nextInt(5325323) + " Test " + i + " ---- " + " " + (char) rand.nextInt(400)));
        }
        return bTree;
    }

    private static BinaryTree<String> buildUnbalancedTreeIncreasing(long size){
        BinaryTree<String> bTree = new BinaryTree<>();
        for(long i = 0; i <size; i++){
            bTree.add(((char) i + " Test " + (char) i + " ---- " + " " ));
        }
        return bTree;
    }

    private static BinaryTree<String> buildUnbalancedTreeDecreasing(long size){
        BinaryTree<String> bTree = new BinaryTree<>();
        for(long i = size; i >0; i--){
            bTree.add(((char) i + " Test " + (char) i + " ---- " + " " ));
        }
        return bTree;
    }

}
