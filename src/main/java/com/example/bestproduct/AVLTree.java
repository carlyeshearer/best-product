//package com.example.bestproduct;
// **********************************************************************************
// Title: BestProduct
// Author: Carly Shearer
// Course Section: CMIS202-ONL1 (Seidel) Fall 2022
// File: AVLTree.java
// Description: Class creates an AVL tree.
// **********************************************************************************
import java.util.*;
public class AVLTree {

    //inner class to create node
    public class Node {
        double key;
        int height;
        Node left;
        Node right;

        Node(double key) {
            this.key = key;
        }
    }

    private Node root;

    //find a node
    public double find(double key) {
        Node current = root;
        while (current != null) {
            if (current.key == key) {
                break;
            }
            current = current.key < key ? current.right : current.left; //look to left or right depending
            //on whether node is < or > than current node
        }
        return current.key;
    }

    //insert a node
    public void insert(double key) {
        root = insert(root, key);
    }

    //delete a node
    public void delete(double key) {
        root = delete(root, key);
    }

    public Node getRoot() {
        return root;
    }

    public int height() {
        return root == null ? -1 : root.height;
    }

    //insert a node at a certain place in tree
    private Node insert(Node node, double key) {
        if (node == null) {
            return new Node(key);
        } else if (node.key > key) {
            node.left = insert(node.left, key);
        } else if (node.key < key) {
            node.right = insert(node.right, key);
        } else {
            System.out.println("Duplicate key.");
        }
        return rebalance(node);
    }

    //delete a node
    private Node delete(Node node, double key) {
        if (node == null) {
            return node;
        } else if (node.key > key) {
            node.left = delete(node.left, key);
        } else if (node.key < key) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                Node mostLeftChild = mostLeftChild(node.right);
                node.key = mostLeftChild.key;
                node.right = delete(node.right, node.key);
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }

    //find leftmost child
    private Node mostLeftChild(Node node) {
        Node current = node;

        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    //rebalance tree
    private Node rebalance(Node z) {
        updateHeight(z);
        int balance = getBalance(z);
        if (balance > 1) { //left rotation performed
            if (height(z.right.right) > height(z.right.left)) {
                z = rotateLeft(z);
            }
            else { //right rotation then left rotation performed
                z.right = rotateRight(z.right);
                z = rotateLeft(z);
            }
        }
        else if (balance < -1) { //right rotation performed
            if (height(z.left.left) > height(z.left.right)) {
                z = rotateRight(z);
            }
            else { //left rotation then right rotation performed
                z.left = rotateLeft(z.left);
                z = rotateRight(z);
            }
        }
        return z;
    }

    //perform right rotation
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node z = x.right;
        x.right = y;
        y.left = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    //perform left rotation
    private Node rotateLeft(Node y) {
        Node x = y.right;
        Node z = x.left;
        x.left = y;
        y.right = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    //update height
    private void updateHeight(Node n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    private int height(Node n) {
        return n == null ? -1 : n.height;
    }

    //get balance of tree
    public int getBalance(Node n) {
        return (n == null) ? 0 : height(n.right) - height(n.left);
    }

    //toString() method
    @Override
    public String toString() {
        return "AVLTree{" +
                "root=" + root.key +
                '}';
    }
}
