package com.dnu.ffeks.lab1;

public class QuickSort {

    private QuickSort() {
    }

    private static String[] array;

    public static void sort(String[] inputArr) {

        if (inputArr == null || inputArr.length == 0) {
            return;
        }
        array = inputArr;
        int length = inputArr.length;
        quickSort(0, length - 1);
    }

    private static void quickSort(int lowerIndex, int higherIndex) {

        int i = lowerIndex;
        int j = higherIndex;

        // calculate pivot number, I am taking pivot as middle index number
        String pivot = array[lowerIndex + (higherIndex - lowerIndex) / 2];

        // Divide into two arrays
        while (i <= j) {

            /**
             * In each iteration, we will identify a number from left side which
             * is greater then the pivot value, and also we will identify a number
             * from right side which is less then the pivot value. Once the search
             * is done, then we exchange both numbers.
             */
            while (array[i].length() < pivot.length()) {
                i++;
            }
            while (array[j].length() > pivot.length()) {
                j--;
            }
            if (i <= j) {
                swap(i, j);
                //move index to next position on both sides
                i++;
                j--;
            }
        }

        // call quickSort() method recursively
        if (lowerIndex < j) {
            quickSort(lowerIndex, j);
        }
        if (i < higherIndex) {
            quickSort(i, higherIndex);
        }
    }

    private static void swap(int i, int j) {
        String temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
