package com.ImplementationOfLoopQueue;
/***
 * @description 循环队列实现 循环队列就是为了shu'j
 * @author huangweiyue
 * @date Created in 2019/6/1-20:18
 */
public class LoopQueue<E> implements Queue<E> {

    private E[] data;
    /**
     * 循环队列的第1个元素位置
     */
    private int front;
    /**
     * 环队列的最后1个元素的【后一个元素】位置
     */
    private int tail;
    private int size;

    public LoopQueue(int capacity){
        data = (E[])new Object[capacity + 1];
        front = 0;
        tail = 0;
        size = 0;
    }

    public LoopQueue(){
        this(10);
    }

    public int getCapacity(){
        return data.length - 1;
    }

    @Override
    public boolean isEmpty(){
        return front == tail;
    }

    @Override
    public int getSize(){
        return size;
    }

    @Override
    public void enqueue(E e){
        //入队 首先看队列是否是满的 如果满了 就扩容
        if((tail + 1) % data.length == front) {
            //扩容 data.length是浪费了1个空间的，所以这里用getCapacity 来取[data.length - 1]长度
            resize(getCapacity() * 2);
        }
        //tail 指向最后一个元素的位置 注意循环队列是浪费了1个空间的！
        data[tail] = e;
        //维护下tail tail本来应该是++ 但是因为是循环队列 所以需要(tail + 1) % data.length（联想钟表的时钟）
        tail = (tail + 1) % data.length;
        size ++;
    }

    @Override
    public E dequeue(){

        if(isEmpty()) {
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");
        }
        E ret = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size --;
        if(size == getCapacity() / 4 && getCapacity() / 2 != 0) {
            resize(getCapacity() / 2);
        }
        return ret;
    }

    /**
     * 获取队首元素
     * @return
     */
    @Override
    public E getFront(){
        if(isEmpty()) {
            throw new IllegalArgumentException("Queue is empty.");
        }
        return data[front];
    }

    private void resize(int newCapacity){

        E[] newData = (E[])new Object[newCapacity + 1];
        for(int i = 0 ; i < size ; i ++) {
            //data中的元素对于newData[]中的元素是有front的偏移量的，所以是data[(i + front)
            //但是由于是循环队列 data[(i + front)可能超过data.length产生越界 所以需要%对length取余
            newData[i] = data[(i + front) % data.length];
        }

        data = newData;
        front = 0;
        //size（循环队列中的元素个数）是不需要动的 因为扩容过程中 数据个数是没变的（无元素入队 出队）
        tail = size;
    }

    @Override
    public String toString(){

        StringBuilder res = new StringBuilder();
        res.append(String.format("Queue: size = %d , capacity = %d\n", size, getCapacity()));
        res.append("front [");
        for(int i = front ; i != tail ; i = (i + 1) % data.length){
            res.append(data[i]);
            if((i + 1) % data.length != tail) {
                res.append(", ");
            }
        }
        res.append("] tail");
        return res.toString();
    }

    public static void main(String[] args){

        LoopQueue<Integer> queue = new LoopQueue<>();
        for(int i = 0 ; i < 10 ; i ++){
            queue.enqueue(i);
            System.out.println(queue);

            if(i % 3 == 2){
                queue.dequeue();
                System.out.println(queue);
            }
        }
    }
}
