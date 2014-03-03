/**
 * 
 */
package spaceHunters.game;

import java.awt.Color;

/**
 * @author Nathan
 * @version Apr 6, 2013
 */
public class AttackRecord {

	private String messageString;
	private Color messageColor;

	public AttackRecord() {
	}

	/**
	 * @return the messageString
	 */
	public String getMessageString() {
		return messageString;
	}

	/**
	 * @param messageString the messageString to set
	 */
	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

	/**
	 * @param messageColor the messageColor to set
	 */
	public void setMessageColor(Color messageColor) {
		this.messageColor = messageColor;
	}

	/**
	 * @return the messageColor
	 */
	public Color getMessageColor() {
		return messageColor;
	}
	
	
}
