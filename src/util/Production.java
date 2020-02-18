package util;

import model.SynthaticNode;
import model.Token;

import java.util.Queue;

public interface Production {
    SynthaticNode run(Queue<Token> tokens);
}