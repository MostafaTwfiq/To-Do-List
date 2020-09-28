package GUI.Screens.EditTaskScreen;

import java.util.Stack;

public class NoteMomento {
    private Stack<String> noteEdits = new Stack<String>();
    private Stack<String> temp  =  new Stack<String>();


    public NoteMomento(String originalNote){
        this.noteEdits.push(originalNote);
    }


    public void updateEdit(String note){
        this.noteEdits.push(note);
    }


    public String undoEdit(){
        temp.push(this.noteEdits.pop());
        return this.noteEdits.peek();
    }


    public String redoEdit(){
     this.noteEdits.push(this.temp.pop());
     return this.noteEdits.peek();
    }

}

