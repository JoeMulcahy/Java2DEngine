import java.util.Stack;

public class UndoRedoStack {

    private static int stackCounter = 0;
    private static Stack<GameObject> stack;

    public UndoRedoStack(){
        stack = new Stack<>();
    }

    public static void addToStack(GameObject o){
        if(stackCounter < Helper.instructionCounter){
            updateStackStack();
            stackCounter++;

        }else{
            stackCounter++;
        }

        stack.push(o);
        peakStack();
    }

    public static Stack<GameObject> updateStackStack(){
        if(stack.size() > 0){
            for(int i = stack.size(); i > stackCounter; i--){
                stack.pop();
                System.out.println("POPPING");
                Helper.instructionCounter = stackCounter;
            }
        }
        else{
            System.out.println("stack is already empty");
        }
        return stack;
    }

    public static GameObject peakStack(){
        System.out.println("Stack last object " + stack.peek().toString());
        return stack.peek();
    }

    public static int isInStack(GameObject o){
        //returns index
        // -1 for not found;
        return stack.search(o);
    }

    public static int getStackCounter(){
        return stackCounter;
    }

    public static void undo(){
        if(stackCounter > 0){
            stackCounter--;
        }
        System.out.println("stack index: " + stackCounter);

        GameObjectAttributesPanel.updateGameObjectJList();
    }

    public static void redo(){
        if(stackCounter < stack.size()){
            stackCounter++;
        }
        System.out.println("stack index: " + stackCounter);

        GameObjectAttributesPanel.updateGameObjectJList();
    }

    public static void clearStack(){
        stack.clear();
        stackCounter = 0;
    }

}
