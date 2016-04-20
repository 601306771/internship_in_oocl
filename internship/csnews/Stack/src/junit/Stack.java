package junit;

import java.util.LinkedList;

/**
 * ��LinkedListʵ��ջ
 * 
 * ���к�ջ���𣺶����Ƚ��ȳ���ջ�Ƚ������
 * 
 * @author �ּ���
 * @version 1.0 Sep 5, 2013 11:24:34 PM
 */
public class Stack<T> {
    private LinkedList<T> storage = new LinkedList<T>();

    /** ��ջ */
    public void push(T v) {
        storage.addFirst(v);
    }

    /** ��ջ������ɾ�� */
    public T peek() {
        return storage.getFirst();
    }

    /** ��ջ��ɾ�� */
    public T pop() {
        return storage.removeFirst();
    }

    /** ջ�Ƿ�Ϊ�� */
    public boolean empty() {
        return storage.isEmpty();
    }

    /** ��ӡջԪ�� */
    public String toString() {
        return storage.toString();
    }
    
    public static void main(String[] args) {
        Stack stack=new Stack<String>();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        //[c, b, a]
        System.out.println(stack.toString());
        //c--[c, b, a]
        Object obj=stack.peek();
        System.out.println(obj+"--"+stack.toString());
        obj=stack.pop();
        //c--[b, a]
        System.out.println(obj+"--"+stack.toString());
        //false
        System.out.println(stack.empty());
    }
}