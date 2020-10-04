package GUI.Screens.EditTaskScreen;

import GUI.Style.ScreensPaths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoteComponentList extends VBox {

    ArrayList<NoteComponentController> noteComponents = new ArrayList<>();

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

            noteComponents.add(ncm);
            getChildren().add(noteParent);

            ncm.requestFocus();

        } catch(IOException exception) {
            exception.printStackTrace();
        }

    }

    public void notifyOfDeletion(NoteComponentController noteComponent) {
        getChildren().remove(noteComponent.getParent());
    }

    public List<NoteComponentController> getNotesComponents() {
        return noteComponents;
    }

}
