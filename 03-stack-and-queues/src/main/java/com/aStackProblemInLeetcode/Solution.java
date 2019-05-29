package com.aStackProblemInLeetcode;

import java.util.Stack;

/***
 * @description leetCode算法题 判断括号匹配
 * @author huangweiyue
 * @date Created in 2019/5/28-23:00
 */
class Solution {

    public boolean isValid(String s) {

        Stack<Character> stack = new Stack<>();
        for(int i = 0 ; i < s.length() ; i ++){
            char c = s.charAt(i);
            if(c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else{
                if(stack.isEmpty()) {
                    return false;
                }

                char topChar = stack.pop();
                if(c == ')' && topChar != '(') {
                    return false;
                }
                if(c == ']' && topChar != '[') {
                    return false;
                }
                if(c == '}' && topChar != '{') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println((new Solution()).isValid("()[]{}"));
        System.out.println((new Solution()).isValid("([)]"));
    }
}
