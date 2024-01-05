package nota;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author PC
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ListView<ClassNota> noteList;

    @FXML
    private TextField titleField;

    @FXML
    private TextArea contentArea;

    private ClassNota selectedNote;

    @FXML
    private ColorPicker textColorPicker;

    @FXML
    private Slider fontSizeSlider;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configura o TextArea para quebrar automaticamente as linhas
        contentArea.setWrapText(true);
        
        // Inicializar a lista de notas com algumas notas de exemplo
        noteList.getItems().addAll(
                new ClassNota("Nota 1", "Conteúdo da Nota 1"),
                new ClassNota("Nota 2", "Conteúdo da Nota 2")
        );

        // Configurar um evento de seleção para a lista de notas
        noteList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    selectedNote = newValue;
                    if (selectedNote != null) {
                        titleField.setText(selectedNote.getTitle().trim());
                        contentArea.setText(selectedNote.getConteudo().trim());
                    }
                }
        );

        // Configurar a célula de fábrica para exibir os títulos das notas
        noteList.setCellFactory(param -> new ListCell<ClassNota>() {
            @Override
            protected void updateItem(ClassNota note, boolean empty) {
                super.updateItem(note, empty);

                if (empty || note == null || note.getTitle() == null) {
                    setText(null);
                } else {
                    setText(note.getTitle());
                }
            }
        });

        // Listener para a mudança da cor do texto
        textColorPicker.setOnAction(event -> changeTextColor());

        // Listener para a mudança do tamanho da fonte
        fontSizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> changeFontSize());
    }

    @FXML
    private void adicionarNota() {
        String title = titleField.getText().trim(); // Obtém o título da TextField
        String content = contentArea.getText().trim(); // Obtém o conteúdo da TextArea

        // Verifica se o título e o conteúdo não estão vazios antes de adicionar a nota
        if (!title.isEmpty() && !content.isEmpty()) {
            ClassNota newNote = new ClassNota(title, content);
            noteList.getItems().add(newNote);
            noteList.getSelectionModel().select(newNote);
            clearFields();
        } else {
            // Mostra uma mensagem de aviso
            showAlert("Erro", "Título e Conteúdo são obrigatórios", "Por favor, escreva o título e o conteúdo da nota.");
        }
    }

    //metodo para abrir a caixa de dialogo de alerta
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void excluirNota() {
        if (selectedNote != null) {
            noteList.getItems().remove(selectedNote);
            clearFields();
        }
    }

    @FXML
    private void atualizarNota() {
        if (selectedNote != null) {
            selectedNote.setTitle(titleField.getText());
            selectedNote.setConteudo(contentArea.getText());
            noteList.refresh(); // Atualiza a exibição na lista
        }
    }

    //limpar tudo
    private void clearFields() {
        titleField.clear();
        contentArea.clear();
    }

    @FXML
    //metodo para negritar
    private void toggleBold() {
        String currentStyle = contentArea.getStyle();
        if (contentArea.getStyle().contains("-fx-font-weight: bold;")) {
            contentArea.setStyle(currentStyle.replace("-fx-font-weight: bold;", ""));
        } else {
            contentArea.setStyle(currentStyle + "-fx-font-weight: bold;");
        }
    }

    @FXML
    //metodo para italizar
    private void toggleItalic() {
        String currentStyle = contentArea.getStyle();
        if (contentArea.getStyle().contains("-fx-font-style: italic;")) {
            contentArea.setStyle(currentStyle.replace("-fx-font-style: italic;", ""));
        } else {
            contentArea.setStyle(currentStyle + "-fx-font-style: italic;");
        }
    }

    @FXML
    //metodo para musar a cor de letra
    private void changeTextColor() {
        javafx.scene.paint.Color selectedColor = textColorPicker.getValue();
        String hexColor = String.format("#%02X%02X%02X",
                (int) (selectedColor.getRed() * 255),
                (int) (selectedColor.getGreen() * 255),
                (int) (selectedColor.getBlue() * 255));

        // Adiciona a nova cor ao estilo
        contentArea.setStyle("-fx-text-fill: " + hexColor + ";");
    }

    @FXML
    //metodo para alterar o tamanho de letra
    private void changeFontSize() {
        double newSize = fontSizeSlider.getValue();
        contentArea.setStyle("-fx-font-size: " + newSize + "px;");
    }

}
