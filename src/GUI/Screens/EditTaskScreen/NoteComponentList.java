package GUI.Screens.EditTaskScreen;

import GUI.Style.ScreensPaths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NoteComponentList extends VBox {

    ArrayList<NoteComponentController> noteComponents = new ArrayList<>();
    ArrayList<String> deletedNotes  = new ArrayList<>();


    public NoteComponentList() {
        styleProperty().setValue("-fx-background-color: transparent;");
    }

    public void addNote(String initialNote) {

        try {
            NoteComponentController ncm  = new NoteComponentController(this,initialNote);
            Parent noteParent = null;
            ScreensPaths paths = new ScreensPaths();

            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource(paths.getNoteComponentFxml()));

            loader.setController(ncm);
            noteParent = loader.load();

            noteParent.getStylesheets().clear();
            noteParent.getStylesheets().add(paths.getNoteComponentCssSheet());

            this.noteComponents.add(ncm);
            this.getChildren().add(noteParent);

            ncm.requestFocus();

        } catch(IOException exception) {
            exception.printStackTrace();
        }

    }

    public void notifyOfDeletion(NoteComponentController noteComponent) {
        this.deletedNotes.add(noteComponent.getInitialNote());
        this.getChildren().remove(noteComponent.getParent());
        this.noteComponents.remove(noteComponent);
    }

    public List<String> getNewNotes(){
        return this.noteComponents.stream()
                .filter(o-> (o.isValidNote() && o.isNewNote()) )
                .map(NoteComponentController::getNote)
                .collect(Collectors.toList());
    }

    public List<String> getDeletedNotes(){
        return this.deletedNotes;
    }

    public List<Pair<String,String>> getUpdatedNotes(){
        return this.noteComponents.stream()
                .filter(NoteComponentController::isUpdated)
                .map(o->new Pair<>(o.getInitialNote(),o.getNote()))
                .collect(Collectors.toList());
    }

}
