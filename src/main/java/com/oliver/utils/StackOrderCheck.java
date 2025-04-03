package com.oliver.utils;

import java.util.Stack;

public class StackOrderCheck {
    public static boolean isPossibleOrder(char[] pushOrder, char[] popOrder) {
        Stack<Character> stack = new Stack<>();
        int pushIndex = 0;
        int popIndex = 0;

        while (popIndex < popOrder.length) {
            // 如果栈顶元素与当前出栈顺序元素相同，直接出栈
            if (!stack.isEmpty() && stack.peek() == popOrder[popIndex]) {
                stack.pop();
                popIndex++;
            } else if (pushIndex < pushOrder.length) {
                // 将压栈顺序中的元素压入栈中
                stack.push(pushOrder[pushIndex]);
                pushIndex++;
            } else {
                // 如果以上条件都不符合，则无法实现给定的出栈顺序
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        char[] pushSequence = {'a', 'b', 'c','d', 'e', 'f', 'g', 'h'};
        char[] popSequence = {'a', 'b', 'c','d', 'e', 'f', 'g', 'h'};

        boolean result = isPossibleOrder(pushSequence, popSequence);
        if (result) {
            System.out.println("给定的出栈顺序可以实现");        } else {
            System.out.println("给定的出栈顺序无法实现");
        }
    }
}

