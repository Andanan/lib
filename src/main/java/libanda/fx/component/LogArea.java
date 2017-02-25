package libanda.fx.component;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import main.Data;
import main.Log;
import offtime.OffTime;

public class LogArea extends TextArea {

	private ObservableList<OffTime> offTimes;

	public LogArea () {
		super();
		init();
	}

	public LogArea(String text){
		super(text);
		init();
	}

	private void init(){
		this.setEditable(false);
		Data.font_logarea_property.addListener(new ChangeListener<Font>() {
			@Override
			public void changed(ObservableValue<? extends Font> observable, Font oldValue, Font newValue) {
				setFont(newValue);
			}
		});
		// auto scroll (down)
		LogArea area = this;
		this.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				area.setScrollTop(Double.MAX_VALUE);
			}
		});
		this.offTimes = FXCollections.observableArrayList();
		this.offTimes.addListener(new ListChangeListener<OffTime>(){
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends OffTime> c) {
				update();
			}
		});
	}

	public void putOffTime(OffTime offTime){
		if(this.offTimes.size() == 0 || this.offTimes.get(this.offTimes.size()-1).isValid()){
			// last list entry is valid
			this.offTimes.add(offTime);
		}else{
			// last list entry is invalid
			// Overwrite existing (invalid) OffTime
			this.offTimes.set(this.offTimes.size()-1, offTime);
		}
	}

	public void empty(){
		this.offTimes = FXCollections.observableArrayList();
		this.update();
	}

	public void update(){
		StringBuffer sb = new StringBuffer();
		for(OffTime ot : this.offTimes){

			sb.append("\n");
			sb.append(Log.createLogEntry(ot, true));
			if(ot.isValid()){
				sb.append("\n");
				sb.append(Log.createLogEntry(ot, false));
			}
			sb.append("\n");
		}
		// change content of text area (does not trigger ChangeListener)
		this.setText(sb.toString());
		// trigger ChangeListener of textArea
		this.appendText("");

	}
}
