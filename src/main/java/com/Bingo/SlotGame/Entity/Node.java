package com.Bingo.SlotGame.Entity;

public class Node {
    int data;
    Node horizontal;
    Node vertical;

    public Node(int data) {
        this.data = data;
        this.horizontal = null;
        this.vertical = null;
    }

    public int getData() {
        return data;
    }

    public Node getHorizontal() {
        return horizontal;
    }

    public Node getVertical() {
        return vertical;
    }

    public void setHorizontal(Node horizontal) {
        this.horizontal = horizontal;
    }

    public void setVertical(Node vertical) {
        this.vertical = vertical;
    }
}
