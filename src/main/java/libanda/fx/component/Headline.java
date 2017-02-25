package libanda.fx.component;

import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import lang.I18N.WordCode;
import main.Data;
import main.DefaultData;

public class Headline extends PercentagePane {

	private Text headline;

	public Headline () {
		super();
		this.headline = new Text();
		init(TextAlignment.CENTER);
	}

	public Headline (WordCode title) {
		super();
		this.headline = new Text();
		this.headline.textProperty().bind(Data.i18n.getWordProperty(title));
		init(TextAlignment.CENTER);
	}

	@Deprecated // TODO delete
	public Headline (Text headline) {
		super();
		this.headline = headline;
		init(TextAlignment.CENTER);
	}

	public Headline (String title){
		super();
		this.headline = new Text(title);
		init(TextAlignment.CENTER);
	}

	public Headline (TextAlignment textAlign) {
		super();
		this.headline = new Text();
		init(textAlign);
	}

	public Headline (WordCode title, TextAlignment textAlign) {
		super();
		this.headline = new Text();
		this.headline.textProperty().bind(Data.i18n.getWordProperty(title));
		init(textAlign);
	}

	@Deprecated // TODO delete
	public Headline (Text headline, TextAlignment textAlign) {
		super();
		this.headline = headline;
		init(textAlign);
	}

	public Headline (String title, TextAlignment textAlign) {
		super();
		this.headline = new Text(title);
		init(textAlign);
	}

	//////////////////////////////////////////////////

	private void init(TextAlignment textAlign) {
		this.headline.setTextAlignment(textAlign);
		this.headline.setFont(DefaultData.FONT_HEADLINE);
		float xAlign = 0, yAlign = 0.5f;
		Alignment alignment = Alignment.CENTER;
		switch (textAlign){
		case CENTER:
			xAlign = 0.5f;
			alignment = Alignment.CENTER;
			break;
		case RIGHT:
			xAlign = 1;
			alignment = Alignment.CENTER_LEFT;
			break;
		case JUSTIFY:
		case LEFT:
			// keep xAlign and yAlign at 0.0f
			alignment = Alignment.CENTER_RIGHT;
			break;
		}
		this.getChildren().clear();
		this.addChild(this.headline, xAlign, yAlign);
		this.areaMap.get(this.headline).alignment = alignment;
		this.setPrefHeight(DefaultData.HEADLINE_HEIGHT);
	}

	public void setTitle(WordCode title){
		this.headline.textProperty().unbind();
		this.headline.textProperty().bind(Data.i18n.getWordProperty(title));
		init(TextAlignment.CENTER);
	}

	@Deprecated // TODO delete
	public void setTitle(Text headline){
		this.headline = headline;
		init(TextAlignment.CENTER);
	}

	public void setTitle(String title) {
		this.headline.textProperty().unbind();
		this.headline.setText(title);
		init(TextAlignment.CENTER);
	}

	public void setTitle(WordCode title, TextAlignment textAlign){
		this.headline.textProperty().unbind();
		this.headline.textProperty().bind(Data.i18n.getWordProperty(title));
		init(textAlign);
	}

	@Deprecated // TODO delete
	public void setTitle(Text headline, TextAlignment textAlign){
		this.headline = headline;
		init(textAlign);
	}

	public void setTitle(String title, TextAlignment textAlign) {
		this.headline.textProperty().unbind();
		this.headline.setText(title);
		init(textAlign);
	}

	public String getTitle(){
		return this.headline.getText();
	}

	public Text getTextElement(){
		return this.headline;
	}

}
