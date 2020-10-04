package GUI.Screens.AddTaskScreen;

import GUI.Style.ScreensPaths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NoteComponentList extends VBox {

    ArrayList<NoteComponentController> noteComponents = new ArrayList<>();

    public NoteComponentList() {
        styleProperty().setValue("-fx-background-color: transparent;");
    }

    public void addNote() {

        try {
            NoteComponentController ncm  = new NoteComponentController(this);
            Parent noteParent = null;
            ScreensPaths paths = new ScreensPaths();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(paths.getAddTaskNoteComponentFxml()));
            loader.setController(ncm);
            noteParent = loader.load();

            noteParent.getStylesheets().clear();
            noteParent.getStylesheets().add(paths.getAddTaskNoteComponentCssSheet());

            this.noteComponents.add(ncm);
            this.getChildren().add(noteParent);

            ncm.requestFocus();

        } catch(IOException exception) {
            exception.printStackTrace();
        }

    }

    public void notifyOfDeletion(NoteComponentController noteComponent) {
        this.getChildren().remove(noteComponent.getParent());
        this.noteComponents.remove(noteComponent);
    }

    public List<String> getNewNotes(){
        return this.noteComponents.stream()
                .filter(NoteComponentController::isValidNote)
                .map(NoteComponentController::getNote)
                .collect(Collectors.toList());
    }

}
