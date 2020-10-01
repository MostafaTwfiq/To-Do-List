package GUI.Screens.NoteWrapper;

import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NoteVBox extends VBox {

    private final double WIDTH = 929.0;
    private final double HEIGHT = 249.0;

    ArrayList<NoteComponent> noteComponents = new ArrayList<>();
    ArrayList<NoteComponent> removedNotes = new ArrayList<>();

    public NoteVBox(){
        super();
        this.setPrefWidth(this.WIDTH);
        this.setPrefHeight(this.HEIGHT);
    }

    public void addNote(){
        NoteComponent ncm  = new NoteComponent(this);
        this.noteComponents.add(ncm);
        this.getChildren().add(ncm);
    }

    public void addNote(String note){
        NoteComponent ncm  = new NoteComponent(this,note);
        this.noteComponents.add(ncm);
        this.getChildren().add(ncm);
    }

    public void notifyOfDeletion(NoteComponent noteComponent) {
        this.getChildren().remove(noteComponent);
        this.removedNotes.add(noteComponent);
    }

    public List<String> getDeletedNotes(){
        return this.removedNotes.stream()
                                .map(NoteComponent::getNoteData)
                                .collect(Collectors.toList());
    }

    public List<NoteComponent> getUpdatedNotes(){
        return this.noteComponents.stream()
                    .filter(o-> o.hasBeenChanged() &&
                                !o.isNewNotes())
                    .collect(Collectors.toList());
    }

    public List<String> getNewNotes(){
        return this.noteComponents.stream()
                .filter(NoteComponent::isNewNotes)
                .map(NoteComponent::getNoteData)
                .collect(Collectors.toList());
    }
}
