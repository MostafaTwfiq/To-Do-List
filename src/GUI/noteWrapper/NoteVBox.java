package GUI.noteWrapper;

import GUI.Style.ScreensPaths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NoteVBox extends VBox {

    private final double WIDTH = 929.0;
    private final double HEIGHT = 249.0;

    ArrayList<NoteComponentController> noteComponents = new ArrayList<>();
    ArrayList<NoteComponentController> removedNotes = new ArrayList<>();

    public NoteVBox(){
        super();

        this.setPrefWidth(this.WIDTH);
        this.setPrefHeight(this.HEIGHT);
        this.setMinHeight(this.HEIGHT);
        this.setMinWidth(this.WIDTH);
    }

    public void addNote() {
        try{
            NoteComponentController ncm  = new NoteComponentController(this);
            Parent noteParent = null;
            ScreensPaths paths = new ScreensPaths();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(paths.getNoteFxml()));
            loader.setController(ncm);
            noteParent = loader.load();


            this.noteComponents.add(ncm);
            this.getChildren().add(noteParent); }
        catch(IOException exception)
        {
            exception.printStackTrace();
        }
    }

    public void addNote(String note)  {
        try{
            NoteComponentController ncm  = new NoteComponentController(this);

            Parent noteParent = null;
            ScreensPaths paths = new ScreensPaths();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(paths.getNoteFxml()));
            loader.setController(ncm);
            noteParent = loader.load();

            this.noteComponents.add(ncm);
            this.getChildren().add(noteParent);

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void notifyOfDeletion(NoteComponentController noteComponent) {
        this.getChildren().remove(noteComponent.getParent());
        this.removedNotes.add(noteComponent);
    }

    public List<String> getDeletedNotes(){
        return this.removedNotes.stream()
                                .map(NoteComponentController::getNote)
                                .collect(Collectors.toList());
    }
//
//    public List<NoteComponentController> getUpdatedNotes(){
//        return this.noteComponents.stream()
//                    .filter(o-> o.hasBeenChanged() &&
//                                !o.isNewNotes())
//                    .collect(Collectors.toList());
//    }

    public List<String> getNewNotes(){
        return this.noteComponents.stream()
                .filter(NoteComponentController::isNewNotes)
                .map(NoteComponentController::getNote)
                .collect(Collectors.toList());
    }
}
