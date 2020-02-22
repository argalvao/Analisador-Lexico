package model;

import java.util.ArrayList;
import java.util.List;

public class SynthaticNode {
    private List<SynthaticNode> nodeList;
    private Token token;

    public SynthaticNode(Token token) {
        this.nodeList = new ArrayList<>();
        this.token = token;
    }

    public SynthaticNode() {
        this.nodeList = new ArrayList<>();
        this.token = null;
    }

    public void add(SynthaticNode synthaticNode) {
        if (synthaticNode != null) {
            this.nodeList.add(synthaticNode);
        }
    }

    public Token getToken() {
        return token;
    }

    public List<SynthaticNode> getNodeList() {
        return nodeList;
    }

    public boolean isEmpty() {
        return this.nodeList.isEmpty();
    }
}