package com.ecommerce_be.ecommerce_backend.service;

public class RemoveItemFromBasketItems {
    public String removeItemFromBasketItems(String inputStr, int numberToRemove) {
        // Split the string into an array of numbers
        String[] numbers = inputStr.split(",");

        // Find the index of the number to remove
        int indexToRemove = -1;
        for (int i = 0; i < numbers.length; i++) {
            if (Integer.parseInt(numbers[i]) == numberToRemove) {
                indexToRemove = i;
                break;
            }
        }

        // If the number is not found, return the original string
        if (indexToRemove == -1) {
            return inputStr;
        }

        // Remove the number and adjust commas based on the conditions
        if (numbers.length > 1) {
            if (indexToRemove == 0) {
                // Remove the first number along with the preceding comma
                numbers = removeElement(numbers, indexToRemove);
                if (numbers.length > 1) {
                    numbers[0] = numbers[0].replaceFirst("^,", "");
                }
            } else if (indexToRemove == numbers.length - 1) {
                // Remove the last number along with the following comma
                numbers = removeElement(numbers, indexToRemove);
                numbers[numbers.length - 1] = numbers[numbers.length - 1].replaceAll(",$", "");
            } else {
                // Remove a number in the middle, just remove the number
                numbers = removeElement(numbers, indexToRemove);
            }
        }

        // Join the numbers back into a string
        String resultStr = String.join(",", numbers);

        return resultStr;
    }

    private static String[] removeElement(String[] array, int index) {
        String[] newArray = new String[array.length - 1];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, newArray.length - index);
        return newArray;
    }


}
