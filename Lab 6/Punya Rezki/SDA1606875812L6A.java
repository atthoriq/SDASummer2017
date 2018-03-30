import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Muhammad Rezki on 11/7/2017.
 */
public class SDA1606875812L6A {
    public static void main(String[] args) throws IOException {
        boolean isMax = true;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] a = new int[1];

        HEAPY heap = new HEAPY(a);

        heap.poll(isMax);

        String line = null;

        while ((line = br.readLine()) != null ) {
            String input[] = line.split(" ");
            String perintah = input[0].toUpperCase();
            switch (perintah){
                case "INSERT":
                    int inputan = Integer.parseInt(input[1]);
                    heap.insert(inputan, isMax);
                    System.out.println("elemen dengan nilai " + inputan + " telah ditambahkan");
                    break;
                case "REMOVE":
                    if (heap.size != 0) {
                        int removean = heap.poll(isMax);
                        System.out.println(removean + " dihapus dari heap");
                        break;
                    } else {
                        System.out.println("heap kosong");
                        break;
                    }
                case "CONVERT":
                    if (input[1].equalsIgnoreCase("min")) {
                        isMax = false;
                        heap.buildHeap(isMax);
                        break;
                    } else if (input[1].equalsIgnoreCase("max")) {
                        isMax = true;
                        heap.buildHeap(isMax);
                        break;
                    }
                case "INFORMATION":
                    if (heap.size != 0) {
                        String tipeHeap = (isMax) ? "max" : "min";
                        int heightHeap = (int) heap.height(heap.getSize());
                        int top = heap.peek();
                        System.out.println(tipeHeap + " heap dengan tinggi " + heightHeap
                                + " dan nilai teratas " + top);
                        break;
                    } else {
                        System.out.println("heap kosong");
                        break;
                    }
                case "TOP":
                    int value = Integer.parseInt(input[1]);
                    if (heap.getSize() >= value) {
                        int top = heap.top(value, isMax);
                        System.out.println("nilai di urutan ke "+value+" adalah "+top);
                        break;
                    } else {
                        System.out.println("ukuran heap kurang");
                        break;
                }
            }
        }
    }

    public static class HEAPY {

        int[] heap;
        int size;

        public HEAPY(int[] heap) {
            this.size = heap.length;
            this.heap = Arrays.copyOf(heap, size);
        }

        /**
         * Makes the array {@param a} satisfy the max heap property starting from
         * {@param index} till the end of array.
         * <p/>
         */
        public void maxOrMinHeapify(int index, boolean isMax) {
            int largest = index;
            int leftIndex = 2 * index + 1;
            int rightIndex = 2 * index + 2;
            if (isMax) {
                if (leftIndex < size && heap[index] < heap[leftIndex]) {
                    largest = leftIndex;
                }
                if (rightIndex < size && heap[largest] < heap[rightIndex]) {
                    largest = rightIndex;
                }

                if (largest != index) {
                    swap(index, largest);
                    maxOrMinHeapify(largest, isMax);
                }

            } else {
                if (leftIndex < size && heap[index] > heap[leftIndex]) {
                    largest = leftIndex;
                }
                if (rightIndex < size && heap[largest] > heap[rightIndex]) {
                    largest = rightIndex;
                }

                if (largest != index) {
                    swap(index, largest);
                    maxOrMinHeapify(largest, isMax);
                }
            }
        }

        /**
         * Converts array {@param a} in to a max heap.
         * <p/>
         * Time complexity: O(n) and is not O(n log n).
         */
        public void buildHeap(boolean isMax) {
            for (int i = size / 2 - 1; i >= 0; i--) {
                maxOrMinHeapify(i, isMax);
            }
        }

        /**
         * Insert a new element into the heap satisfying the heap property.
         * <p>
         * Time complexity: O(log n) where 'n' is total no. of elements in heap or
         * O(h) where 'h' is the height of heap.
         *
         * @param elem
         */
        public void insert(int elem, boolean isMax) {
            if (isMax) {
                // increase heap size
                heap = Arrays.copyOf(heap, size + 1);
                int i = size;
                int parentIndex = (int) Math.floor((i - 1) / 2);
                // move up through the heap till you find the right position
                while (i > 0 && elem > heap[parentIndex]) {
                    heap[i] = heap[parentIndex];
                    i = parentIndex;
                    parentIndex = (int) Math.floor((i - 1) / 2);
                }
                heap[i] = elem;
                size++;

            } else {
                // increase heap size
                heap = Arrays.copyOf(heap, size + 1);
                int i = size;
                int parentIndex = (int) Math.floor((i - 1) / 2);
                // move up through the heap till you find the right position
                while (i > 0 && elem < heap[parentIndex]) {
                    heap[i] = heap[parentIndex];
                    i = parentIndex;
                    parentIndex = (int) Math.floor((i - 1) / 2);
                }
                heap[i] = elem;
                size++;
            }
        }

        public int peek() {
            if (size == 0) {
                return -1;
            } else {
                return heap[0];
            }
        }

        public int poll(boolean isMax) {
            if (size == 0)
                return -1;

            int min = heap[0];
            heap[0] = heap[size - 1];
            size--;
            maxOrMinHeapify(0, isMax);
            return min;
        }

        public int getSize() {
            return size;
        }

        public int top(int top, boolean isMax) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (int i = 0; i < top - 1; i++) {
                temp.add(poll(isMax));
            }
            int returnTop = peek();
            for (int i = 0; i < temp.size(); i++) {
                insert(temp.get(i), isMax);
            }
            return returnTop;
        }

        public double height(int size) {
            return Math.floor(Math.log(size) / Math.log(2));
        }


        private void swap(int firstIndex, int secondIndex) {
            int temp = heap[firstIndex];
            heap[firstIndex] = heap[secondIndex];
            heap[secondIndex] = temp;
        }


    }
}

