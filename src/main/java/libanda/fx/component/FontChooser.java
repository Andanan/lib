package libanda.fx.component;

import gui.elements.PercentagePane.Alignment;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import lang.I18N.WordCode;
import main.Data;

public class FontChooser extends GridPane {

	private ObjectProperty<Font> fontProperty;

	private StringProperty familyProperty;
	private ObjectProperty<Style> styleProperty;
	private DoubleProperty sizeProperty;


	private ChoiceBox<String> familyBox;
	private ChoiceBox<String> styleBox;
	private ChoiceBox<Double> sizeBox;

	private String previewText;

	public static final Font STANDARD_FONT = Font.font("SansSerif", 12);
	public static final String STANDARD_PREVIEW_TEXT = "Lorem ipsum dolor sit Amet.";

	private static final Double[] SIZES = {10.0,11.0,12.0,13.0,14.0,16.0,18.0,20.0,22.0,24.0};

	public FontChooser () {
		this.previewText = STANDARD_PREVIEW_TEXT;
		this.fontProperty = new SimpleObjectProperty<>(STANDARD_FONT);
		initElements();
	}

	public FontChooser (String previewText){
		this.previewText = previewText;
		this.fontProperty = new SimpleObjectProperty<>(STANDARD_FONT);
		initElements();
	}

	public FontChooser (Font initialFont){
		this.previewText = STANDARD_PREVIEW_TEXT;
		this.fontProperty = new SimpleObjectProperty<>(initialFont);
		initElements();
	}

	public FontChooser (Font initialFont, String previewText){
		this.previewText = previewText;
		this.fontProperty = new SimpleObjectProperty<>(initialFont);
		initElements();
	}

	private void initElements(){
		String regular = Data.i18n.getWordProperty(WordCode.FONTCHOOSER_REGULAR).get();
		String bold = Data.i18n.getWordProperty(WordCode.FONTCHOOSER_BOLD).get();
		String italic = Data.i18n.getWordProperty(WordCode.FONTCHOOSER_ITALIC).get();
		String bolditalic = Data.i18n.getWordProperty(WordCode.FONTCHOOSER_BOLDITALIC).get();
		ObservableList<String> styleContent = FXCollections.observableArrayList(regular, bold, italic, bolditalic);
		Data.i18n.getWordProperty(WordCode.FONTCHOOSER_REGULAR).addListener((obs, oldVal, newVal) -> {
			styleContent.set(0, newVal);
		});
		Data.i18n.getWordProperty(WordCode.FONTCHOOSER_BOLD).addListener((obs, oldVal, newVal) -> {
			styleContent.set(1, newVal);
		});
		Data.i18n.getWordProperty(WordCode.FONTCHOOSER_ITALIC).addListener((obs, oldVal, newVal) -> {
			styleContent.set(2, newVal);
		});
		Data.i18n.getWordProperty(WordCode.FONTCHOOSER_BOLDITALIC).addListener((obs, oldVal, newVal) -> {
			styleContent.set(3, newVal);
		});


		// TODO family label

		// TODO family box

		// TODO style label

		// TODO style Box finished?
		this.styleBox = new ChoiceBox<>(styleContent);
		this.sizeBox.getSelectionModel().selectedIndexProperty().addListener((obs, old, newVal) -> {
			switch((int) newVal){
			case 0:
				styleProperty.set(Style.REGULAR);
				break;
			case 1:
				styleProperty.set(Style.BOLD);
				break;
			case 2:
				styleProperty.set(Style.ITALIC);
				break;
			case 3:
				styleProperty.set(Style.BOLD_ITALIC);
				break;
			}
		});
		this.styleProperty.addListener((obs,oldVal,newVal) -> {
			updateFontProperty();
		});
		this.add(this.styleBox, 1, 1);

		// TODO size label

		this.sizeBox = new ChoiceBox<>(FXCollections.observableArrayList(SIZES));
		this.sizeProperty.bind(this.sizeBox.getSelectionModel().selectedItemProperty());
		this.sizeProperty.addListener((obs,oldVal,newVal) -> {
			updateFontProperty();
		});
		this.add(sizeBox, 2, 1);

		Headline previewTitle = new Headline(WordCode.FONTCHOOSER_PREVIEW, TextAlignment.LEFT);
		this.add(previewTitle, 0, 2);

		TextArea preview = new TextArea(this.previewText);
		this.fontProperty.addListener(new ChangeListener<Font>() {
			@Override
			public void changed(ObservableValue<? extends Font> observable, Font oldValue, Font newValue) {
				preview.setFont(newValue);
			}
		});
		preview.prefHeight(200);
		this.add(preview, 0, 3, 3, 1);

		selectFont(this.fontProperty.get());
	}

	private void updateFontProperty(){
		String family = this.familyProperty.get();
		Style style = this.styleProperty.get();
		FontWeight weight = FontWeight.NORMAL;
		FontPosture posture = FontPosture.REGULAR;
		switch(style){
		case BOLD:
			weight = FontWeight.BOLD;
			break;
		case BOLD_ITALIC:
			weight = FontWeight.BOLD;
			posture = FontPosture.ITALIC;
			break;
		case ITALIC:
			posture = FontPosture.ITALIC;
			break;
		case REGULAR:
			break;
		}
		double size = this.sizeProperty.get();
		Font newFont = Font.font(family, weight, posture, size);
		this.fontProperty.set(newFont);
	}

	private void selectFont(Font font){
		String family = font.getFamily();
		String style = font.getStyle();
		boolean bold = style.contains("Bold");
		boolean italic = style.contains("Italic");
		double size = font.getSize();

		this.familyBox.getSelectionModel().select(family);
		this.sizeBox.getSelectionModel().select(size);
		if(!bold && !italic){
			// regular
			this.styleBox.getSelectionModel().select(0);
		}else if(bold && !italic){
			// bold
			this.styleBox.getSelectionModel().select(1);
		}else if(!bold && italic){
			// italic
			this.styleBox.getSelectionModel().select(2);
		}else if(bold && italic){
			// bold italic
			this.styleBox.getSelectionModel().select(3);
		}
	}

	public Font getFont(){
		return this.fontProperty.get();
	}

	public void setFont(Font font){
		this.fontProperty.set(font);
		selectFont(font);
	}

	public ObjectProperty<Font> fontProperty(){
		return this.fontProperty;
	}

	private enum Style{
		BOLD,
		BOLD_ITALIC,
		ITALIC,
		REGULAR
	}

}
