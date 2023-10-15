package dev.app;

import java.util.Stack;

public class UndoRedoStack {

    private int stackCounter = 0;
    private Stack<GameObject> stack;
    public static UndoRedoStack Instance;

    public UndoRedoStack(){
        Instance = this;
        stack = new Stack<>();
    }

    public void addToStack(GameObject o){
        if(stackCounter < GameManager.instructionCounter){
            updateStackStack();
        }
        stackCounter++;

        stack.push(o);
        peakStack();
    }

    public Stack<GameObject> updateStackStack(){
        if(stack.size() > 0){
            for(int i = stack.size(); i > stackCounter; i--){
                stack.pop();
               //System.out.println("POPPING");
                GameManager.instructionCounter = stackCounter;
            }
        }
        else{
            System.out.println("stack is already empty");
        }
        return stack;
    }

    public GameObject peakStack(){
        //System.out.println("Stack last object " + stack.peek().toString());
        return stack.peek();
    }

    public int isInStack(GameObject o){
        //returns index
        // -1 for not found;
        return stack.search(o);
    }

    public int getStackCounter(){
        return stackCounter;
    }

    public void undo(){
        if(stackCounter > 0){
            stackCounter--;
        }
        //System.out.println("stack index: " + stackCounter);

        InspectorPanel.Instance.updateGameObjectJList();
    }

    public void redo(){
        if(stackCounter < stack.size()){
            stackCounter++;
        }
        //System.out.println("stack index: " + stackCounter);

        InspectorPanel.Instance.updateGameObjectJList();
    }

    public void clearStack(){
        stack.clear();
        stackCounter = 0;
    }

}
