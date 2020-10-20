package engine.main;

public class Format {
	
	private Font font;
	private String name = Font.DIALOG;
	private int style = Font.PLAIN;
	private int size = 12;

	public Format() {
		font = new Font(name, style, size);
	}

	public void setName(String name) {
		this.name = name;
		font = new Font(name, style, size);
	}

	public void setStyle(int style) {
		this.style = style;
		font = new Font(name, style, size);
	}

	public void setSize(int size) {
		this.size = size;
		font = new Font(name, style, size);
	}

	public Font getFont() {
		return font;
	}
}
