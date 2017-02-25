package libanda.fx.layout;

import java.util.HashMap;
import java.util.List;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * A Layout container for JavaFX applications which makes use of percentages to
 * position and resize its child nodes
 *
 * @author Florian Stricker (Andanan)
 * @version 1.0
 * @date 11.June.2016
 */
public class PercentagePane extends Pane {

	protected HashMap<Node, Area> areaMap = new HashMap<>();
	private Alignment alignment;
	private Rectangle oldHorizontal;
	private Rectangle oldVertical;
	private boolean printLines = false;

	/**
	 * Creates a new PercentagePane which has an Node alignment of 'CENTER'.
	 * <br />
	 * <b>IMPORTANT</b>: Do not add children via
	 * "PercentagePane.getChildren().add(child)"! If you do so, the child nodes
	 * will not appear, because the pane does not know where and how big the
	 * nodes should be positioned. Use the "setChild()" method instead.
	 */
	public PercentagePane() {
		super();
		this.alignment = Alignment.CENTER;
	}

	/**
	 * Creates a new PercentagePane of which the Node alignment is specified.
	 * All alignments <b>despite 'DEFAULT'</b> are allowed.<br />
	 * <b>IMPORTANT</b>: Do not add children via
	 * "PercentagePane.getChildren().add(child)"! If you do so, the child nodes
	 * will not appear, because the pane does not know where and how big the
	 * nodes should be positioned. Use the "setChild()" method instead.
	 *
	 * @param alignment
	 *            - The standard Alignment of the child Nodes
	 */
	public PercentagePane(Alignment alignment) {
		super();
		if (alignment == Alignment.DEFAULT) {
			throw new IllegalArgumentException("\"DEFAULT\" is not a valid standard Node alignment");
		}
		this.alignment = alignment;
	}

	/**
	 * Adds a child Node to this PercentagePane. Is used as replacement for the
	 * <br />
	 * "PercentagePane.getChildren().add(child)" - way of adding child Nodes. In
	 * this specific version of the addChild()-Method the child node and the
	 * position (in percent [0-1]) on the screen are specified.<br />
	 * <b>IMPORTANT</b>: Do not add children via
	 * "PercentagePane.getChildren().add(child)"! If you do so, the child nodes
	 * will not appear, because the pane does not know where and how big the
	 * nodes should be positioned.
	 *
	 * @param child
	 *            - The child to be added
	 * @param xPercentage
	 *            - The xPercentage of the node as percentage (0-1) of the total
	 *            paneWidth
	 * @param yPercentage
	 *            - The yPercentage of the node as percentage (0-1) of the total
	 *            paneHeight
	 */
	public void addChild(Node child, float xPercentage, float yPercentage) {
		addChild(child, xPercentage, yPercentage, -1, -1, Alignment.DEFAULT);
	}

	/**
	 * Adds a child Node to this PercentagePane. Is used as replacement for the
	 * <br />
	 * "PercentagePane.getChildren().add(child)" - way of adding child Nodes. In
	 * this specific version of the addChild()-Method the child node, the
	 * position (in percent [0-1]) on the screen and the alignment of this node
	 * are specified.<br />
	 * <b>IMPORTANT</b>: Do not add children via
	 * "PercentagePane.getChildren().add(child)"! If you do so, the child nodes
	 * will not appear, because the pane does not know where and how big the
	 * nodes should be positioned.
	 *
	 * @param child
	 *            - The child to be added
	 * @param xPercentage
	 *            - The xPercentage of the node as percentage (0-1) of the total
	 *            paneWidth
	 * @param yPercentage
	 *            - The yPercentage of the node as percentage (0-1) of the total
	 *            paneHeight
	 * @param alignment
	 *            - The Alignment of the given node (based on the position of
	 *            the point specified by xPercentage and yPercentage)
	 */
	public void addChild(Node child, float xPercentage, float yPercentage, Alignment alignment) {
		addChild(child, xPercentage, yPercentage, -1, -1, alignment);
	}

	/**
	 * Adds a child Node to this PercentagePane. Is used as replacement for the
	 * <br />
	 * "PercentagePane.getChildren().add(child)" - way of adding child Nodes. In
	 * this specific version of the addChild()-Method the child node, the
	 * position (in percent [0-1]) on the screen and the size in percent of the
	 * paneWidth and paneHeight are specified.<br />
	 * <b>IMPORTANT</b>: Do not add children via
	 * "PercentagePane.getChildren().add(child)"! If you do so, the child nodes
	 * will not appear, because the pane does not know where and how big the
	 * nodes should be positioned.
	 *
	 * @param child
	 *            - The child to be added
	 * @param xPercentage
	 *            - The xPercentage of the node as percentage (0-1) of the total
	 *            paneWidth
	 * @param yPercentage
	 *            - The yPercentage of the node as percentage (0-1) of the total
	 *            paneHeight
	 * @param widthPercentage
	 *            - The width of the node as percentage (0-1) of the total
	 *            paneWidth
	 * @param heightPercentage
	 *            - The height of the node as percentage (0-1) of the total
	 *            paneHeight
	 */
	public void addChild(Node child, float xPercentage, float yPercentage, float widthPercentage,
			float heightPercentage) {
		addChild(child, xPercentage, yPercentage, widthPercentage, heightPercentage, Alignment.DEFAULT);
	}

	/**
	 * Adds a child Node to this PercentagePane. Is used as replacement for the
	 * <br />
	 * "PercentagePane.getChildren().add(child)" - way of adding child Nodes. In
	 * this specific version of the addChild()-Method the child node, the
	 * position (in percent [0-1]) on the screen, the size in percent of the
	 * paneWidth and paneHeight and the alignment of this node are specified.
	 * <br />
	 * <b>IMPORTANT</b>: Do not add children via
	 * "PercentagePane.getChildren().add(child)"! If you do so, the child nodes
	 * will not appear, because the pane does not know where and how big the
	 * nodes should be positioned.
	 *
	 * @param child
	 *            - The child to be added
	 * @param xPercentage
	 *            - The xPercentage of the node as percentage (0-1) of the total
	 *            paneWidth
	 * @param yPercentage
	 *            - The yPercentage of the node as percentage (0-1) of the total
	 *            paneHeight
	 * @param widthPercentage
	 *            - The width of the node as percentage (0-1) of the total
	 *            paneWidth
	 * @param heightPercentage
	 *            - The height of the node as percentage (0-1) of the total
	 *            paneHeight
	 * @param alignment
	 *            - The Alignment of the given node (based on the position of
	 *            the point specified by xPercentage and yPercentage)
	 */
	public void addChild(Node child, float xPercentage, float yPercentage, float widthPercentage,
			float heightPercentage, Alignment alignment) {
		if (xPercentage < 0 || xPercentage > 1 || yPercentage < 0 || yPercentage > 1 || widthPercentage == 0
				|| widthPercentage > 1 || heightPercentage == 0 || heightPercentage > 1) {
			throw new IllegalArgumentException();
		}
		this.getChildren().add(child);
		this.areaMap.put(child, new Area(xPercentage, yPercentage, widthPercentage, heightPercentage, alignment));
	}

	@Override
	protected void layoutChildren() {
		super.layoutChildren();
		Bounds paneBounds = this.layoutBoundsProperty().get();
		double paneWidth = paneBounds.getWidth();
		double paneHeight = paneBounds.getHeight();

		if (printLines) {
			getChildren().remove(oldHorizontal);
			getChildren().remove(oldVertical);
			Rectangle horizontal = new Rectangle(0, paneHeight / 2, paneWidth, 1);
			Rectangle vertical = new Rectangle(paneWidth / 2, 0, 1, paneHeight);
			horizontal.setManaged(false);
			vertical.setManaged(false);
			horizontal.setMouseTransparent(true);
			vertical.setMouseTransparent(true);
			getChildren().add(horizontal);
			getChildren().add(vertical);
			this.oldHorizontal = horizontal;
			this.oldVertical = vertical;
		}

		List<Node> children = getManagedChildren();
		for (Node child : children) {
			Area childArea = this.areaMap.get(child);
			// calc size
			double childHeight;
			double childWidth;
			if (childArea.heightPercentage >= 0) {
				childHeight = paneHeight * childArea.heightPercentage;
			} else {
				childHeight = child.layoutBoundsProperty().get().getHeight();
			}
			if (childArea.widthPercentage >= 0) {
				childWidth = paneWidth * childArea.widthPercentage;
			} else {
				childWidth = child.layoutBoundsProperty().get().getWidth();
			}
			// end cals size
			// calc pos
			double xPos = 0;
			double yPos = 0;
			Alignment nodeAlignment;
			if (childArea.alignment == Alignment.DEFAULT) {
				nodeAlignment = this.alignment;
			} else {
				nodeAlignment = childArea.alignment;
			}
			switch (nodeAlignment) {
			case BOTTOM_RIGHT:
				xPos = paneWidth * childArea.xPercentage;
				yPos = paneHeight * childArea.yPercentage;
				break;
			case BOTTOM_CENTER:
				xPos = (paneWidth * childArea.xPercentage) - (childWidth / 2);
				yPos = paneHeight * childArea.yPercentage;
				break;
			case BOTTOM_LEFT:
				xPos = (paneWidth * childArea.xPercentage) - childWidth;
				yPos = paneHeight * childArea.yPercentage;
				break;
			case CENTER_RIGHT:
				xPos = paneWidth * childArea.xPercentage;
				yPos = (paneHeight * childArea.yPercentage) - (childHeight / 2);
				break;
			case CENTER:
				xPos = (paneWidth * childArea.xPercentage) - (childWidth / 2);
				yPos = (paneHeight * childArea.yPercentage) - (childHeight / 2);
				break;
			case CENTER_LEFT:
				xPos = (paneWidth * childArea.xPercentage) - childWidth;
				yPos = (paneHeight * childArea.yPercentage) - (childHeight / 2);
				break;
			case TOP_RIGHT:
				xPos = paneWidth * childArea.xPercentage;
				yPos = (paneHeight * childArea.yPercentage) - childHeight;
				break;
			case TOP_CENTER:
				xPos = (paneWidth * childArea.xPercentage) - (childWidth / 2);
				yPos = (paneHeight * childArea.yPercentage) - childHeight;
				break;
			case TOP_LEFT:
				xPos = (paneWidth * childArea.xPercentage) - childWidth;
				yPos = (paneHeight * childArea.yPercentage) - childHeight;
				break;
			case DEFAULT:
			default:
				child.setVisible(false);
				continue;
			}
			// end calc pos
			child.resizeRelocate(xPos, yPos, childWidth, childHeight);
		}
	}

	protected class Area {
		float xPercentage;
		float yPercentage;
		Alignment alignment = Alignment.DEFAULT;
		float widthPercentage = -1;
		float heightPercentage = -1;

		Area(float xPercentage, float yPercentage, float widthPercentage, float heightPercentage, Alignment alignment) {
			this.xPercentage = xPercentage;
			this.yPercentage = yPercentage;
			this.widthPercentage = widthPercentage;
			this.heightPercentage = heightPercentage;
			this.alignment = alignment;
		}
	}

	/**
	 * An Enum specifying the different Alignments of the Nodes in respect of
	 * the anchor point (which is set by percentage)
	 *
	 * @author Florian Stricker (Andanan)
	 */
	public enum Alignment {
		/**
		 * The Anchor Point is at the top left of the according node
		 */
		TOP_LEFT,
		/**
		 * The Anchor Point is at the top center of the according node
		 */
		TOP_CENTER,
		/**
		 * The Anchor Point is at the top right of the according node
		 */
		TOP_RIGHT,
		/**
		 * The Anchor Point is at the center left of the according node
		 */
		CENTER_LEFT,
		/**
		 * The Anchor Point is at the center of the according node
		 */
		CENTER,
		/**
		 * The Anchor Point is at the center right of the according node
		 */
		CENTER_RIGHT,
		/**
		 * The Anchor Point is at the bottom left of the according node
		 */
		BOTTOM_LEFT,
		/**
		 * The Anchor Point is at the bottom center of the according node
		 */
		BOTTOM_CENTER,
		/**
		 * The Anchor Point is at the bottom right of the according node
		 */
		BOTTOM_RIGHT,
		/**
		 * The position of the anchor point is defined by the default value
		 * which is set in the constructor.<br />
		 * <b>Important:</b> This value is not allowed in the PercentagePane
		 * constructor, because it is a reference to the value which is set
		 * there. If you use this value in the constructor anyway, an
		 * IllegalArgumentException will be thrown!
		 */
		DEFAULT
	}

}
